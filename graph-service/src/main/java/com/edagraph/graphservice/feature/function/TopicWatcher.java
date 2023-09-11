package com.edagraph.graphservice.feature.function;

import com.edagraph.graphservice.feature.Event;
import com.edagraph.graphservice.feature.function.domain.EventDto;
import com.edagraph.graphservice.feature.service.EventQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Component("topicWatcher")
@RequiredArgsConstructor
@Slf4j
public class TopicWatcher implements Consumer<Flux<String>> {

    private final ObjectMapper objectMapper;
    private final EventQueryService eventQueryService;

    @Override
    public void accept(Flux<String> eventFlux) {
        eventFlux
                .flatMap(event -> Mono.fromCallable(() -> objectMapper.readValue(event, EventDto.class)))
                .doOnNext(event -> log.info("Received event [Name|Trace ID|Timestamp] : [{}|{}|{}]",
                        event.getName(),
                        event.getTraceId(),
                        event.getTimestamp().toString()))
                .map(this::convert)
                .flatMap(eventQueryService::updateEvent)
                .onErrorContinue((t, o) -> log.error("Error processing event from topic list with cause: {}", t.getMessage()))
                .subscribe();
    }

    private Event convert(EventDto eventDto) {
        return new Event(
                Event.generateId(eventDto.getName(), eventDto.getTimestamp()),
                eventDto.getName(),
                eventDto.getTraceId(),
                eventDto.getTimestamp(),
                eventDto.getPayload()
        );
    }
}
