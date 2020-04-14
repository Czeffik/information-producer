package com.trzewik.information.producer.interfaces;

import com.trzewik.information.producer.interfaces.rest.RestInterfacesConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    RestInterfacesConfiguration.class
})
@Configuration
public class InterfacesConfiguration {
}
