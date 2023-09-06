package com.edagraph.salesservice.feature.service;

import com.edagraph.salesservice.feature.ClientLead;

import reactor.core.publisher.Mono;

public interface SalesService {
    Mono<Boolean> attempSale(ClientLead clientLead);
}
