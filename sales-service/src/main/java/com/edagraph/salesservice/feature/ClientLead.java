package com.edagraph.salesservice.feature;

import java.util.HashMap;
import java.util.Map;

import com.edagraph.salesservice.core.event.Event;
import com.edagraph.salesservice.feature.command.ConvertClient;
import com.edagraph.salesservice.feature.command.LoseClient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class ClientLead {

    public static String CLIENT_LEAD_CLOSED = "CLIENT_LEAD_CLOSED";
    public static String CLIENT_LEAD_LOST = "CLIENT_LEAD_LOST";

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
    // Lastname is not very important when making the sale
    // So I've excluded it from the model :)
    private String firstName, phoneNumber;
    private Boolean converted;
    private Map<String, String> purchaseDetails;

    public ClientLead(String email, String firstName, String phoneNumber) {
        this.clientId = ClientId.fromEmail(email);
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.converted = Boolean.FALSE;
        this.purchaseDetails = new HashMap<>();
    }

    public ClientLead execute(ConvertClient command) {
        return this.toBuilder()
                .purchaseDetails(command.purchaseDetails())
                .converted(true)
                .build();
    }

    public ClientLead execute(LoseClient command) {
        return this.toBuilder()
                .converted(false)
                .build();
    }
}