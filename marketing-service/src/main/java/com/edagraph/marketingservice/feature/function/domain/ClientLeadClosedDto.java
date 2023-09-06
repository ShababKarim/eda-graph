package com.edagraph.marketingservice.feature.function.domain;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientLeadClosedDto {
    private String name, traceId;
    private LocalDateTime timestamp;
    private ClientLead data;

    @Data
    @Builder
    public static class ClientLead {
        private String clientId;
        private String firstName, phoneNumber;
        private Boolean converted;
        private Map<String, String> purchaseDetails;
    }
}
