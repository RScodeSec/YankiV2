package com.bootcamp.yankiaccount.transaction.service;

import com.bootcamp.yankiaccount.customer.entity.Account;
import com.bootcamp.yankiaccount.customer.service.AccountService;
import com.bootcamp.yankiaccount.transaction.dto.BootCoinRequest;
import com.bootcamp.yankiaccount.transaction.dto.ProcessConfirmation;
import com.bootcamp.yankiaccount.transaction.dto.SendKafka;
import com.bootcamp.yankiaccount.transaction.entity.BootcoinPay;
import com.bootcamp.yankiaccount.transaction.entity.Send;
import com.bootcamp.yankiaccount.transaction.message.producer.KafkaSender;
import com.bootcamp.yankiaccount.transaction.repository.BootcoinPayRepository;
import com.bootcamp.yankiaccount.transaction.repository.SendRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class SendServiceImpl  implements SendService{
    @Value("${topic}")
    String topic;

    @Autowired
    private SendRepository sendRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private KafkaSender kafkaSender;

    //BootCoinPay
    @Autowired
    private BootcoinPayRepository bootcoinPayRepository;

    @Override
    public Mono<Send> sendTransaction (Send send) {

        var phoneNumbers = Flux.just(send.getDestinationPhone(), send.getOriginPhone());
        var accounts = phoneNumbers.flatMap(phone -> accountService.findAccount(phone));
        var originAccount = accounts.filter(account -> account.getPhoneNumber().equals(send.getOriginPhone()));

        var updateBalanceInMemory = accounts.collectList().flatMapMany(account -> updateBalancesInMemory(account, send));

        var paymentIsWithBalance = originAccount.map(Account::getBalance).flatMap(balance -> transactionIsWitBalance(balance, send));

        return paymentIsWithBalance.flatMap(condition -> {
            if(condition){
                return updateBalanceInMemory.flatMap(account -> accountService.saveAccount(account)).then(Mono.just(false));
            }
            return Mono.just(true);
        }).any(value -> value.equals(true)).flatMap(condition -> {
            if (condition) {
                return originAccount.flatMap(origin -> {
                    send.setPaymentWitDebit(true);
                    send.setTransferSuccessful(false);
                    return sendRepository.save(send).map(operation -> {
                        SendKafka sendKafka = new SendKafka();
                        sendKafka.setAssociatedAccount(origin.getAssociatedAccount());
                        sendKafka.setAmount(send.getAmount());
                        sendKafka.setOperationNumber(operation.getOperationNumber());
                        sendKafka.setSendTo(send.getDestinationPhone());
                        kafkaSender.sendMessage(topic, sendKafka);
                        return operation;
                    });
                }).then(Mono.empty());
            } else {
                return sendRepository.save(send);
            }
        });

    }

    @Override
    public Mono<Account> processPaymentConfirmation(ProcessConfirmation processConfirmation) {
        var getBalance = accountService.findAccount(processConfirmation.getSendTo());
        var update = sendRepository.findByOperationNumber(processConfirmation.getOperationNumber())
                .map(status -> {
                    status.setTransferSuccessful(true);
                    return status;
                }).flatMap(sendRepository::save).flatMap(confirm -> {
                    return getBalance.map(balance ->{
                        balance.setBalance(balance.getBalance() + processConfirmation.getAmount());
                        return balance;
                    }).flatMap(accountService::saveAccount);
                });


        return Mono.just(update.block());
    }

    @Override
    public Mono<Send> findByOperationNumber(String opNumber) {
        log.info("making request to DB");
        return sendRepository.findByOperationNumber(opNumber);
    }

    @Override
    public Mono<BootcoinPay> sendTransactionBootCoin(BootCoinRequest bootcoinPay) {
        var priceBootcoin = 1.5;
        var account = accountService.findAccount(bootcoinPay.getAccountNumber());
        return  account.flatMap(pay -> {
            if(pay.getBalance() >= bootcoinPay.getAmount()*priceBootcoin){
                var registerTransact = new BootcoinPay();
                registerTransact.setCodeRequestBuy(bootcoinPay.getCodeRequestBuy());
                registerTransact.setAmount(bootcoinPay.getAmount());
                registerTransact.setTotalPrice(bootcoinPay.getAmount()*priceBootcoin);
                registerTransact.setAccountNumber(bootcoinPay.getAccountNumber());
                registerTransact.setUserDocBuy(bootcoinPay.getUserDocBuy());
                pay.setBalance(pay.getBalance() -bootcoinPay.getAmount()*priceBootcoin);

                return accountService.saveAccount(pay).then(bootcoinPayRepository.save(registerTransact));
                //return bootcoinPayRepository.save(registerTransact);
            }
            // send Bank
            return Mono.empty();
        });
        //return Mono.just(opBootcoin.block());

    }

    private Flux<Account> updateBalancesInMemory (List<Account> account, Send send) {
        var destination = account.get(0);
        var origin = account.get(1);
        if (destination.getPhoneNumber().equals(send.getDestinationPhone())) {
            destination.setBalance(destination.getBalance() + send.getAmount());
            origin.setBalance(origin.getBalance() - send.getAmount());
        } else {
            destination.setBalance(destination.getBalance() - send.getAmount());
            origin.setBalance(origin.getBalance() + send.getAmount());
        }
        return Flux.just(destination, origin);
    }

    private Mono<Boolean> transactionIsWitBalance (Double balance, Send send) {
        if (balance >= send.getAmount()){
            return Mono.just(true);
        }
        else {
            return Mono.just(false);
        }
    }


}
