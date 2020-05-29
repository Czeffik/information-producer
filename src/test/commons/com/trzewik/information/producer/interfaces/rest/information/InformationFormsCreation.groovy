package com.trzewik.information.producer.interfaces.rest.information

import com.trzewik.information.producer.domain.information.CarCreation
import com.trzewik.information.producer.domain.information.InformationCreation
import com.trzewik.information.producer.domain.information.PersonCreation

trait InformationFormsCreation {

    InformationCreation.InformationCreator createInformationCreator(InformationController.InformationForm informationForm) {
        return new InformationCreation.InformationCreator(
            description: informationForm.description,
            message: informationForm.message,
            personCreator: new PersonCreation.PersonCreator(name: informationForm.person.name, lastName: informationForm.person.lastName),
            carCreators: informationForm.cars.collect { new CarCreation.CarCreator(brand: it.brand, model: it.model, color: it.color) })
    }

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
