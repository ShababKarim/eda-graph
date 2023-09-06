package com.edagraph.salesservice.feature.function.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientLeadUpdatedDto {
    private String name, traceId;
    private LocalDateTime timestamp;
    private ClientLead data;

    @Data
    @Builder
    public static class ClientLead {
        private String clientId;
        private String firstName, lastName, phoneNumber;
        private String status;
    }
}
