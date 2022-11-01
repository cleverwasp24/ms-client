package com.nttdata.bootcamp.msclient.service;

import com.nttdata.bootcamp.msclient.dto.CreditCardDTO;
import reactor.core.publisher.Flux;

public interface CreditCardService {

    Flux<CreditCardDTO> findAllById(Long id);

}