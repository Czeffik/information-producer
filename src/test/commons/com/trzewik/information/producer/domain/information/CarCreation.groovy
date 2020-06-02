package com.trzewik.information.producer.domain.information

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
        Color color = Color.GREEN

        CarCreator() {}

        CarCreator(Car car) {
            this.id = car.id
            this.brand = car.brand
            this.model = car.model
            this.color = car.color
        }

        CarCreator(String id = UUID.randomUUID().toString(), InformationService.InformationCommand.Car car) {
            this.id = id
            this.brand = car.brand
            this.model = car.model
            this.color = car.color
        }
    }
}
