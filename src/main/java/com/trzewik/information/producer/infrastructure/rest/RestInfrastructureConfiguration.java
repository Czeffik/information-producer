package com.trzewik.information.producer.infrastructure.rest;

import com.trzewik.information.producer.domain.information.InformationReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestInfrastructureConfiguration {
    @Bean
    InformationReceiver informationReceiver() {
        return new InformationReceiverImpl();
    }
}
