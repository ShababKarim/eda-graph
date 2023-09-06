package com.edagraph.marketingservice.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.edagraph.marketingservice.feature.function.domain.ClientLeadClosedDto;
import com.edagraph.marketingservice.feature.function.domain.ClientLeadClosedDtoDeserializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
public class MainConfig {

    @Bean
    ObjectMapper getObjectMapper() {
        var objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(customDeserializersModule());

        return objectMapper;
    }

    private SimpleModule customDeserializersModule() {
        var simpleModule = new SimpleModule();

        simpleModule.addDeserializer(ClientLeadClosedDto.class, new ClientLeadClosedDtoDeserializer());

        return simpleModule;
    }
}
