package com.edagraph.graphservice.core.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local-testing")
public class LocalConfig {

    @Bean
    public Faker faker() {
        return new Faker();
    }
}
