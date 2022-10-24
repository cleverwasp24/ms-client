package com.nttdata.bootcamp.msclient.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoanDTO {

    private String loanCode;
    private Integer loanType;
    private Double totalDebt;
    private Double pendingDebt;
    private Integer installments;
    private LocalDateTime loanDate;

}
