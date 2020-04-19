package com.trzewik.information.producer.domain.information

import spock.lang.Specification
import spock.lang.Subject

class InformationServiceImplUT extends Specification implements InformationFormCreation, InformationCreation {
    InformationRepository informationRepository = new InformationRepositoryMock()

    @Subject
    InformationService informationService = InformationServiceFactory.create(informationRepository)


    def 'should get information from repository'() {
        given:
        def information = createInformation()

        and:
        informationRepository.save(information)

        when:
        def foundInformation = informationService.get(information.id)

        then:
        foundInformation == information
    }

    def 'should throw exception when information is not found in repository'() {
        given:
        def information = createInformation()

        and:
        informationRepository.save(information)

        and:
        def notExistingId = information.id + '1'

        when:
        informationService.get(notExistingId)

        then:
        def exception = thrown(InformationRepository.NotFoundException)
        exception.message == "Can not find information with id: [${notExistingId}] in repository."
    }

    def 'should create new information with given information form and save in repository'() {
        given:
        def informationForm = createFormInformation()

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


    def 'should update existing information and save in repository with new data'() {
        given:
        def informationForm = createFormInformation()

        and:
        def information = createInformation(new InformationCreator(informationForm))

        and:
        informationRepository.save(information)

        and:
        informationForm.description = 'New description'

        when:
        def updatedInformation = informationService.update(information.id, informationForm)

        then:
        with(updatedInformation) {
            description == informationForm.description
            description != information.description
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

    def 'should throw exception when information for update is not found in repository'() {
        given:
        def informationForm = createFormInformation()

        and:
        def information = createInformation(new InformationCreator(informationForm))

        and:
        informationRepository.save(information)

        and:
        def notExistingId = information.id + '1'

        and:
        informationForm.description = 'New description'

        when:
        informationService.update(notExistingId, informationForm)

        then:
        def exception = thrown(InformationRepository.NotFoundException)
        exception.message == "Can not find information with id: [${notExistingId}] in repository."
    }

    def 'should replace existing information and save in repository with new data'() {
        given:
        def informationForm = createFormInformation()

        and:
        def information = createInformation(new InformationCreator(informationForm))

        and:
        informationRepository.save(information)

        and:
        informationForm.description = 'New description'

        when:
        def updatedInformation = informationService.replace(information.id, informationForm)

        then:
        with(updatedInformation) {
            description == informationForm.description
            description != information.description
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

    def 'should throw exception when information for replace is not found in repository'() {
        given:
        def informationForm = createFormInformation()

        and:
        def information = createInformation(new InformationCreator(informationForm))

        and:
        informationRepository.save(information)

        and:
        def notExistingId = information.id + '1'

        and:
        informationForm.description = 'New description'

        when:
        informationService.replace(notExistingId, informationForm)

        then:
        def exception = thrown(InformationRepository.NotFoundException)
        exception.message == "Can not find information with id: [${notExistingId}] in repository."
    }

    def 'should delete existing information from repository'() {
        given:
        def information = createInformation()

        and:
        informationRepository.save(information)

        expect:
        informationRepository.repository.size() == 1

        when:
        informationService.delete(information.id)

        then:
        informationRepository.repository.size() == 0
    }

    def 'should throw exception when information for delete is not found in repository'() {
        given:
        def information = createInformation()

        and:
        informationRepository.save(information)

        and:
        def notExistingId = information.id + '1'

        when:
        informationService.delete(notExistingId)

        then:
        def exception = thrown(InformationRepository.NotFoundException)
        exception.message == "Can not find information with id: [${notExistingId}] in repository."
    }

}
