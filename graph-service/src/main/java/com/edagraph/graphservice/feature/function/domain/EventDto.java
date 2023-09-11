package com.edagraph.graphservice.feature.function.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class EventDto {
    private String name, traceId;
    private LocalDateTime timestamp;
    private String payload;
}
