package com.edagraph.salesservice.feature.service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

import com.edagraph.salesservice.core.event.Event;
import com.edagraph.salesservice.core.repository.LocalRepository;
import com.edagraph.salesservice.feature.ClientLead;
import com.edagraph.salesservice.feature.command.ConvertClient;
import com.edagraph.salesservice.feature.command.LoseClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultClientLeadService implements ClientLeadService {

    private static final Duration CALL_WAIT_TIME = Duration.ofSeconds(5);

    private final SalesService salesService;
    private final LocalRepository<ClientLead> clientLeadRepository;

    @Override
    public Mono<Event<ClientLead>> performClosing(ClientLead clientLead) {
        return Mono.just(clientLead)
                .doOnNext(cl -> log.info("Waiting to call {} after 5 second(s) for sales attempt...",
                        cl.getClientId().id()))
                .delayElement(CALL_WAIT_TIME)
                .flatMap(cl -> salesService.attempSale(cl)
                        .filter(saleSucceded -> saleSucceded)
                        .map(saleSucceded -> generateConvertedClientEvent(cl))
                        .switchIfEmpty(Mono.just(generateClientLostEvent(cl))))
                .flatMap(event -> clientLeadRepository.save(event.getData())
                        .doOnNext(c -> log.info("Saved ClientLead {} as {}",
                                event.getData().getClientId().id(),
                                event.getName()))
                        .thenReturn(event));
    }

    private Event<ClientLead> generateConvertedClientEvent(ClientLead clientLead) {
        var closedClient = clientLead.execute(new ConvertClient(getMockPurchaseOrder()));

        return ClientLead.generateEvent(ClientLead.CLIENT_LEAD_CLOSED, closedClient);
    }

    private Event<ClientLead> generateClientLostEvent(ClientLead clientLead) {
        var lostClient = clientLead.execute(new LoseClient());

        return ClientLead.generateEvent(ClientLead.CLIENT_LEAD_LOST, lostClient);
    }

    private Map<String, String> getMockPurchaseOrder() {
        return new HashMap<>() {
            {
                put("product1", "123");
            }
        };
    }
}
