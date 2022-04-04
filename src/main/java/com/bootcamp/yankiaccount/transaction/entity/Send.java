package com.bootcamp.yankiaccount.transaction.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "send-money")
public class Send {

    @Id
    private String sendId;

    private String destinationPhone;
    private String originPhone;
    private Double amount;

    @Builder.Default
    private String operationNumber = java.util.UUID.randomUUID().toString();

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Builder.Default
    private LocalDate date = LocalDate.now();

    @Builder.Default
    private Boolean paymentWitDebit = false;

    @Builder.Default
    private Boolean transferSuccessful = true;
}
