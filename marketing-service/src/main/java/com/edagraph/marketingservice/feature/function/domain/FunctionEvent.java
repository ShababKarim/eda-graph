package com.edagraph.marketingservice.feature.function.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FunctionEvent<T> {
    private String name, traceId, timestamp;
    private T data;

    public FunctionEvent(String name, T data) {
        this.name = name;
        this.traceId = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now().toString();
        this.data = data;
    }
}
