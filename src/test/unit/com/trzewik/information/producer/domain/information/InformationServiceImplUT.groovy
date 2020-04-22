package com.trzewik.information.producer.domain.information

import spock.lang.Specification
import spock.lang.Subject

class InformationServiceImplUT extends Specification implements InformationFormCreation, InformationVerifier {
    InformationRepository informationRepository = new InformationRepositoryMock()

    @Subject
    InformationService informationService = InformationServiceFactory.create(informationRepository)

    Information information;

    def setup() {
        information = createInformation()
        informationRepository.save(information)
    }

    def 'should get information from repository'() {
        when:
        def foundInformation = informationService.get(information.id)

        then:
        foundInformation == information
    }

    def 'should throw exception when information is not found in repository'() {
        given:
        def notExistingId = information.id + '1'

        when:
        informationService.get(notExistingId)

        then:
        def exception = thrown(InformationRepository.NotFoundException)
        exception.message == "Can not find information with id: [${notExistingId}] in repository."
    }

    def 'should create new information from given information form and save in repository'() {
        given:
        def informationForm = createInformationForm()

        when:
        def newInformation = informationService.create(informationForm)

        then:
        verifyInformation(newInformation, informationForm)

        and:
        informationRepository.repository.size() == 2
    }


    def 'should update all fields in existing information and save in repository with new data'() {
        given:
        def informationForm = createInformationForm(new InformationFormCreator(
            description: 'New description',
            message: 'New message',
            personCreator: new PersonFormCreator(name: "New name", lastName: "New last name"),
            carCreators: [new CarFormCreator(brand: "New brand", model: 'New model', color: 'New color')]
        ))

        when:
        def updatedInformation = informationService.update(information.id, informationForm)

        and:
        def informationFromRepo = informationRepository.get(information.id)

        then:
        verifyInformation(updatedInformation, informationForm)
        verifyInformation(informationFromRepo, informationForm)

        and:
        informationRepository.repository.size() == 1
    }

    def 'should update description field in existing information and save in repository with new data'() {
        given:
        def informationForm = createInformationForm(new InformationFormCreator(description: 'New description'))

        when:
        def updatedInformation = informationService.update(information.id, informationForm)

        then:
        verifyInformation(updatedInformation, informationForm)

        and:
        informationRepository.repository.size() == 1
    }

    def 'should update message field in existing information and save in repository with new data'() {
        given:
        def informationForm = createInformationForm(new InformationFormCreator(message: 'New message'))

        when:
        def updatedInformation = informationService.update(information.id, informationForm)

        then:
        verifyInformation(updatedInformation, informationForm)

        and:
        informationRepository.repository.size() == 1
    }

    def 'should update person field in existing information and save in repository with new data'() {
        given:
        def informationForm = createInformationForm(new InformationFormCreator(
            personCreator: new PersonFormCreator(name: "New name", lastName: "New last name")))

        when:
        def updatedInformation = informationService.update(information.id, informationForm)

        then:
        verifyInformation(updatedInformation, informationForm)

        and:
        informationRepository.repository.size() == 1
    }

    def 'should update cars list field in existing information and save in repository with new data'() {
        given:
        def informationForm = createInformationForm(new InformationFormCreator(
            carCreators: [new CarFormCreator(brand: "New brand", model: 'New model', color: 'New color')]))

        when:
        def updatedInformation = informationService.update(information.id, informationForm)

        then:
        verifyInformation(updatedInformation, informationForm)

        and:
        informationRepository.repository.size() == 1
    }

    def 'should update existing information and save in repository with new data or old if field is null'() {
        given:
        def informationForm = createInformationForm()

        and:
        def information = createInformation(new InformationCreator(informationForm))

        and:
        informationRepository.save(information)

        and:
        def newInformationForm = createInformationForm(new InformationFormCreator(
            description: null,
            message: 'New message',
            personCreator: null,
            carCreators: [new CarFormCreator(brand: "New brand", model: 'New model'), new CarFormCreator()]
        ))

        when:
        def updatedInformation = informationService.update(information.id, newInformationForm)

        then:
        verifyInformation(updatedInformation, new InformationService.InformationForm(
            description: informationForm.description,
            message: newInformationForm.message,
            person: informationForm.person,
            cars: newInformationForm.cars
        ))

        and:
        informationRepository.repository.size() == 2
    }

    def 'should not update existing information with new data if all fields are null'() {
        given:
        def informationForm = createInformationForm()

        and:
        def information = createInformation(new InformationCreator(informationForm))

        and:
        informationRepository.save(information)

        and:
        def newInformationForm = createInformationForm(new InformationFormCreator(
            description: null,
            message: null,
            personCreator: null,
            carCreators: null
        ))

        when:
        def updatedInformation = informationService.update(information.id, newInformationForm)

        then:
        verifyInformation(updatedInformation, informationForm)

        and:
        informationRepository.repository.size() == 2
    }

    def 'should throw exception when person name is null when trying update'() {
        given:
        def informationForm = createInformationForm(new InformationFormCreator(
            personCreator: new PersonFormCreator(name: null)
        ))

        when:
        informationService.update(information.id, informationForm)

        then:
        def exception = thrown(NullPointerException)
        exception.message == "name is marked non-null but is null"
    }

    def 'should throw exception when person last name is null when trying update'() {
        given:
        def informationForm = createInformationForm(new InformationFormCreator(
            personCreator: new PersonFormCreator(lastName: null)
        ))

        when:
        informationService.update(information.id, informationForm)

        then:
        def exception = thrown(NullPointerException)
        exception.message == "lastName is marked non-null but is null"
    }

    def 'should throw exception when one of cars brand is null when trying update'() {
        given:
        def informationForm = createInformationForm(new InformationFormCreator(
            carCreators: [new CarFormCreator(brand: null)]
        ))

        when:
        informationService.update(information.id, informationForm)

        then:
        def exception = thrown(NullPointerException)
        exception.message == "brand is marked non-null but is null"
    }

    def 'should throw exception when one of cars model is null when trying update'() {
        given:
        def informationForm = createInformationForm(new InformationFormCreator(
            carCreators: [new CarFormCreator(model: null)]
        ))

        when:
        informationService.update(information.id, informationForm)

        then:
        def exception = thrown(NullPointerException)
        exception.message == "model is marked non-null but is null"
    }

    def 'should throw exception when one of cars color is null when trying update'() {
        given:
        def informationForm = createInformationForm(new InformationFormCreator(
            carCreators: [new CarFormCreator(color: null)]
        ))

        when:
        informationService.update(information.id, informationForm)

        then:
        def exception = thrown(NullPointerException)
        exception.message == "color is marked non-null but is null"
    }

    def 'should throw exception when information for update is not found in repository'() {
        given:
        def notExistingId = information.id + '1'

        and:
        def informationForm = createInformationForm(new InformationFormCreator(
            description: 'New description'
        ))

        when:
        informationService.update(notExistingId, informationForm)

        then:
        def exception = thrown(InformationRepository.NotFoundException)
        exception.message == "Can not find information with id: [${notExistingId}] in repository."
    }

    def 'should replace existing information and save in repository with new data'() {
        given:
        def informationForm = createInformationForm(new InformationFormCreator(
            description: 'New description'
        ))

        when:
        def replacedInformation = informationService.replace(information.id, informationForm)

        and:
        def informationFromRepo = informationRepository.get(information.id)

        then:
        verifyInformation(replacedInformation, informationForm)
        verifyInformation(informationFromRepo, informationForm)

        and:
        informationRepository.repository.size() == 1
    }

    def 'should throw exception when information for replace is not found in repository'() {
        given:
        def notExistingId = information.id + '1'

        and:
        def informationForm = createInformationForm(new InformationFormCreator(
            description: 'New description'
        ))

        when:
        informationService.replace(notExistingId, informationForm)

        then:
        def exception = thrown(InformationRepository.NotFoundException)
        exception.message == "Can not find information with id: [${notExistingId}] in repository."
    }

    def 'should throw exception when any field of information for replace is null'() {
        given:
        def informationForm = createInformationForm(new InformationFormCreator(
            description: null
        ))

        when:
        informationService.replace(information.id, informationForm)

        then:
        def exception = thrown(NullPointerException)
        exception.message == "description is marked non-null but is null"
    }

    def 'should delete existing information from repository'() {
        expect:
        informationRepository.repository.size() == 1

        when:
        def deletedInformation = informationService.delete(information.id)

        then:
        deletedInformation == information
        informationRepository.repository.size() == 0
    }

    def 'should throw exception when information for delete is not found in repository'() {
        given:
        def notExistingId = information.id + '1'

        when:
        informationService.delete(notExistingId)

        then:
        def exception = thrown(InformationRepository.NotFoundException)
        exception.message == "Can not find information with id: [${notExistingId}] in repository."
    }

}
