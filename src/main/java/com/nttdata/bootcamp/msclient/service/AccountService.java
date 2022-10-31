package com.nttdata.bootcamp.msclient.service;

import com.nttdata.bootcamp.msclient.dto.AccountDTO;
import com.nttdata.bootcamp.msclient.dto.CreditCardDTO;
import reactor.core.publisher.Flux;

public interface AccountService {

    Flux<AccountDTO> findAllById(Long id);

}
