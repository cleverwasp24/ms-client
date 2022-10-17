package com.nttdata.bootcamp.msclient.service;

import com.nttdata.bootcamp.msclient.dto.BusinessClientDTO;
import com.nttdata.bootcamp.msclient.dto.PersonalClientDTO;
import com.nttdata.bootcamp.msclient.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    Flux<Client> findAll();

    Mono<Client> create(Client client);

    Mono<Client> findById(Integer id);

    Mono<Client> update(Integer id, Client client);

    Mono<Void> delete(Integer id);

    Mono<PersonalClientDTO> createPersonalClient(PersonalClientDTO clientDTO);

    Mono<BusinessClientDTO> createBusinessClient(BusinessClientDTO clientDTO);

}
