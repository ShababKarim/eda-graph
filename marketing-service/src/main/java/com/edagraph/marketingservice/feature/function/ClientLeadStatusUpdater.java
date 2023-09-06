package com.edagraph.marketingservice.feature.function;

import java.time.LocalDateTime;
import java.util.function.Function;

import com.edagraph.marketingservice.feature.ClientLead;
import com.edagraph.marketingservice.feature.function.domain.FunctionEvent;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.edagraph.marketingservice.feature.ClientStatus;
import com.edagraph.marketingservice.feature.command.UpdateClientStatus;
import com.edagraph.marketingservice.feature.function.domain.ClientLeadClosedDto;
import com.edagraph.marketingservice.feature.service.ClientLeadService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component("clientLeadStatusUpdater")
@RequiredArgsConstructor
@Slf4j
public class ClientLeadStatusUpdater implements Function<Flux<String>, Flux<Message<String>>> {

    private final ClientLeadService clientLeadService;
    private final ObjectMapper objectMapper;

    @Override
    public Flux<Message<String>> apply(Flux<String> clientLeadClosedFlux) {
        return clientLeadClosedFlux
                .flatMap(clientLeadClosed -> Mono.fromCallable(() -> objectMapper.readValue(clientLeadClosed, ClientLeadClosedDto.class)))
                .doOnNext(dto -> log.info("Received ClientLeadClosed event for {} [{}]",
                        dto.getData().getClientId(),
                        dto.getTraceId()))
                .flatMap(dto -> clientLeadService.updateClientStatus(new UpdateClientStatus(dto.getData().getClientId(), ClientStatus.CONTACTED.getValue()))
                        .map(clientLead -> generateFunctionEvent(ClientLead.CLIENT_LEAD_UPDATED, dto.getTraceId(), clientLead))
                        .flatMap(this::generateMessage));
    }

    private FunctionEvent<ClientLead> generateFunctionEvent(String name, String traceId, ClientLead data) {
        return FunctionEvent.<ClientLead>builder()
                .name(name)
                .traceId(traceId)
                .timestamp(LocalDateTime.now().toString())
                .data(data)
                .build();
    }

    private Mono<Message<String>> generateMessage(FunctionEvent<ClientLead> functionEvent) {
        return Mono.fromCallable(() -> objectMapper.writeValueAsString(functionEvent))
                .map(functionEventJson -> MessageBuilder.withPayload(functionEventJson)
                        .setHeader(KafkaHeaders.KEY, functionEvent.getData().getClientId().id())
                        .build());
    }
}
