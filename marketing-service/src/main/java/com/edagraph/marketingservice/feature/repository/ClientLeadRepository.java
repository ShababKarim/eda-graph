package com.edagraph.marketingservice.feature.repository;

import org.springframework.stereotype.Component;

import com.edagraph.marketingservice.core.repository.LocalRepository;
import com.edagraph.marketingservice.feature.ClientLead;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.stream.*;

@Component
public class ClientLeadRepository implements LocalRepository<ClientLead> {

    private List<ClientLead> clientLeads = List.of();

    @Override
    public Flux<ClientLead> findAll() {
        return Flux.fromIterable(this.clientLeads);
    }

    @Override
    public Mono<ClientLead> findById(String id) {
        return Mono.justOrEmpty(this.clientLeads.stream()
                .filter(clientLead -> clientLead.getClientId().id().equalsIgnoreCase(id))
                .findFirst());
    }

    @Override
    public Mono<ClientLead> save(ClientLead data) {
        this.clientLeads = Stream.concat(
                this.clientLeads.stream()
                        .filter(clientLead -> !clientLead.getClientId().id().equalsIgnoreCase(data.getClientId().id())),
                Stream.of(data))
                .toList();

        return Mono.just(data);
    }
}
