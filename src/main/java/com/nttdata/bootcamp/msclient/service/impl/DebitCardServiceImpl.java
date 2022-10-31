package com.nttdata.bootcamp.msclient.service.impl;

import com.nttdata.bootcamp.msclient.dto.DebitCardDTO;
import com.nttdata.bootcamp.msclient.service.DebitCardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Log4j2
@Service
public class DebitCardServiceImpl implements DebitCardService {

    private final WebClient webClient;

    public DebitCardServiceImpl(WebClient.Builder webClientBuilder) {
        //microservicio cards
        this.webClient = webClientBuilder.baseUrl("http://localhost:8084").build();
    }

    @Override
    public Flux<DebitCardDTO> findAllById(Long id) {
        Flux<DebitCardDTO> findAllById = this.webClient.get()
                .uri("/bootcamp/card/findAllDebitByClientId/{id}", id)
                .retrieve()
                .bodyToFlux(DebitCardDTO.class);

        log.info("Debit cards obtained from service ms-card:" + findAllById);
        return findAllById;
    }

}
