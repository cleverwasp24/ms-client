package com.nttdata.bootcamp.msclient.service.impl;

import com.nttdata.bootcamp.msclient.dto.AccountDTO;
import com.nttdata.bootcamp.msclient.dto.LoanDTO;
import com.nttdata.bootcamp.msclient.service.AccountService;
import com.nttdata.bootcamp.msclient.service.LoanService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {

    private final WebClient webClient;

    public AccountServiceImpl(WebClient.Builder webClientBuilder) {
        //microservicio account
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082").build();
    }

    public Flux<AccountDTO> findAllById(Long id) {
        Flux<AccountDTO> findAllById = this.webClient.get()
                .uri("/bootcamp/account/findAllByClientId/{id}", id)
                .retrieve()
                .bodyToFlux(AccountDTO.class);

        log.info("Accounts obtained from service ms-account:" + findAllById);
        return findAllById;
    }

}
