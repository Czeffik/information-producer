package com.trzewik.information.producer.domain.information

import spock.lang.Specification
import spock.lang.Subject

class InformationServiceImplUT extends Specification implements InformationFormCreation, InformationCreation, InformationVerifier {
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
        def informationFormCreator = new InformationFormCreator()

        when:
        def information = informationService.create(createInformationForm(informationFormCreator))

        then:
        verifyIfInformationHaveSameValues(information, informationFormCreator)
        information.cars.size() == informationFormCreator.carFormCreators.size()

        and:
        informationRepository.repository.size() == 1
    }


    def 'should update existing information and save in repository with new data'() {
        given:
        def informationForm = createInformationForm()

        and:
        def information = createInformation(new InformationCreator(informationForm))

        and:
        informationRepository.save(information)

        and:
        def newInformationFormCreator = new InformationFormCreator(description: 'New description')
        def newInformationForm = createInformationForm(newInformationFormCreator)

        when:
        def updatedInformation = informationService.update(information.id, newInformationForm)

        then:
        verifyIfInformationHaveSameValues(updatedInformation, newInformationFormCreator)
        updatedInformation.cars.size() == newInformationForm.cars.size()

        and:
        informationRepository.repository.size() == 1
    }

    def 'should update existing information and save in repository with new data or old if field is null'() {
        given:
        def informationFormCreator = new InformationFormCreator()
        def informationForm = createInformationForm(informationFormCreator)

        and:
        def information = createInformation(new InformationCreator(informationForm))

        and:
        informationRepository.save(information)

        and:
        def newInformationFormCreator = new InformationFormCreator(
            description: null,
            message: 'New message',
            personFormCreator: new PersonFormCreator(name: null, lastName: null),
            carFormCreators: [new CarFormCreator(brand: "New brand", model: 'New model'), new CarFormCreator()]
        )
        def newInformationForm = createInformationForm(newInformationFormCreator)

        when:
        def updatedInformation = informationService.update(information.id, newInformationForm)

        then:
        verifyIfInformationHaveSameValues(updatedInformation, new InformationFormCreator(
            description: informationFormCreator.description,
            message: newInformationForm.message,
            personFormCreator: informationFormCreator.personFormCreator,
            carFormCreators: newInformationFormCreator.carFormCreators
        ))
        updatedInformation.cars.size() == newInformationForm.cars.size()

        and:
        informationRepository.repository.size() == 1
    }

    def 'should not update existing information with new data if all fields are null'() {
        given:
        def informationFormCreator = new InformationFormCreator()
        def informationForm = createInformationForm(informationFormCreator)

        and:
        def information = createInformation(new InformationCreator(informationForm))

        and:
        informationRepository.save(information)

        and:
        def newInformationForm = createInformationFormWithNullValues()

        when:
        def updatedInformation = informationService.update(information.id, newInformationForm)

        then:
        verifyIfInformationHaveSameValues(updatedInformation, informationFormCreator)
        updatedInformation.cars.size() == informationForm.cars.size()

        and:
        informationRepository.repository.size() == 1
    }

    def 'should throw exception when information for update is not found in repository'() {
        given:
        def informationForm = createInformationForm()

        and:
        def information = createInformation(new InformationCreator(informationForm))

        and:
        informationRepository.save(information)

        and:
        def notExistingId = information.id + '1'

        and:
        def newInformationForm = createInformationForm(new InformationFormCreator(
            description: 'New description'
        ))

        when:
        informationService.update(notExistingId, newInformationForm)

        then:
        def exception = thrown(InformationRepository.NotFoundException)
        exception.message == "Can not find information with id: [${notExistingId}] in repository."
    }

    def 'should replace existing information and save in repository with new data'() {
        given:
        def informationForm = createInformationForm()

        and:
        def information = createInformation(new InformationCreator(informationForm))

        and:
        informationRepository.save(information)

        and:
        def newInformationFormCreator = new InformationFormCreator(
            description: 'New description'
        )
        def newInformationForm = createInformationForm(newInformationFormCreator)

        when:
        def updatedInformation = informationService.replace(information.id, newInformationForm)

        then:
        verifyIfInformationHaveSameValues(updatedInformation, newInformationFormCreator)
        updatedInformation.cars.size() == newInformationForm.cars.size()

        and:
        informationRepository.repository.size() == 1
    }

    def 'should throw exception when information for replace is not found in repository'() {
        given:
        def informationForm = createInformationForm()

        and:
        def information = createInformation(new InformationCreator(informationForm))

        and:
        informationRepository.save(information)

        and:
        def notExistingId = information.id + '1'

        and:
        def newInformationForm = createInformationForm(new InformationFormCreator(
            description: 'New description'
        ))

        when:
        informationService.replace(notExistingId, newInformationForm)

        then:
        def exception = thrown(InformationRepository.NotFoundException)
        exception.message == "Can not find information with id: [${notExistingId}] in repository."
    }

    def 'should throw exception when any field of information for replace is null'() {
        given:
        def informationForm = createInformationForm()

        and:
        def information = createInformation(new InformationCreator(informationForm))

        and:
        informationRepository.save(information)

        and:
        def newInformationForm = createInformationForm(new InformationFormCreator(
            description: null
        ))

        when:
        informationService.replace(information.id, newInformationForm)

        then:
        def exception = thrown(NullPointerException)
        exception.message == "description is marked non-null but is null"
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
