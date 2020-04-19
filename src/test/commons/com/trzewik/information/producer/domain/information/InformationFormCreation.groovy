package com.trzewik.information.producer.domain.information

trait InformationFormCreation implements CarFormCreation, PersonFormCreation {

    InformationService.InformationForm createFormInformation(InformationFormCreator builder = new InformationFormCreator(createPersonForm(), createCarForms())) {
        return new InformationService.InformationForm(
            description: builder.description,
            message: builder.message,
            person: builder.person,
            cars: builder.cars
        )
    }

    static class InformationFormCreator {

        String description = 'Test description'
        String message = 'Test message'
        InformationService.PersonForm person
        List<InformationService.CarForm> cars

        InformationFormCreator(InformationService.PersonForm personForm, List<InformationService.CarForm> carForms) {
            person = personForm
            cars = carForms
        }
    }
}
