package com.nttdata.bootcamp.msclient.service;

import com.nttdata.bootcamp.msclient.dto.CreditCardDTO;
import com.nttdata.bootcamp.msclient.dto.DebitCardDTO;
import reactor.core.publisher.Flux;

public interface DebitCardService {

    Flux<DebitCardDTO> findAllById(Long id);

}
