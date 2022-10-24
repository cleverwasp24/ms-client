package com.nttdata.bootcamp.msclient.service.impl;

import com.nttdata.bootcamp.msclient.dto.CreditCardDTO;
import com.nttdata.bootcamp.msclient.dto.LoanDTO;
import com.nttdata.bootcamp.msclient.service.CreditCardService;
import com.nttdata.bootcamp.msclient.service.LoanService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Log4j2
@Service
public class LoanServiceImpl implements LoanService {

    private final WebClient webClient;

    public LoanServiceImpl(WebClient.Builder webClientBuilder) {
        //microservicio credit
        this.webClient = webClientBuilder.baseUrl("http://localhost:8083").build();
    }

    public Flux<LoanDTO> findAllById(Integer id) {
        Flux<LoanDTO> findAllById = this.webClient.get()
                .uri("/bootcamp/loan/findAllByClientId/{id}", id)
                .retrieve()
                .bodyToFlux(LoanDTO.class);

        log.info("Loans obtained from service ms-credit:" + findAllById);
        return findAllById;
    }

}
