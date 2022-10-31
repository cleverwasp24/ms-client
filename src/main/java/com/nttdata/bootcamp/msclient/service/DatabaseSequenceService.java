package com.nttdata.bootcamp.msclient.service;

import reactor.core.publisher.Mono;

public interface DatabaseSequenceService {

    Mono<Long> generateSequence(String seqName);

}
