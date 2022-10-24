package com.nttdata.bootcamp.msclient.service;

import com.nttdata.bootcamp.msclient.dto.CreditCardDTO;
import com.nttdata.bootcamp.msclient.dto.LoanDTO;
import reactor.core.publisher.Flux;

public interface LoanService {

    Flux<LoanDTO> findAllById(Integer id);

}
