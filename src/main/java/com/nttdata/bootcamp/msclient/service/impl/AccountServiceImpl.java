package com.nttdata.bootcamp.msclient.service.impl;

import com.nttdata.bootcamp.msclient.dto.AccountDTO;
import com.nttdata.bootcamp.msclient.dto.LoanDTO;
import com.nttdata.bootcamp.msclient.service.AccountService;
import com.nttdata.bootcamp.msclient.service.LoanService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {

    private final WebClient webClient;

    public AccountServiceImpl(WebClient.Builder webClientBuilder) {
        //microservice gateway (account)
        this.webClient = webClientBuilder.baseUrl("http://ms-gateway:8088").build();
    }

    @CircuitBreaker(name = "service-account", fallbackMethod = "findAllByIdFallback")
    @TimeLimiter(name = "service-account")
    @Override
    public Flux<AccountDTO> findAllById(Long id) {
        return this.webClient.get()
                .uri("/bootcamp/account/findAllByClientId/{id}", id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Error " + clientResponse.statusCode())))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Error " + clientResponse.statusCode())))
                .bodyToFlux(AccountDTO.class);
    }

    public Flux<AccountDTO> findAllByIdFallback(Long id, Throwable t) {
        log.info("Fallback method for findAllByIdFallback (ACCOUNT) executed {}", t.getMessage());
        return Flux.empty();
    }

}
