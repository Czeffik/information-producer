package com.trzewik.information.producer.domain.information

trait InformationFormCreation implements CarFormCreation, PersonFormCreation {

    InformationService.InformationForm createInformationForm(InformationFormCreator creator = new InformationFormCreator()) {
        return new InformationService.InformationForm(
            description: creator.description,
            message: creator.message,
            person: createPersonForm(creator.personFormCreator),
            cars: createCarForms(creator.carFormCreators)
        )
    }

    InformationService.InformationForm createInformationFormWithNullValues() {
        return new InformationService.InformationForm(
            description: null,
            message: null,
            person: null,
            cars: null
        )
    }

    static class InformationFormCreator {

        String description = 'Test description'
        String message = 'Test message'
        PersonFormCreator personFormCreator = new PersonFormCreator()
        List<CarFormCreator> carFormCreators = [new CarFormCreator(), new CarFormCreator()]
    }
}
