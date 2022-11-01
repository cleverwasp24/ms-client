package com.nttdata.bootcamp.msclient.service;

import com.nttdata.bootcamp.msclient.dto.AccountDTO;
import com.nttdata.bootcamp.msclient.dto.BusinessClientDTO;
import com.nttdata.bootcamp.msclient.dto.PersonalClientDTO;
import com.nttdata.bootcamp.msclient.dto.ProductDTO;
import com.nttdata.bootcamp.msclient.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    Flux<Client> findAll();

    Mono<Client> create(Client client);

    Mono<Client> findById(Long id);

    Mono<Client> update(Long id, Client client);

    Mono<Void> delete(Long id);

    Mono<String> createPersonalClient(PersonalClientDTO clientDTO);

    Mono<String> createBusinessClient(BusinessClientDTO clientDTO);

    Mono<String> checkFields(Client client);

    Mono<ProductDTO> findAllProductsById(Long id);

}
