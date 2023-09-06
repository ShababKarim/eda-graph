package com.edagraph.marketingservice.feature.service;

import com.edagraph.marketingservice.feature.ClientLead;
import com.edagraph.marketingservice.feature.command.AddClientDetails;
import com.edagraph.marketingservice.feature.command.UpdateClientStatus;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientLeadService {
    Flux<ClientLead> getAll();

    Mono<ClientLead> addClientDetails(AddClientDetails command);

    Mono<ClientLead> updateClientStatus(UpdateClientStatus command);
}
