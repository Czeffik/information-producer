package com.trzewik.information.producer.domain.information

import spock.lang.Specification

class InformationServiceFactoryUT extends Specification {

    def 'should create information service with given repository'() {
        given:
        def repository = new InformationRepositoryMock()

        when:
        def service = InformationServiceFactory.create(repository)

        then:
        service instanceof InformationService
    }
}
