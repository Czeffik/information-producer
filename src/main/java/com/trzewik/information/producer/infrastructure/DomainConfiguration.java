package com.trzewik.information.producer.infrastructure;

import com.trzewik.information.producer.domain.information.InformationRepository;
import com.trzewik.information.producer.domain.information.InformationService;
import com.trzewik.information.producer.domain.information.InformationServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {
    @Bean
    InformationService informationService(InformationRepository informationRepository) {
        return InformationServiceFactory.create(informationRepository);
    }
}
