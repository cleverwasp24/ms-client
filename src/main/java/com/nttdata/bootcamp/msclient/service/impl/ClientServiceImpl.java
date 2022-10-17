package com.nttdata.bootcamp.msclient.service.impl;

import com.nttdata.bootcamp.msclient.dto.BusinessClientDTO;
import com.nttdata.bootcamp.msclient.dto.PersonalClientDTO;
import com.nttdata.bootcamp.msclient.infrastructure.ClientRepository;
import com.nttdata.bootcamp.msclient.mapper.ClientDTOMapper;
import com.nttdata.bootcamp.msclient.model.Client;
import com.nttdata.bootcamp.msclient.model.enums.ClientTypeEnum;
import com.nttdata.bootcamp.msclient.service.ClientService;
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

    public Mono<PersonalClientDTO> createPersonalClient(PersonalClientDTO clientDTO) {
        log.info("Creating personal client: " + clientDTO.toString());
        Client client = clientDTOMapper.convertToEntity(clientDTO, ClientTypeEnum.PERSONAL);
        return clientRepository.save(client)
                .map(c -> (PersonalClientDTO) clientDTOMapper.convertToDto(c, ClientTypeEnum.PERSONAL));
    }

    public Mono<BusinessClientDTO> createBusinessClient(BusinessClientDTO clientDTO) {
        log.info("Creating business client: " + clientDTO.toString());
        Client client = clientDTOMapper.convertToEntity(clientDTO, ClientTypeEnum.BUSINESS);
        return clientRepository.save(client)
                .map(c -> (BusinessClientDTO) clientDTOMapper.convertToDto(c, ClientTypeEnum.BUSINESS));
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
                .flatMap(savedClient -> {
                    savedClient.setClientType(client.getClientType());
                    savedClient.setDocType(client.getDocType());
                    savedClient.setDocNumber(client.getDocNumber());
                    savedClient.setFirstName(client.getFirstName());
                    savedClient.setLastName(client.getLastName());
                    savedClient.setCompanyName(client.getCompanyName());
                    savedClient.setEmail(client.getEmail());
                    return clientRepository.save(savedClient);
                });
    }

    @Override
    public Mono<Void> delete(Integer id) {
        log.info("Deleting client with id: " + id);
        return clientRepository.deleteById(id);
    }
}
