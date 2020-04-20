package com.trzewik.information.producer.domain.information

import com.trzewik.information.producer.domain.information.InformationService.CarForm
import com.trzewik.information.producer.domain.information.InformationService.InformationForm
import com.trzewik.information.producer.domain.information.InformationService.PersonForm

trait InformationVerifier {

    boolean verifyIfInformationHaveSameValues(Information information, InformationForm informationForm) {
        return information.description == informationForm.description &&
            information.message == informationForm.message &&
            verifyIfPersonHaveSameValues(information.person, informationForm.person) &&
            verifyListOfCarsIfHaveSameValues(information.cars, informationForm.cars)
    }

    boolean verifyIfPersonHaveSameValues(Person person, PersonForm personForm) {
        return person.name == personForm.name && person.lastName == person.lastName
    }

    boolean verifyListOfCarsIfHaveSameValues(List<Car> cars, List<CarForm> carForms) {
        return changeCarIntoCarCreators(cars) == changeCarFormsIntoCarCreators(carForms)
    }

    boolean changeCarFormsIntoCarCreators(List<CarForm> carForms) {
        return carForms.collect { carForm -> new CarCreation.CarCreator(carForm) }
    }

    boolean changeCarIntoCarCreators(List<Car> cars) {
        return cars.collect { car -> new CarCreation.CarCreator(car) }
    }

}
