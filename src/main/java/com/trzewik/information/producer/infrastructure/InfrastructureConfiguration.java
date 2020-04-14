package com.trzewik.information.producer.infrastructure;

import com.trzewik.information.producer.infrastructure.rest.RestInfrastructureConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    RestInfrastructureConfiguration.class
})
@Configuration
public class InfrastructureConfiguration {
}
