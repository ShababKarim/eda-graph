package com.edagraph.graphservice.feature.service;

import com.edagraph.graphservice.feature.Event;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Mono;

public interface EventQueryService {
    Integer DEFAULT_PAGE_NUMBER = 0;
    Integer DEFAULT_PAGE_SIZE = 10;
    Sort DEFAULT_SORT_DIRECTION = Sort.by("timestamp").ascending();

    ExampleMatcher EXAMPLE_MATCHER = ExampleMatcher.matching()
            .withIgnorePaths("id", "timestamp", "payload")
            .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::exact)
            .withMatcher("traceId", ExampleMatcher.GenericPropertyMatcher::exact)
            .withIgnoreNullValues();

    Mono<Event> updateEvent(Event event);
    Mono<Page<Event>> getEvents(Event example, PageRequest pageRequest);
}
