package com.edagraph.salesservice.feature.service;

import java.util.Random;
import org.springframework.stereotype.Component;
import com.edagraph.salesservice.feature.ClientLead;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class MockPhoneSalesService implements SalesService {

    private final Random random;

    public MockPhoneSalesService() {
        this.random = new Random();
    }

    @Override
    public Mono<Boolean> attempSale(ClientLead clientLead) {
        return Mono.fromCallable(() -> random.nextBoolean())
                .doFirst(() -> log.info("Attempting to sell to client {}", clientLead.getClientId().id()))
                .doOnNext(isSuccessful -> log.info("Successfully sold to client {}: {}", clientLead.getClientId().id(),
                        isSuccessful));
    }
}
