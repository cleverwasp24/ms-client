package com.nttdata.bootcamp.msclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AccountDTO {

    private String accountNumber;
    private Integer accountType;
    private Double balance;
    private LocalDateTime openingDate;

}
