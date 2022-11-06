package com.nttdata.bootcamp.msclient.service.impl;

import com.nttdata.bootcamp.msclient.dto.*;
import com.nttdata.bootcamp.msclient.infrastructure.ClientRepository;
import com.nttdata.bootcamp.msclient.dto.mapper.ClientDTOMapper;
import com.nttdata.bootcamp.msclient.model.Client;
import com.nttdata.bootcamp.msclient.model.enums.ClientTypeEnum;
import com.nttdata.bootcamp.msclient.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    private DebitCardService debitCardService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private DatabaseSequenceService databaseSequenceService;

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
        return checkFields(client)
                .switchIfEmpty(databaseSequenceService.generateSequence(Client.SEQUENCE_NAME).flatMap(sequence -> {
                    client.setId(sequence);
                    return clientRepository.save(client)
                            .flatMap(c -> Mono.just("Personal Client created! " + clientDTOMapper.convertToDto(c, ClientTypeEnum.PERSONAL)))
                            .onErrorMap(e -> new RuntimeException("Error creating personal client" + e.getMessage()));
                }));
    }

    public Mono<String> createBusinessClient(BusinessClientDTO clientDTO) {
        log.info("Creating business client: " + clientDTO.toString());
        Client client = clientDTOMapper.convertToEntity(clientDTO, ClientTypeEnum.BUSINESS);
        return checkFields(client)
                .switchIfEmpty(databaseSequenceService.generateSequence(Client.SEQUENCE_NAME).flatMap(sequence -> {
                    client.setId(sequence);
                    return clientRepository.save(client)
                            .flatMap(c -> Mono.just("Business Client created! " + clientDTOMapper.convertToDto(c, ClientTypeEnum.BUSINESS)))
                            .onErrorMap(e -> new RuntimeException("Error creating business client" + e.getMessage()));
                }));
    }

    @Override
    public Mono<Client> findById(Long id) {
        log.info("Searching client by id: " + id);
        return clientRepository.findById(id);
    }

    @Override
    public Mono<Client> update(Long id, Client client) {
        log.info("Updating client with id: " + id + " with : " + client.toString());
        return clientRepository.findById(id).flatMap(c -> {
            client.setId(id);
            return clientRepository.save(client);
        });
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.info("Deleting client with id: " + id);
        return clientRepository.deleteById(id);
    }

    @Override
    public Mono<String> checkFields(Client client) {
        if (client.getDocType() == null) {
            return Mono.error(new IllegalArgumentException("Client document type cannot be empty"));
        }
        if (client.getDocNumber() == null || client.getDocNumber().trim().equals("")) {
            return Mono.error(new IllegalArgumentException("Client document number cannot be empty"));
        }
        switch (ClientTypeEnum.valueOf(client.getClientType())) {
            case PERSONAL:
                if (client.getFirstName() == null || client.getFirstName().trim().equals("")) {
                    return Mono.error(new IllegalArgumentException("Client first name cannot be empty"));
                }
                if (client.getLastName() == null || client.getLastName().trim().equals("")) {
                    return Mono.error(new IllegalArgumentException("Client last name cannot be empty"));
                }
                break;
            case BUSINESS:
                if (client.getCompanyName() == null || client.getCompanyName().trim().equals("")) {
                    return Mono.error(new IllegalArgumentException("Client company name cannot be empty"));
                }
                break;
        }
        return Mono.empty();
    }

    @Override
    public Mono<ProductDTO> findAllProductsById(Long id) {
        log.info("Listing all products by client id: " + id);
        Mono<ProductDTO> productDTOMono = Mono.just(new ProductDTO());
        Flux<AccountDTO> accounts = accountService.findAllById(id);
        Flux<CreditCardDTO> creditCards = creditCardService.findAllById(id);
        Flux<DebitCardDTO> debitCards = debitCardService.findAllById(id);
        Flux<LoanDTO> loans = loanService.findAllById(id);
        return productDTOMono.flatMap(p1 -> accounts.collectList().map(a -> {
            p1.setAccounts(a);
            return p1;
        }).flatMap(p2 -> creditCards.collectList().map(cc -> {
            p2.setCreditCards(cc);
            return p2;
        }).flatMap(p3 -> debitCards.collectList().map(dc -> {
            p3.setDebitCards(dc);
            return p3;
        }).flatMap(p4 -> loans.collectList().map(l -> {
            p4.setLoans(l);
            return p4;
        })))));
    }

}
