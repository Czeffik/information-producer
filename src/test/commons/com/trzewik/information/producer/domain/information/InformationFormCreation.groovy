package com.trzewik.information.producer.domain.information

trait InformationFormCreation implements CarFormCreation, PersonFormCreation {

    InformationService.InformationForm createInformationForm(InformationFormCreator creator = new InformationFormCreator()) {
        return new InformationService.InformationForm(
            description: creator.description,
            message: creator.message,
            person: createPersonForm(creator.personCreator),
            cars: createCarForms(creator.carCreators)
        )
    }

    static class InformationFormCreator {

        String description = 'Test description'
        String message = 'Test message'
        PersonFormCreator personCreator = new PersonFormCreator()
        List<CarFormCreator> carCreators = [new CarFormCreator(), new CarFormCreator()]
    }
}
