package com.trzewik.information.producer.domain.information

trait CarFormCreation {

    InformationService.CarForm createCarForm(CarFormCreator creator = new CarFormCreator()) {
        return new InformationService.CarForm(
            brand: creator.brand,
            model: creator.model,
            color: creator.color
        )
    }

    List<InformationService.CarForm> createCarForms(int amountOfCars = 3) {
        def cars = []
        amountOfCars.times {
            cars << createCarForm()
        }
        return cars
    }

    static class CarFormCreator {
        String brand = 'Test brand'
        String model = 'Test model'
        String color = 'Test color'
    }

}
