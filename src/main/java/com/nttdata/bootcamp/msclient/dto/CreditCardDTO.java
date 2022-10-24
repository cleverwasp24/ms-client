package com.nttdata.bootcamp.msclient.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreditCardDTO {

    private String creditCardNumber;
    private Integer creditCardType;
    private Double creditLine;
    private Double availableCredit;
    private LocalDateTime creationDate;
}
