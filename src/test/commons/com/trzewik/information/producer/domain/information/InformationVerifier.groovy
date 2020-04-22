package com.trzewik.information.producer.domain.information

trait InformationVerifier implements InformationCreation {

    boolean verifyInformation(Information information, InformationService.InformationForm informationForm) {
        assert information.description == informationForm.description
        assert information.message == informationForm.message
        assert verifyPerson(information.person, informationForm.person)
        assert verifyCars(information.cars, informationForm.cars)
        return true
    }

    boolean verifyPerson(Person person, InformationService.PersonForm personForm) {
        assert person.name == personForm.name
        assert person.lastName == person.lastName
        return true
    }

    boolean verifyCars(List<Car> cars, List<InformationService.CarForm> carForms) {
        assert cars.size() == carForms.size()
        assert cars == translateCarFormsInCars(cars, carForms)
        return true
    }

    List<Car> translateCarFormsInCars(List<Car> cars, List<InformationService.CarForm> carForms) {
        int index = 0
        return carForms.collect { carForm -> createCar(new CarCreator(cars.get(index++).id, carForm)) }
    }

}
