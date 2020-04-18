package com.trzewik.information.producer.domain.information

trait InformationFormCreation {

    InformationService.InformationForm createFormInformation(InformationFormBuilder builder = new InformationFormBuilder(new PersonFormBuilder(), new CarFormBuilder())) {
        InformationService.InformationForm informationForm = new InformationService.InformationForm()
        informationForm.with {
            setDescription(builder.description)
            setMessage(builder.message)
            setPerson(builder.person)
            setCars(builder.cars)
        }
        return informationForm
    }

    static class InformationFormBuilder {
        String description = 'Test description'
        String message = 'Test message'
        InformationService.PersonForm person
        List<InformationService.CarForm> cars


        InformationFormBuilder(PersonFormBuilder personBuilder, CarFormBuilder carBuilder, int amountOfCars = 3) {
            person = new InformationService.PersonForm()
            person.with {
                setName(personBuilder.name)
                setLastName(personBuilder.lastName)

            }
            cars = createListOfCars(amountOfCars, carBuilder)
        }

        InformationFormBuilder(InformationService.InformationForm information) {
            this.description = information.description
            this.message = information.message
            this.person = information.person
            this.cars = information.cars
        }

        static List<InformationService.CarForm> createListOfCars(int amountOfCars, CarFormBuilder carBuilder) {
            def cars = []
            amountOfCars.times {
                InformationService.CarForm carForm = new InformationService.CarForm()
                carForm.with {
                    setBrand(carBuilder.brand)
                    setModel(carBuilder.model)
                    setColor(carBuilder.color)
                }
                cars << carForm
            }
            return cars
        }

    }

    static class PersonFormBuilder {
        String name = 'Test name'
        String lastName = 'Test last name'

        PersonFormBuilder() {}

    }

    static class CarFormBuilder {
        String brand = 'Test brand'
        String model = 'Test model'
        String color = 'Test color'

        CarFormBuilder() {}
    }

}
