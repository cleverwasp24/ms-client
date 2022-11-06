package com.nttdata.bootcamp.msclient.service.impl;

import com.nttdata.bootcamp.msclient.dto.CreditCardDTO;
import com.nttdata.bootcamp.msclient.dto.LoanDTO;
import com.nttdata.bootcamp.msclient.service.CreditCardService;
import com.nttdata.bootcamp.msclient.service.LoanService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class LoanServiceImpl implements LoanService {

    private final WebClient webClient;

    public LoanServiceImpl(WebClient.Builder webClientBuilder) {
        //microservicio credit
        this.webClient = webClientBuilder.baseUrl("http://ms-gateway:8088").build();
    }

    @CircuitBreaker(name = "service-loan", fallbackMethod = "findAllByIdFallback")
    @TimeLimiter(name = "service-loan")
    @Override
    public Flux<LoanDTO> findAllById(Long id) {
        return this.webClient.get()
                .uri("/bootcamp/loan/findAllByClientId/{id}", id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Error " + clientResponse.statusCode())))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Error " + clientResponse.statusCode())))
                .bodyToFlux(LoanDTO.class);
    }

    public Flux<LoanDTO> findAllByIdFallback(Long id, Throwable t) {
        log.info("Fallback method for findAllByIdFallback (LOAN) executed {}", t.getMessage());
        return Flux.empty();
    }

}
