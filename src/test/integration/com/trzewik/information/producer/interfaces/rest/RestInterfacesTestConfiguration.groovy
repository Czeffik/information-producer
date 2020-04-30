package com.trzewik.information.producer.interfaces.rest

import com.trzewik.information.producer.domain.information.InformationService
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import spock.mock.DetachedMockFactory

@Profile(TEST_CONFIG)
@TestConfiguration
class RestInterfacesTestConfiguration {

    public final static String TEST_CONFIG = 'rest-interfaces-test-config'

    DetachedMockFactory factory = new DetachedMockFactory()

    @Bean
    InformationService informationServiceMock() {
        return factory.Mock(InformationService)
    }

}
