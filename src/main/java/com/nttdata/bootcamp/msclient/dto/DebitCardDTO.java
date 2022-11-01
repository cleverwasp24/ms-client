package com.nttdata.bootcamp.msclient.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DebitCardDTO {

    private String cardNumber;
    private Integer clientCardType;
    private Long primaryAccountId;
    private List<Long> associatedAccountsId;
    private LocalDateTime creationDate;
}
