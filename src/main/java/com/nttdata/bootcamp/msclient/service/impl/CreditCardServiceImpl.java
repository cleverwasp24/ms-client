package com.nttdata.bootcamp.msclient.service.impl;

import com.nttdata.bootcamp.msclient.dto.CreditCardDTO;
import com.nttdata.bootcamp.msclient.service.CreditCardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Log4j2
@Service
public class CreditCardServiceImpl implements CreditCardService {

    private final WebClient webClient;

    public CreditCardServiceImpl(WebClient.Builder webClientBuilder) {
        //microservicio credit
        this.webClient = webClientBuilder.baseUrl("http://localhost:8083").build();
    }

    public Flux<CreditCardDTO> findAllById(Integer id) {
        Flux<CreditCardDTO> findAllById = this.webClient.get()
                .uri("/bootcamp/creditcard/findAllByClientId/{id}", id)
                .retrieve()
                .bodyToFlux(CreditCardDTO.class);

        log.info("Credit cards obtained from service ms-credit:" + findAllById);
        return findAllById;
    }

}
