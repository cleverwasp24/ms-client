package com.nttdata.bootcamp.msclient.service.impl;

import com.nttdata.bootcamp.msclient.dto.*;
import com.nttdata.bootcamp.msclient.infrastructure.ClientRepository;
import com.nttdata.bootcamp.msclient.dto.mapper.ClientDTOMapper;
import com.nttdata.bootcamp.msclient.model.Client;
import com.nttdata.bootcamp.msclient.model.enums.ClientTypeEnum;
import com.nttdata.bootcamp.msclient.service.AccountService;
import com.nttdata.bootcamp.msclient.service.ClientService;
import com.nttdata.bootcamp.msclient.service.CreditCardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private LoanServiceImpl loanService;

    private ClientDTOMapper clientDTOMapper = new ClientDTOMapper();

    @Override
    public Flux<Client> findAll() {
        log.info("Listing all clients");
        return clientRepository.findAll();
    }

    @Override
    public Mono<Client> create(Client client) {
        log.info("Creating client: " + client.toString());
        return clientRepository.save(client);
    }

    public Mono<String> createPersonalClient(PersonalClientDTO clientDTO) {
        log.info("Creating personal client: " + clientDTO.toString());
        Client client = clientDTOMapper.convertToEntity(clientDTO, ClientTypeEnum.PERSONAL);
        return checkFieldsPersonalClient(clientDTO)
                .switchIfEmpty(clientRepository.save(client)
                        .then(Mono.just("Personal Client created! "
                                + clientDTOMapper.convertToDto(client, ClientTypeEnum.PERSONAL))));
    }

    public Mono<String> createBusinessClient(BusinessClientDTO clientDTO) {
        log.info("Creating business client: " + clientDTO.toString());
        Client client = clientDTOMapper.convertToEntity(clientDTO, ClientTypeEnum.BUSINESS);
        return checkFieldsBusinessClient(clientDTO)
                .switchIfEmpty(clientRepository.save(client)
                        .then(Mono.just("Business Client"
                                + clientDTOMapper.convertToDto(client, ClientTypeEnum.BUSINESS))));
    }

    @Override
    public Mono<Client> findById(Integer id) {
        log.info("Searching client by id: " + id);
        return clientRepository.findById(id);
    }

    @Override
    public Mono<Client> update(Integer id, Client client) {
        log.info("Updating client with id: " + id + " with : " + client.toString());
        return clientRepository.findById(id)
                .flatMap(c -> {
                    client.setId(id);
                    return clientRepository.save(client);
                });
    }

    @Override
    public Mono<Void> delete(Integer id) {
        log.info("Deleting client with id: " + id);
        return clientRepository.deleteById(id);
    }

    @Override
    public Mono<String> checkFieldsPersonalClient(PersonalClientDTO clientDTO) {
        if (clientDTO.getDocType() == null) {
            return Mono.error(new IllegalArgumentException("Client document type cannot be empty"));
        }
        if (clientDTO.getDocNumber() == null || clientDTO.getDocNumber().trim().equals("")) {
            return Mono.error(new IllegalArgumentException("Client document number cannot be empty"));
        }
        if (clientDTO.getFirstName() == null || clientDTO.getFirstName().trim().equals("")) {
            return Mono.error(new IllegalArgumentException("Client first name cannot be empty"));
        }
        if (clientDTO.getLastName() == null || clientDTO.getLastName().trim().equals("")) {
            return Mono.error(new IllegalArgumentException("Client last name cannot be empty"));
        }
        return clientRepository.findById(clientDTO.getId())
                .flatMap(c -> Mono.error(new IllegalArgumentException("Client id already exists")));
    }

    @Override
    public Mono<String> checkFieldsBusinessClient(BusinessClientDTO clientDTO) {
        if (clientDTO.getDocType() == null) {
            return Mono.error(new IllegalArgumentException("Client document type cannot be empty"));
        }
        if (clientDTO.getDocNumber() == null || clientDTO.getDocNumber().trim().equals("")) {
            return Mono.error(new IllegalArgumentException("Client document number cannot be empty"));
        }
        if (clientDTO.getCompanyName() == null || clientDTO.getCompanyName().trim().equals("")) {
            return Mono.error(new IllegalArgumentException("Client company name cannot be empty"));
        }
        return clientRepository.findById(clientDTO.getId())
                .flatMap(c -> Mono.error(new IllegalArgumentException("Client id already exists")));
    }

    @Override
    public Mono<ProductDTO> findAllProductsById(Integer id) {
        log.info("Listing all products by client id: " + id);
        Mono<ProductDTO> productDTOMono = Mono.just(new ProductDTO());
        Flux<AccountDTO> accounts = accountService.findAllById(id);
        Flux<CreditCardDTO> creditCards = creditCardService.findAllById(id);
        Flux<LoanDTO> loans = loanService.findAllById(id);
        return productDTOMono.flatMap(p1 -> accounts.collectList().map(a -> {
            p1.setAccounts(a);
            return p1;
        }).flatMap(p2 -> creditCards.collectList().map(c -> {
            p2.setCreditCards(c);
            return p2;
        }).flatMap(p3 -> loans.collectList().map(l -> {
            p3.setLoans(l);
            return p3;
        }))));


    }

    /*@Override
    public Mono<ProductDTO> findAllProductsById(Integer id) {
        log.info("Listing all products by client id: " + id);
        ProductDTO productDTO = new ProductDTO();
        accountService.findAllById(id).flatMap(accountDTO -> {
            productDTO.getAccounts().add(accountDTO);
            return null;
        });
        creditCardService.findAllById(id).flatMap(creditCardDTO -> {
            productDTO.getCreditCards().add(creditCardDTO);
            return null;
        });
        loanService.findAllById(id).flatMap(loanDTO -> {
            productDTO.getLoans().add(loanDTO);
            return null;
        });
        return Mono.just(productDTO);
    }*/
}
