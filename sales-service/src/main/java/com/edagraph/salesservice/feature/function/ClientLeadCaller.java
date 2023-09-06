package com.edagraph.salesservice.feature.function;

import java.time.LocalDateTime;
import java.util.function.Function;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.edagraph.salesservice.core.event.Event;
import com.edagraph.salesservice.feature.ClientLead;
import com.edagraph.salesservice.feature.function.domain.ClientLeadUpdatedDto;
import com.edagraph.salesservice.feature.function.domain.FunctionEvent;
import com.edagraph.salesservice.feature.service.ClientLeadService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component("clientLeadCaller")
@RequiredArgsConstructor
@Slf4j
public class ClientLeadCaller implements Function<Flux<String>, Flux<Message<String>>> {
    private static final String CLIENT_LEAD_GENERATED = "CLIENT_LEAD_GENERATED";

    private final ObjectMapper objectMapper;
    private final ClientLeadService clientLeadService;

    @Override
    public Flux<Message<String>> apply(Flux<String> clientLeadUpdatedFlux) {
        return clientLeadUpdatedFlux
                .doOnNext(clientLeadUpdated -> log.info("Received incoming ClientLeadUpdated event {}",
                        clientLeadUpdated))
                .flatMap(clientLeadUpdated -> Mono
                        .fromCallable(() -> objectMapper.readValue(clientLeadUpdated, ClientLeadUpdatedDto.class))
                        .filter(dto -> dto.getName().equalsIgnoreCase(CLIENT_LEAD_GENERATED))
                        .flatMap(dto -> Mono.fromCallable(() -> convertToClientLead(dto))
                                .flatMap(clientLead -> clientLeadService.performClosing(clientLead)
                                        .flatMap(event -> generatMessage(dto.getTraceId(), event)))));
    }

    private ClientLead convertToClientLead(ClientLeadUpdatedDto dto) {
        return new ClientLead(
                dto.getData().getClientId(),
                dto.getData().getFirstName(),
                dto.getData().getPhoneNumber());
    }

    private Mono<Message<String>> generatMessage(String traceId, Event<ClientLead> event) {
        return Mono.fromCallable(() -> generateFunctionEvent(event.getName(), traceId, event.getData()))
                .doOnNext(functionEvent -> log.info("Generating and publishing event {} for {} [{}]",
                        event.getName(),
                        event.getData().getClientId().id(),
                        functionEvent.getTraceId()))
                .flatMap(functionEvent -> Mono.fromCallable(() -> objectMapper.writeValueAsString(functionEvent)))
                .map(functionEventJson -> MessageBuilder.withPayload(functionEventJson)
                        .setHeader(KafkaHeaders.KEY, event.getData().getClientId().id())
                        .build());
    }

    private FunctionEvent<ClientLead> generateFunctionEvent(String name, String traceId, ClientLead data) {
        return FunctionEvent.<ClientLead>builder()
                .name(name)
                .traceId(traceId)
                .timestamp(LocalDateTime.now().toString())
                .data(data)
                .build();
    }
}
