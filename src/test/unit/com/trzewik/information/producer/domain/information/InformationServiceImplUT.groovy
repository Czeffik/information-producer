package com.trzewik.information.producer.domain.information

import spock.lang.Specification
import spock.lang.Subject

class InformationServiceImplUT extends Specification implements InformationFormCreation, InformationVerifier {
    InformationRepository informationRepository = new InformationRepositoryMock()

    @Subject
    InformationService informationService = InformationServiceFactory.create(informationRepository)

    Information information

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
            def newInformationForm = createInformationForm()
        when:
            def newInformation = informationService.create(newInformationForm)
        then:
            verifyInformation(newInformation, createInformation(new InformationCreator(newInformation.id, newInformationForm)))
        and:
            informationRepository.repository.size() == 2
        and:
            informationRepository.repository.get(newInformation.id) == newInformation
    }

    def 'should update all fields in existing information and save in repository with new data'() {
        given:
            def newInformationForm = createInformationForm()
        when:
            def updatedInformation = informationService.update(information.id, newInformationForm)
        and:
            def informationFromRepo = informationRepository.get(information.id)
        then:
            verifyInformation(updatedInformation, createInformation(new InformationCreator(updatedInformation.id, newInformationForm)))
        and:
            updatedInformation == informationFromRepo
        and:
            informationRepository.repository.size() == 1
    }

    def 'should update description field in existing information and save in repository with new data'() {
        given:
            def newInformationForm = createInformationForm(InformationFormCreator.create(description: 'New description'))
        when:
            def updatedInformation = informationService.update(information.id, newInformationForm)
        then:
            verifyInformation(updatedInformation, createInformation(new InformationCreator(
                id: information.id,
                description: newInformationForm.description,
                message: information.message,
                personCreator: new PersonCreator(information.person),
                carCreators: information.cars.collect { new CarCreator(it) }
            )))
        and:
            informationRepository.repository.size() == 1
    }

    def 'should update message field in existing information and save in repository with new data'() {
        given:
            def newInformationForm = createInformationForm(InformationFormCreator.create(message: 'New message'))
        when:
            def updatedInformation = informationService.update(information.id, newInformationForm)
        then:
            verifyInformation(updatedInformation, createInformation(new InformationCreator(
                id: information.id,
                description: information.description,
                message: newInformationForm.message,
                personCreator: new PersonCreator(information.person),
                carCreators: information.cars.collect { new CarCreator(it) }
            )))
        and:
            informationRepository.repository.size() == 1
    }

    def 'should update person field in existing information and save in repository with new data'() {
        given:
            def newInformationForm = createInformationForm(InformationFormCreator.create(personCreator: new PersonFormCreator()))
        when:
            def updatedInformation = informationService.update(information.id, newInformationForm)
        then:
            verifyInformation(updatedInformation, createInformation(new InformationCreator(
                id: information.id,
                description: information.description,
                message: information.message,
                personCreator: new PersonCreator(newInformationForm.person),
                carCreators: information.cars.collect { new CarCreator(it) }
            )))
        and:
            informationRepository.repository.size() == 1
    }

    def 'should update cars list field in existing information and save in repository with new data'() {
        given:
            def newInformationForm = createInformationForm(InformationFormCreator.create(carCreators: [new CarFormCreator(), new CarFormCreator()]))
        when:
            def updatedInformation = informationService.update(information.id, newInformationForm)
        then:
            verifyInformation(updatedInformation, createInformation(new InformationCreator(
                id: information.id,
                description: information.description,
                message: information.message,
                personCreator: new PersonCreator(information.person),
                carCreators: newInformationForm.cars.collect { new CarCreator(it) }
            )))
        and:
            informationRepository.repository.size() == 1
    }

    def 'should not update existing information with new data if all fields are null'() {
        given:
            def newInformationForm = createInformationForm(InformationFormCreator.create([:]))
        when:
            def updatedInformation = informationService.update(information.id, newInformationForm)
        then:
            information == updatedInformation
        and:
            informationRepository.repository.size() == 1
        and:
            informationRepository.repository.get(information.id) == updatedInformation
    }

    def 'should throw exception when person name is null when trying update'() {
        given:
            def newInformationForm = createInformationForm(new InformationFormCreator(
                personCreator: new PersonFormCreator(name: null)
            ))
        when:
            informationService.update(information.id, newInformationForm)
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'name is marked non-null but is null'
    }

    def 'should throw exception when person last name is null when trying update'() {
        given:
            def newInformationForm = createInformationForm(new InformationFormCreator(
                personCreator: new PersonFormCreator(lastName: null)
            ))
        when:
            informationService.update(information.id, newInformationForm)
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'lastName is marked non-null but is null'
    }

    def 'should throw exception when one of cars brand is null when trying update'() {
        given:
            def newInformationForm = createInformationForm(new InformationFormCreator(
                carCreators: [new CarFormCreator(brand: null)]
            ))
        when:
            informationService.update(information.id, newInformationForm)
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'brand is marked non-null but is null'
    }

    def 'should throw exception when one of cars model is null when trying update'() {
        given:
            def newInformationForm = createInformationForm(new InformationFormCreator(
                carCreators: [new CarFormCreator(model: null)]
            ))
        when:
            informationService.update(information.id, newInformationForm)
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'model is marked non-null but is null'
    }

    def 'should throw exception when one of cars color is null when trying update'() {
        given:
            def newInformationForm = createInformationForm(new InformationFormCreator(
                carCreators: [new CarFormCreator(color: null)]
            ))
        when:
            informationService.update(information.id, newInformationForm)
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'color is marked non-null but is null'
    }

    def 'should throw exception when information for update is not found in repository'() {
        given:
            def notExistingId = information.id + '1'
        and:
            def newInformationForm = createInformationForm()
        when:
            informationService.update(notExistingId, newInformationForm)
        then:
            def exception = thrown(InformationRepository.NotFoundException)
            exception.message == "Can not find information with id: [${notExistingId}] in repository."
    }

    def 'should replace existing information and save in repository with new data'() {
        given:
            def newInformationForm = createInformationForm()
        when:
            def replacedInformation = informationService.replace(information.id, newInformationForm)
        and:
            def informationFromRepo = informationRepository.get(information.id)
        then:
            verifyInformation(replacedInformation, createInformation(new InformationCreator(information.id, newInformationForm)))
        and:
            replacedInformation == informationFromRepo
        and:
            informationRepository.repository.size() == 1
    }

    def 'should throw exception when information for replace is not found in repository'() {
        given:
            def notExistingId = information.id + '1'
        and:
            def newInformationForm = createInformationForm()
        when:
            informationService.replace(notExistingId, newInformationForm)
        then:
            def exception = thrown(InformationRepository.NotFoundException)
            exception.message == "Can not find information with id: [${notExistingId}] in repository."
    }

    def 'should throw exception when description field of information for replace is null'() {
        given:
            def newInformationForm = createInformationForm(new InformationFormCreator(
                description: null
            ))
        when:
            informationService.replace(information.id, newInformationForm)
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'description is marked non-null but is null'
    }

    def 'should throw exception when message field of information for replace is null'() {
        given:
            def newInformationForm = createInformationForm(new InformationFormCreator(
                message: null
            ))
        when:
            informationService.replace(information.id, newInformationForm)
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'message is marked non-null but is null'
    }

    def 'should throw exception when person field of information for replace is null'() {
        given:
            def newInformationForm = createInformationForm(new InformationFormCreator(
                personCreator: null
            ))
        when:
            informationService.replace(information.id, newInformationForm)
        then:
            thrown(NullPointerException)
    }

    def 'should throw exception when car field of information for replace is null'() {
        given:
            def newInformationForm = createInformationForm(new InformationFormCreator(
                carCreators: null
            ))
        when:
            informationService.replace(information.id, newInformationForm)
        then:
            thrown(NullPointerException)
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
