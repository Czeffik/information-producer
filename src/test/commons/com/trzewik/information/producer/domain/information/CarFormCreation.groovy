package com.trzewik.information.producer.domain.information

trait CarFormCreation {

    InformationService.CarForm createCarForm(CarFormCreator creator = new CarFormCreator()) {
        return creator == null ? null : new InformationService.CarForm(
            brand: creator.brand,
            model: creator.model,
            color: creator.color
        )
    }

    List<InformationService.CarForm> createCarForms(List<CarFormCreator> carFormCreators) {
        return carFormCreators?.collect { createCarForm(it) }
    }

    static class CarFormCreator {
        String brand = 'Test form brand'
        String model = 'Test form model'
        String color = 'Test form color'

        static CarFormCreator create(Map map) {
            return new CarFormCreator(
                brand: map.brand,
                model: map.model,
                color: map.color
            )
        }
    }
}
