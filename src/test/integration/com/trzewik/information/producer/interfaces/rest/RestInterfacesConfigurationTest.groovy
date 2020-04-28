package com.trzewik.information.producer.interfaces.rest

import com.trzewik.information.producer.domain.information.InformationService
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import spock.mock.DetachedMockFactory

@Profile('rest-interface-test-config')
@TestConfiguration
class RestInterfacesConfigurationTest {

    DetachedMockFactory factory = new DetachedMockFactory()

    @Bean
    InformationService informationServiceMock() {
        return factory.Mock(InformationService)
    }

}
