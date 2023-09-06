package com.edagraph.marketingservice.feature.function.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class ClientLeadClosedDtoDeserializer extends StdDeserializer<ClientLeadClosedDto> {

    public ClientLeadClosedDtoDeserializer() {
        this(null);
    }

    public ClientLeadClosedDtoDeserializer(Class<ClientLeadClosedDto> vc) {
        super(vc);
    }

    @Override
    public ClientLeadClosedDto deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JacksonException {
        ObjectCodec codec = p.getCodec();
        JsonNode rootNode = codec.readTree(p);

        JsonNode dataNode = rootNode.at("/data");

        return ClientLeadClosedDto.builder()
                .name(rootNode.at("/name").asText())
                .traceId(rootNode.at("/traceId").asText())
                .timestamp(LocalDateTime.parse(rootNode.at("/timestamp").asText()))
                .data(ClientLeadClosedDto.ClientLead.builder()
                        .clientId(dataNode.at("/clientId/id").asText())
                        .firstName(dataNode.at("/firstName").asText())
                        .phoneNumber(dataNode.at("/phoneNumber").asText())
                        .converted(dataNode.at("/converted").asBoolean())
                        .purchaseDetails(convertNodeToMap(dataNode.at("/purchaseDetails")))
                        .build())
                .build();
    }

    private Map<String, String> convertNodeToMap(JsonNode mapNode) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(mapNode.fields(), Spliterator.ORDERED), false)
                .collect(Collectors.toMap(kv -> kv.getKey(), kv -> kv.getValue().asText()));
    }
}
