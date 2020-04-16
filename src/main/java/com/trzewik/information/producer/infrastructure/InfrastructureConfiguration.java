package com.trzewik.information.producer.infrastructure;

import com.trzewik.information.producer.infrastructure.db.DbConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    DbConfiguration.class
})
@Configuration
public class InfrastructureConfiguration {
}
