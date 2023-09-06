package com.edagraph.salesservice.core.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LocalRepository<T> {

    Flux<T> findAll();

    Mono<T> findById(String id);

    Mono<T> save(T data);
}
