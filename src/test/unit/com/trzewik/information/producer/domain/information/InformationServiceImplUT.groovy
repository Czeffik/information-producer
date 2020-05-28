package com.trzewik.information.producer.domain.information

import spock.lang.Specification
import spock.lang.Subject

class InformationServiceImplUT extends Specification implements InformationCommandsCreation, InformationVerifier {
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
            def foundInformation = informationService.get(createGetInformationCommand(new GetInformationCommandCreator(id: information.id)))
        then:
            foundInformation == information
    }

    def 'should throw exception when information is not found in repository'() {
        given:
            def command = createGetInformationCommand()
        when:
            informationService.get(command)
        then:
            def exception = thrown(InformationRepository.NotFoundException)
            exception.message == "Can not find information with id: [${command.id}] in repository."
    }

    def 'should create new information from given information form and save in repository'() {
        given:
            def command = createCreateInformationCommand()
        when:
            def newInformation = informationService.create(command)
        then:
            verifyInformation(newInformation, createInformation(new InformationCreator(newInformation.id, command)))
        and:
            informationRepository.repository.size() == 2
        and:
            informationRepository.repository.get(newInformation.id) == newInformation
    }

    def 'should update all fields in existing information and save in repository with new data'() {
        given:
            def command = createUpdateInformationCommand(
                new UpdateInformationCommandCreator(
                    id: information.id
                )
            )
        when:
            def updatedInformation = informationService.update(command)
        and:
            def informationFromRepo = informationRepository.get(information.id)
        then:
            verifyInformation(updatedInformation, createInformation(new InformationCreator(updatedInformation.id, command)))
        and:
            updatedInformation == informationFromRepo
        and:
            informationRepository.repository.size() == 1
    }

    def 'should update description field in existing information and save in repository with new data'() {
        given:
            def command = createUpdateInformationCommand(
                new UpdateInformationCommandCreator(
                    id: information.id,
                    description: 'new new new new'
                )
            )
        when:
            def updatedInformation = informationService.update(command)
        then:
            verifyInformation(updatedInformation, createInformation(new InformationCreator(
                id: information.id,
                description: command.description,
                message: information.message,
                personCreator: new PersonCreator(information.person),
                carCreators: information.cars.collect { new CarCreator(it) }
            )))
        and:
            informationRepository.repository.size() == 1
    }

    def 'should update message field in existing information and save in repository with new data'() {
        given:
            def command = createUpdateInformationCommand(
                new UpdateInformationCommandCreator(
                    id: information.id,
                    message: 'new new new new'
                )
            )
        when:
            def updatedInformation = informationService.update(command)
        then:
            verifyInformation(updatedInformation, createInformation(new InformationCreator(
                id: information.id,
                description: information.description,
                message: command.message,
                personCreator: new PersonCreator(information.person),
                carCreators: information.cars.collect { new CarCreator(it) }
            )))
        and:
            informationRepository.repository.size() == 1
    }

    def 'should update person field in existing information and save in repository with new data'() {
        given:
            def command = createUpdateInformationCommand(
                new UpdateInformationCommandCreator(
                    id: information.id,
                    person: createInformationCommandPerson()
                )
            )
        when:
            def updatedInformation = informationService.update(command)
        then:
            verifyInformation(updatedInformation, createInformation(new InformationCreator(
                id: information.id,
                description: information.description,
                message: information.message,
                personCreator: new PersonCreator(command.person),
                carCreators: information.cars.collect { new CarCreator(it) }
            )))
        and:
            informationRepository.repository.size() == 1
    }

    def 'should update cars list field in existing information and save in repository with new data'() {
        given:
            def command = createUpdateInformationCommand(
                new UpdateInformationCommandCreator(
                    id: information.id,
                    cars: [createInformationCommandCar(), createInformationCommandCar(), createInformationCommandCar()]
                )
            )
        when:
            def updatedInformation = informationService.update(command)
        then:
            verifyInformation(updatedInformation, createInformation(new InformationCreator(
                id: information.id,
                description: information.description,
                message: information.message,
                personCreator: new PersonCreator(information.person),
                carCreators: command.cars.collect { new CarCreator(it) }
            )))
        and:
            informationRepository.repository.size() == 1
    }

    def 'should not update existing information with new data if all fields are null'() {
        given:
            def command = createUpdateInformationCommand(
                UpdateInformationCommandCreator.create(
                    id: information.id
                )
            )
        when:
            def updatedInformation = informationService.update(command)
        then:
            information == updatedInformation
        and:
            informationRepository.repository.size() == 1
        and:
            informationRepository.repository.get(information.id) == updatedInformation
    }

    def 'should throw exception when person name is null when trying update'() {
        given:
            def command = createUpdateInformationCommand(
                new UpdateInformationCommandCreator(
                    id: information.id,
                    person: createInformationCommandPerson(new InformationCommandCreator.PersonCreator(name: null))
                )
            )
        when:
            informationService.update(command)
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'name is marked non-null but is null'
    }

    def 'should throw exception when person last name is null when trying update'() {
        given:
            def command = createUpdateInformationCommand(
                new UpdateInformationCommandCreator(
                    id: information.id,
                    person: createInformationCommandPerson(new InformationCommandCreator.PersonCreator(lastName: null))
                )
            )
        when:
            informationService.update(command)
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'lastName is marked non-null but is null'
    }

    def 'should throw exception when one of cars brand is null when trying update'() {
        given:
            def command = createUpdateInformationCommand(
                new UpdateInformationCommandCreator(
                    id: information.id,
                    cars: [createInformationCommandCar(new InformationCommandCreator.CarCreator(brand: null))]
                )
            )
        when:
            informationService.update(command)
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'brand is marked non-null but is null'
    }

    def 'should throw exception when one of cars model is null when trying update'() {
        given:
            def command = createUpdateInformationCommand(
                new UpdateInformationCommandCreator(
                    id: information.id,
                    cars: [createInformationCommandCar(new InformationCommandCreator.CarCreator(model: null))]
                )
            )
        when:
            informationService.update(command)
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'model is marked non-null but is null'
    }

    def 'should throw exception when one of cars color is null when trying update'() {
        given:
            def command = createUpdateInformationCommand(
                new UpdateInformationCommandCreator(
                    id: information.id,
                    cars: [createInformationCommandCar(new InformationCommandCreator.CarCreator(color: null))]
                )
            )
        when:
            informationService.update(command)
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'color is marked non-null but is null'
    }

    def 'should throw exception when information for update is not found in repository'() {
        given:
            def command = createUpdateInformationCommand()
        when:
            informationService.update(command)
        then:
            def exception = thrown(InformationRepository.NotFoundException)
            exception.message == "Can not find information with id: [${command.id}] in repository."
    }

    def 'should replace existing information and save in repository with new data'() {
        given:
            def command = createReplaceInformationCommand(
                new ReplaceInformationCommandCreator(
                    id: information.id
                )
            )
        when:
            def replacedInformation = informationService.replace(command)
        and:
            def informationFromRepo = informationRepository.get(information.id)
        then:
            verifyInformation(replacedInformation, createInformation(new InformationCreator(information.id, command)))
        and:
            replacedInformation == informationFromRepo
        and:
            informationRepository.repository.size() == 1
    }

    def 'should throw exception when information for replace is not found in repository'() {
        given:
            def command = createReplaceInformationCommand()
        when:
            informationService.replace(command)
        then:
            def exception = thrown(InformationRepository.NotFoundException)
            exception.message == "Can not find information with id: [${command.id}] in repository."
    }

    def 'should delete existing information from repository'() {
        given:
            def command = createDeleteInformationCommand(
                new DeleteInformationCommandCreator(
                    id: information.id
                )
            )
        expect:
            informationRepository.repository.size() == 1
        when:
            def deletedInformation = informationService.delete(command)
        then:
            deletedInformation == information
            informationRepository.repository.size() == 0
    }

    def 'should throw exception when information for delete is not found in repository'() {
        given:
            def command = createDeleteInformationCommand()
        when:
            informationService.delete(command)
        then:
            def exception = thrown(InformationRepository.NotFoundException)
            exception.message == "Can not find information with id: [${command.id}] in repository."
    }

    def 'should throw exception when description is null in create command'() {
        when:
            createCreateInformationCommand(
                new CreateInformationCommandCreator(
                    description: null
                )
            )
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'description is marked non-null but is null'
    }

    def 'should throw exception when message is null in create command'() {
        when:
            createCreateInformationCommand(
                new CreateInformationCommandCreator(
                    message: null
                )
            )
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'message is marked non-null but is null'
    }

    def 'should throw exception when person is null in create command'() {
        when:
            createCreateInformationCommand(
                new CreateInformationCommandCreator(
                    person: null
                )
            )
        then:
            thrown(NullPointerException)
    }

    def 'should throw exception when cars is null in create command'() {
        when:
            createCreateInformationCommand(
                new CreateInformationCommandCreator(
                    cars: null
                )
            )
        then:
            thrown(NullPointerException)
    }

    def 'should throw exception when id is null in get command'() {
        when:
            createGetInformationCommand(
                new GetInformationCommandCreator(
                    id: null
                )
            )
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'id is marked non-null but is null'
    }

    def 'should throw exception when id is null in delete command'() {
        when:
            createDeleteInformationCommand(
                new DeleteInformationCommandCreator(
                    id: null
                )
            )
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'id is marked non-null but is null'
    }

    def 'should throw exception when id is null in update command'() {
        when:
            createUpdateInformationCommand(
                new UpdateInformationCommandCreator(
                    id: null
                )
            )
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'id is marked non-null but is null'
    }

    def 'should throw exception when id is null in replace command'() {
        when:
            createReplaceInformationCommand(
                new ReplaceInformationCommandCreator(
                    id: null
                )
            )
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'id is marked non-null but is null'
    }

    def 'should throw exception when description is null in replace command'() {
        when:
            createReplaceInformationCommand(
                new ReplaceInformationCommandCreator(
                    description: null
                )
            )
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'description is marked non-null but is null'
    }

    def 'should throw exception when message is null in replace command'() {
        when:
            createReplaceInformationCommand(
                new ReplaceInformationCommandCreator(
                    message: null
                )
            )
        then:
            def exception = thrown(NullPointerException)
            exception.message == 'message is marked non-null but is null'
    }

    def 'should throw exception when person is null in replace command'() {
        when:
            createReplaceInformationCommand(
                new ReplaceInformationCommandCreator(
                    person: null
                )
            )
        then:
            thrown(NullPointerException)
    }

    def 'should throw exception when cars is null in replace command'() {
        when:
            createReplaceInformationCommand(
                new ReplaceInformationCommandCreator(
                    cars: null
                )
            )
        then:
            thrown(NullPointerException)
    }
}
