package com.trzewik.information.producer.domain.information

trait InformationCommandsCreation {

    InformationService.GetInformationCommand createGetInformationCommand(GetInformationCommandCreator creator = new GetInformationCommandCreator()) {
        return new InformationService.GetInformationCommand(
            creator.id
        )
    }

    InformationService.DeleteInformationCommand createDeleteInformationCommand(DeleteInformationCommandCreator creator = new DeleteInformationCommandCreator()) {
        return new InformationService.DeleteInformationCommand(
            creator.id
        )
    }

    InformationService.CreateInformationCommand createCreateInformationCommand(CreateInformationCommandCreator creator = new CreateInformationCommandCreator()) {
        return new InformationService.CreateInformationCommand(
            creator.description,
            creator.message,
            creator.person,
            creator.cars
        )
    }

    InformationService.UpdateInformationCommand createUpdateInformationCommand(UpdateInformationCommandCreator creator = new UpdateInformationCommandCreator()) {
        return new InformationService.UpdateInformationCommand(
            creator.id,
            creator.description,
            creator.message,
            creator.person,
            creator.cars
        )
    }

    InformationService.ReplaceInformationCommand createReplaceInformationCommand(ReplaceInformationCommandCreator creator = new ReplaceInformationCommandCreator()) {
        return new InformationService.ReplaceInformationCommand(
            creator.id,
            creator.description,
            creator.message,
            creator.person,
            creator.cars
        )
    }

    InformationService.InformationCommand.Person createInformationCommandPerson(InformationCommandCreator.PersonCreator creator = new InformationCommandCreator.PersonCreator()) {
        new InformationService.InformationCommand.Person(
            creator.name,
            creator.lastName
        )
    }

    InformationService.InformationCommand.Car createInformationCommandCar(InformationCommandCreator.CarCreator creator = new InformationCommandCreator.CarCreator()) {
        new InformationService.InformationCommand.Car(
            creator.brand,
            creator.model,
            creator.color
        )
    }

    static class UpdateInformationCommandCreator extends InformationCommandCreator {
        String id = 'example id'

        UpdateInformationCommandCreator() {}

        static UpdateInformationCommandCreator create(Map map) {
            return new UpdateInformationCommandCreator(
                id: map.id,
                description: map.description,
                message: map.message,
                person: map.person as InformationService.InformationCommand.Person,
                cars: map.cars as List<InformationService.InformationCommand.Car>
            )
        }
    }

    static class CreateInformationCommandCreator extends InformationCommandCreator {
    }

    static class ReplaceInformationCommandCreator extends InformationCommandCreator {
        String id = 'example id'
    }

    static class DeleteInformationCommandCreator {
        String id = 'example id'
    }

    static class GetInformationCommandCreator {
        String id = 'example id'
    }

    static abstract class InformationCommandCreator implements InformationCommandsCreation {
        String description = 'Test description'
        String message = 'Test message'
        InformationService.InformationCommand.Person person = createInformationCommandPerson()
        List<InformationService.InformationCommand.Car> cars = [createInformationCommandCar(), createInformationCommandCar()]

        static class PersonCreator {
            String name = 'Test name'
            String lastName = 'Test last name'
        }

        static class CarCreator {
            String brand = 'Test brand'
            String model = 'Test model'
            String color = 'Test color'
        }
    }
}
