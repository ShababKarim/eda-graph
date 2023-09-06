package com.edagraph.marketingservice.feature.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edagraph.marketingservice.feature.ClientLead;
import com.edagraph.marketingservice.feature.command.AddClientDetails;
import com.edagraph.marketingservice.feature.service.ClientLeadService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientLeadController {

    private final ClientLeadService clientLeadService;

    @GetMapping("/")
    public Flux<ClientLead> getAll() {
        return this.clientLeadService.getAll();
    }

    @PostMapping("/new")
    public Mono<ClientLead> newClient(@RequestBody AddClientDetails command) {
        return this.clientLeadService.addClientDetails(command);
    }
}
