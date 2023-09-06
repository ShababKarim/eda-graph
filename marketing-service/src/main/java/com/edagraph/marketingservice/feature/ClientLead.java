package com.edagraph.marketingservice.feature;

import java.util.Objects;

import com.edagraph.marketingservice.core.event.Event;
import com.edagraph.marketingservice.feature.command.AddClientDetails;
import com.edagraph.marketingservice.feature.command.UpdateClientStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class ClientLead {

    public static String CLIENT_LEAD_GENERATED = "CLIENT_LEAD_GENERATED";
    public static String CLIENT_LEAD_UPDATED = "CLIENT_LEAD_UPDATED";

    public static Event<ClientLead> generateEvent(String eventName, ClientLead data) {
        return new Event<ClientLead>() {
            @Override
            public String getName() {
                return eventName;
            }

            @Override
            public ClientLead getData() {
                return data;
            }

        };
    }

    private ClientId clientId;
    private String firstName, lastName, phoneNumber;
    private ClientStatus status;

    public ClientLead(AddClientDetails command) {
        if (Objects.isNull(command.email()) || Objects.isNull(command.phoneNumber())) {
            throw new RuntimeException("ClientLead email or phoneNumber cannot be null");
        }

        this.clientId = ClientId.fromEmail(command.email());
        this.firstName = command.firstName();
        this.lastName = command.lastName();
        this.phoneNumber = command.phoneNumber();
        this.status = ClientStatus.ADDED;
    }

    public ClientLead execute(UpdateClientStatus command) {
        return this.toBuilder()
                .status(ClientStatus.findValue(command.status()))
                .build();
    }
}
