package com.trzewik.information.producer.interfaces.rest.information

trait InformationFormsCreation {

    InformationController.CarForm createCarForm(CarFormCreator creator = new CarFormCreator()) {
        return new InformationController.CarForm(
            brand: creator.brand,
            model: creator.model,
            color: creator.color
        )
    }

    List<InformationController.CarForm> createCarForms(List<CarFormCreator> carFormCreators) {
        return carFormCreators?.collect { createCarForm(it) }
    }

    InformationController.InformationForm createInformationForm(InformationFormCreator creator = new InformationFormCreator()) {
        return new InformationController.InformationForm(
            description: creator.description,
            message: creator.message,
            person: createPersonForm(creator.personCreator),
            cars: createCarForms(creator.carCreators)
        )
    }

    InformationController.PersonForm createPersonForm(PersonFormCreator creator = new PersonFormCreator()) {
        return creator == null ? null : new InformationController.PersonForm(
            name: creator.name,
            lastName: creator.lastName
        )
    }

    static class PersonFormCreator {
        String name = 'Test form name'
        String lastName = 'Test form last name'
    }

    static class InformationFormCreator {

        String description = 'Test form description'
        String message = 'Test form message'
        PersonFormCreator personCreator = new PersonFormCreator()
        List<CarFormCreator> carCreators = [new CarFormCreator(), new CarFormCreator()]

        static InformationFormCreator create(Map map) {
            return new InformationFormCreator(
                description: map.description,
                message: map.message,
                personCreator: map.personCreator as PersonFormCreator,
                carCreators: map.carCreators as List<CarFormCreator>
            )
        }
    }

    static class CarFormCreator {
        String brand = 'Test form brand'
        String model = 'Test form model'
        String color = 'Test form color'
    }
}
