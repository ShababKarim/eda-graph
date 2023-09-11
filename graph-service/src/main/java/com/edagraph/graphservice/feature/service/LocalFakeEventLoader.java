package com.edagraph.graphservice.feature.service;

import com.edagraph.graphservice.feature.Event;
import com.edagraph.graphservice.feature.repository.EventRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Profile("local-testing")
@RequiredArgsConstructor
@Slf4j
public class LocalFakeEventLoader implements CommandLineRunner {
    private static final List<String> EVENT_NAMES = List.of("A", "B", "C", "D", "E");
    private static final List<String> TRACE_IDS = List.of("1", "2", "3", "4", "5", "6", "7");


    private final EventRepository eventRepository;
    private final Faker faker;

    @Override
    public void run(String... args) throws Exception {
        Flux.range(0, 50)
                .map(num -> getEvent(num))
                .doOnNext(event -> log.info("Generated fake event {}", event.getId()))
                .collectList()
                .flatMap(events -> Mono.fromCallable(() -> eventRepository.saveAll(events))
                        .doOnNext(e -> log.info("Saved {} fake events", events.size())))
                .block();
    }

    private Event getEvent(Integer num) {
        var name = EVENT_NAMES.get(num % 5);
        var ldt = LocalDateTime.now();

        return new Event(
                Event.generateId(name, ldt),
                name,
                TRACE_IDS.get(num % 7),
                ldt,
                faker.funnyName().name()
        );
    }
}
