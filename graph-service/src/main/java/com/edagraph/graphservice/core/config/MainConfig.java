package com.edagraph.graphservice.core.config;

import com.edagraph.graphservice.feature.function.domain.EventDto;
import com.edagraph.graphservice.feature.function.domain.EventDtoDeserializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {

    @Bean
    ObjectMapper getObjectMapper() {
        var objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(customDeserializersModule());

        return objectMapper;
    }

    private SimpleModule customDeserializersModule() {
        var simpleModule = new SimpleModule();

        simpleModule.addDeserializer(EventDto.class, new EventDtoDeserializer());

        return simpleModule;
    }
}
