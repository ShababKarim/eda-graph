package com.edagraph.graphservice.feature.service;

import com.edagraph.graphservice.feature.Event;
import com.edagraph.graphservice.feature.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultEventQueryService implements EventQueryService {

    private final EventRepository eventRepository;

    @Override
    public Mono<Event> updateEvent(Event event) {
        return Mono.fromCallable(() -> event)
                .doOnNext(e -> log.info("Saved event [Name|Trace ID|Timestamp] : [{}|{}|{}]",
                        event.getName(),
                        event.getTraceId(),
                        event.getTimestamp().toString()));
    }

    @Override
    public Mono<Page<Event>> getEvents(Event probe, PageRequest pageRequest) {
        return Mono.fromCallable(() -> eventRepository.findAll(getExample(probe), pageRequest))
                .doOnNext(page -> log.info("Retrieved {} event(s) for page {} ", page.getNumberOfElements(), page.getNumber()));
    }

    private Example<Event> getExample(Event probe) {
        return Example.of(probe, EXAMPLE_MATCHER);
    }
}
