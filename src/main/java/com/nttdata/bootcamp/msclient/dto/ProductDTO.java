package com.nttdata.bootcamp.msclient.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductDTO {

    private List<AccountDTO> accounts;
    private List<CreditCardDTO> creditCards;
    private List<DebitCardDTO> debitCards;
    private List<LoanDTO> loans;

}
