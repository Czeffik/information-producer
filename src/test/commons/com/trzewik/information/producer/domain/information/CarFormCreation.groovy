package com.trzewik.information.producer.domain.information

import groovy.transform.EqualsAndHashCode

trait CarFormCreation {

    InformationService.CarForm createCarForm(CarFormCreator creator = new CarFormCreator()) {
        return new InformationService.CarForm(
            brand: creator.brand,
            model: creator.model,
            color: creator.color
        )
    }

    List<InformationService.CarForm> createCarForms(List<CarFormCreator> carFormCreators) {
        return carFormCreators.collect { createCarForm(it) }
    }

    @EqualsAndHashCode
    static class CarFormCreator {
        String brand = 'Test brand'
        String model = 'Test model'
        String color = 'Test color'

        CarFormCreator() {}

        CarFormCreator(Car car) {
            this.brand = car.brand
            this.model = car.model
            this.color = car.color
        }
    }

}
