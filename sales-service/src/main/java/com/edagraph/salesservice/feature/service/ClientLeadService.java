package com.edagraph.salesservice.feature.service;

import com.edagraph.salesservice.core.event.Event;
import com.edagraph.salesservice.feature.ClientLead;

import reactor.core.publisher.Mono;

public interface ClientLeadService {
    Mono<Event<ClientLead>> performClosing(ClientLead clientLead);
}
