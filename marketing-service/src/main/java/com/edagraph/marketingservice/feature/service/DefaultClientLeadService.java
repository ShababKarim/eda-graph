package com.edagraph.marketingservice.feature.service;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.edagraph.marketingservice.core.event.Event;
import com.edagraph.marketingservice.core.event.EventStream;
import com.edagraph.marketingservice.core.repository.LocalRepository;
import com.edagraph.marketingservice.feature.ClientLead;
import com.edagraph.marketingservice.feature.command.AddClientDetails;
import com.edagraph.marketingservice.feature.command.UpdateClientStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultClientLeadService implements ClientLeadService {

    private final EventStream eventStream;
    private final LocalRepository<ClientLead> clientLeadRepository;

    @Override
    public Flux<ClientLead> getAll() {
        return this.clientLeadRepository.findAll()
                .collectList()
                .doOnNext(clientLeads -> log.info("Retrieved {} clientLead(s)", clientLeads.size()))
                .flatMapIterable(Function.identity());
    }

    @Override
    public Mono<ClientLead> addClientDetails(AddClientDetails command) {
        return Mono.fromCallable(() -> new ClientLead(command))
                .flatMap(clientLead -> this.clientLeadRepository.save(clientLead)
                        .doOnNext(cl -> log.info("Saved new clientLead {}", cl.getClientId()))
                        .flatMap(cl -> eventStream
                                .publishEvent(ClientLead.generateEvent(ClientLead.CLIENT_LEAD_GENERATED, cl)))
                        .map(Event::getData))
                .doOnError(err -> log.error("Error creating and saving new client lead with cause {}", err.getMessage()));
    }

    @Override
    public Mono<ClientLead> updateClientStatus(UpdateClientStatus command) {
        return this.clientLeadRepository.findById(command.email())
                .map(clientLead -> clientLead.execute(command))
                .flatMap(clientLead -> this.clientLeadRepository.save(clientLead))
                .doOnNext(cl -> log.info("Updated existing clientLead {} with status {}",
                        cl.getClientId(),
                        cl.getStatus().getValue()))
                .doOnError(err -> log.error("Error creating and saving new client lead with cause {}",
                        err.getMessage()));
    }
}
