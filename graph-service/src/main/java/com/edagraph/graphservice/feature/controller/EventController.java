package com.edagraph.graphservice.feature.controller;

import com.edagraph.graphservice.feature.Event;
import com.edagraph.graphservice.feature.controller.domain.PageRequestDto;
import com.edagraph.graphservice.feature.service.EventQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event")
@Slf4j
public class EventController {

    private final EventQueryService eventQueryService;

    @PostMapping("/query")
    public Mono<Page<Event>> queryByExample(@RequestBody PageRequestDto<Event> dto) {
        return Mono.fromCallable(() -> getPageRequest(dto))
                .doOnNext(pageRequest -> log.info("Retrieving page of [{}:{}] event(s)", pageRequest.getPageNumber(), pageRequest.getPageSize()))
                .flatMap(pageRequest -> eventQueryService.getEvents(dto.example(), pageRequest));
    }

    private PageRequest getPageRequest(PageRequestDto<Event> dto) {
        return PageRequest.of(
                Optional.ofNullable(dto.pageNumber()).orElse(EventQueryService.DEFAULT_PAGE_NUMBER),
                Optional.ofNullable(dto.pageSize()).orElse(EventQueryService.DEFAULT_PAGE_SIZE),
                EventQueryService.DEFAULT_SORT_DIRECTION
        );
    }
}
