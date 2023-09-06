package com.edagraph.marketingservice.feature.function;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.edagraph.marketingservice.core.event.Event;
import com.edagraph.marketingservice.core.event.EventStream;
import com.edagraph.marketingservice.feature.ClientLead;
import com.edagraph.marketingservice.feature.function.domain.FunctionEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component("clientLeadDetailsUpdater")
@RequiredArgsConstructor
@Slf4j
public class ClientLeadDetailsUpdater implements Supplier<Flux<Message<String>>> {

    private final EventStream eventStream;
    private final ObjectMapper objectMapper;

    @Override
    public Flux<Message<String>> get() {
        return eventStream.streamEvents(ClientLead.class)
                .flatMap(applicationEvent -> generateMessage(applicationEvent));
    }

    private Mono<Message<String>> generateMessage(Event<ClientLead> event) {
        return Mono.fromCallable(() -> generateFunctionEvent(event.getName(), event.getData()))
                .doOnNext(functionEvent -> log.info("Generating and publishing event {} for {} [{}]",
                        functionEvent.getName(),
                        functionEvent.getData().getClientId().id(),
                        functionEvent.getTraceId()))
                .flatMap(functionEvent -> Mono.fromCallable(() -> objectMapper.writeValueAsString(functionEvent)))
                .map(functionEventJson -> MessageBuilder.withPayload(functionEventJson)
                        .setHeader(KafkaHeaders.KEY, event.getData().getClientId().id())
                        .build());
    }

    private FunctionEvent<ClientLead> generateFunctionEvent(String name, ClientLead data) {
        return new FunctionEvent<>(name, data);
    }
}
