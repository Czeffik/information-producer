package com.trzewik.information.producer.domain.information

trait InformationVerifier implements InformationCreation {

    boolean verifyInformation(Information information, Information expected) {
        assert information.id == expected.id
        assert information.description == expected.description
        assert information.message == expected.message
        assert verifyPerson(information.person, expected.person)
        assert verifyCars(information.cars, expected.cars)
        return true
    }

    boolean verifyPerson(Person person, Person expected) {
        assert person.id != null
        assert person.name == expected.name
        assert person.lastName == expected.lastName
        return true
    }

    boolean verifyCars(List<Car> cars, List<Car> expectedCars) {
        assert cars.size() == expectedCars.size()
        cars.eachWithIndex { Car car, int i ->
            assert car.id != null
            assert car.color == expectedCars.get(i).color
            assert car.model == expectedCars.get(i).model
            assert car.brand == expectedCars.get(i).brand
        }
        return true
    }
}
