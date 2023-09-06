package com.edagraph.marketingservice.core.event;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface EventStream {
    <T> Mono<Event<T>> publishEvent(Event<T> data);

    <T> Flux<Event<T>> streamEvents(Class<T> clazz);
}
