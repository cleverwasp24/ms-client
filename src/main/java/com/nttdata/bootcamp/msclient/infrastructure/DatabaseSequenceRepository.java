package com.nttdata.bootcamp.msclient.infrastructure;

import com.nttdata.bootcamp.msclient.model.DatabaseSequence;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface DatabaseSequenceRepository extends ReactiveMongoRepository<DatabaseSequence, String> {

    Mono<DatabaseSequence> findDatabaseSequenceById(String seqName);

}
