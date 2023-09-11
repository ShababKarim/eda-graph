package com.edagraph.graphservice.feature;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "event")
public class Event {
    public static String generateId(String name, LocalDateTime timestamp) {
        if (name == null || timestamp == null) {
            throw new RuntimeException("Event name and/or timestamp cannot be null");
        }

        return name
                .concat("-")
                .concat(timestamp.toString());
    }

    @Id
    private String id;
    private String name, traceId;
    private LocalDateTime timestamp;
    private String payload;
}
