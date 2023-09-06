package com.edagraph.marketingservice.core.event;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitFailureHandler;

@Component
@Slf4j
public class DefaultEventStream implements EventStream {

    private final Sinks.Many<Event<?>> events = Sinks.many().replay().latest();

    @Override
    public <T> Mono<Event<T>> publishEvent(Event<T> data) {
        return Mono.fromRunnable(() -> events.emitNext(data, EmitFailureHandler.FAIL_FAST))
                .retry(3)
                .thenReturn(data)
                .doOnNext(v -> log.info("Published internal app event {}", data.getName()))
                .onErrorResume(err -> {
                    log.error("Error publishing internal app event {}", err.getMessage());
                    return Mono.empty();
                });
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Flux<Event<T>> streamEvents(Class<T> clazz) {
        return events.asFlux()
                .filter(event -> event.getData().getClass().equals(clazz))
                .map(event -> (Event<T>) event);
    }
}
