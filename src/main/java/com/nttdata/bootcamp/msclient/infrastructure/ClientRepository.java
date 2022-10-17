package com.nttdata.bootcamp.msclient.infrastructure;

import com.nttdata.bootcamp.msclient.model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, Integer> {
}
