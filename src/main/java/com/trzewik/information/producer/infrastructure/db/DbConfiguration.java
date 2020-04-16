package com.trzewik.information.producer.infrastructure.db;

import com.trzewik.information.producer.domain.information.Information;
import com.trzewik.information.producer.domain.information.InformationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DbConfiguration {

    @Bean
    InformationRepository informationRepository(Map<String, Information> repository) {
        return new InMemoryRepository(repository);
    }

    @Bean
    Map<String, Information> repository() {
        return new HashMap<>();
    }
}
