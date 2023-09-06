package com.edagraph.salesservice.feature.function.domain;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class ClientLeadUpdatedDtoDeserializer extends StdDeserializer<ClientLeadUpdatedDto> {

    public ClientLeadUpdatedDtoDeserializer() {
        this(null);
    }

    public ClientLeadUpdatedDtoDeserializer(Class<ClientLeadUpdatedDto> vc) {
        super(vc);
    }

    @Override
    public ClientLeadUpdatedDto deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JacksonException {
        ObjectCodec codec = p.getCodec();
        JsonNode rootNode = codec.readTree(p);

        JsonNode dataNode = rootNode.at("/data");

        return ClientLeadUpdatedDto.builder()
                .name(rootNode.at("/name").asText())
                .traceId(rootNode.at("/traceId").asText())
                .timestamp(LocalDateTime.parse(rootNode.at("/timestamp").asText()))
                .data(ClientLeadUpdatedDto.ClientLead.builder()
                        .clientId(dataNode.at("/clientId/id").asText())
                        .firstName(dataNode.at("/firstName").asText())
                        .lastName(dataNode.at("/lastName").asText())
                        .phoneNumber(dataNode.at("/phoneNumber").asText())
                        .status(dataNode.at("/status").asText())
                        .build())
                .build();
    }
}
