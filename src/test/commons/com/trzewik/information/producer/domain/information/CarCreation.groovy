package com.trzewik.information.producer.domain.information

import groovy.transform.EqualsAndHashCode

trait CarCreation {

    Car createCar(CarCreator creator = new CarCreator()) {
        return new Car(
            creator.id,
            creator.brand,
            creator.model,
            creator.color
        )
    }

    List<Car> createCars(List<CarCreator> carCreators) {
        return carCreators.collect { createCar(it) }
    }

    static class CarCreator {
        String id = 'Test-car-id'
        String brand = 'Test brand'
        String model = 'Test model'
        String color = 'Test color'

        CarCreator() {}

        CarCreator(Car car) {
            this.id = car.id
            this.brand = car.brand
            this.model = car.model
            this.color = car.color
        }

        CarCreator(InformationService.CarForm carForm) {
            this.id = UUID.randomUUID().toString()
            this.brand = carForm.brand
            this.model = carForm.model
            this.color = carForm.color
        }
    }
}
