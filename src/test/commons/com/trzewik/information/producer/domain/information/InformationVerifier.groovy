package com.trzewik.information.producer.domain.information

trait InformationVerifier {

    boolean verifyInformation(Information information, InformationService.InformationForm informationForm) {
        assert information.description == informationForm.description
        assert information.message == informationForm.message
        assert verifyPerson(information.person, informationForm.person)
        assert verifyCars(information.cars, informationForm.cars)
        return true
    }

    boolean verifyPerson(Person person, InformationService.PersonForm personForm) {
        assert person.name == personForm.name && person.lastName == person.lastName
        return true
    }

    boolean verifyCars(List<Car> cars, List<InformationService.CarForm> carForms) {
        assert cars.collect { car -> new CarFormCreation.CarFormCreator(car) } == carForms.collect { carForm -> new CarFormCreation.CarFormCreator(carForm) }
        return true
    }

}
