package com.edagraph.graphservice.feature.function.domain;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

public class EventDtoDeserializer extends StdDeserializer<EventDto> {

    public EventDtoDeserializer() {
        this(null);
    }

    public EventDtoDeserializer(Class<EventDto> vc) {
        super(vc);
    }

    @Override
    public EventDto deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JacksonException {
        ObjectCodec codec = p.getCodec();
        JsonNode rootNode = codec.readTree(p);

        return EventDto.builder()
                .name(rootNode.at("/name").asText())
                .traceId(rootNode.at("/traceId").asText())
                .timestamp(LocalDateTime.parse(rootNode.at("/timestamp").asText()))
                .payload(rootNode.at("/data").asText())
                .build();
    }
}
