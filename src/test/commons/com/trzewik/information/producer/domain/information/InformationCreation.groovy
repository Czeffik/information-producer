package com.trzewik.information.producer.domain.information

trait InformationCreation implements CarCreation, PersonCreation {

    Information createInformation(InformationCreator creator = new InformationCreator()) {
        return new Information(
            creator.id,
            creator.description,
            creator.message,
            createPerson(creator.personCreator),
            createCars(creator.carCreators)
        )
    }

    static class InformationCreator {

        String id = 'Test-information-id'
        String description = 'Test description'
        String message = 'Test message'
        PersonCreator personCreator = new PersonCreator()
        List<CarCreator> carCreators = [new CarCreator(), new CarCreator()]

        InformationCreator() {}

        InformationCreator(Information information) {
            this.id = information.id
            this.description = information.description
            this.message = information.message
            this.personCreator = new PersonCreator(information.person)
            this.carCreators = information.cars.collect { new CarCreator(it) }
        }

        InformationCreator(InformationService.InformationForm informationForm) {
            this.id = UUID.randomUUID().toString()
            this.description = informationForm.description
            this.message = informationForm.message
            this.personCreator = new PersonCreator(informationForm.person)
            this.carCreators = informationForm.cars.collect { new CarCreator(it) }
        }
    }
}
