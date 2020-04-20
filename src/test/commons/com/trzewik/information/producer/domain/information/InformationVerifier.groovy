package com.trzewik.information.producer.domain.information

trait InformationVerifier {

    boolean verifyIfInformationHaveSameValues(Information information, InformationFormCreation.InformationFormCreator informationFormCreator) {
        return information.description == informationFormCreator.description &&
            information.message == informationFormCreator.message &&
            verifyIfPersonHaveSameValues(information.person, informationFormCreator.personFormCreator) &&
            verifyListOfCarsIfHaveSameValues(information.cars, informationFormCreator.carFormCreators)
    }

    boolean verifyIfPersonHaveSameValues(Person person, PersonFormCreation.PersonFormCreator personForm) {
        return person.name == personForm.name && person.lastName == person.lastName
    }

    boolean verifyListOfCarsIfHaveSameValues(List<Car> cars, List<CarFormCreation.CarFormCreator> carForms) {
        return changeCarIntoCarCreators(cars) == carForms
    }

    List<CarFormCreation.CarFormCreator> changeCarIntoCarCreators(List<Car> cars) {
        return cars.collect { car -> new CarFormCreation.CarFormCreator(car) }
    }

}
