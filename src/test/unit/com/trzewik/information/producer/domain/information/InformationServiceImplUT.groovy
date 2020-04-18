package com.trzewik.information.producer.domain.information

import spock.lang.Specification
import spock.lang.Subject

class InformationServiceImplUT extends Specification implements InformationFormCreation {
    InformationRepository informationRepository = new InformationRepositoryMock()

    @Subject
    InformationService informationService = InformationServiceFactory.create(informationRepository)

    def 'should create new information with given information form and save in repository'() {
        given:
        InformationService.InformationForm informationForm = createFormInformation()

        when:
        def information = informationService.create(informationForm)

        then:
        with(information) {
            description == informationForm.description
            message == informationForm.message
            with(person) {
                name == informationForm.person.name
                lastName == informationForm.person.lastName
            }
            with(cars.first()) {
                brand == informationForm.cars.first().brand
                model == informationForm.cars.first().model
                color == informationForm.cars.first().color
            }
            cars.size() == informationForm.cars.size()
        }
        and:
        informationRepository.repository.size() == 1
    }

}
