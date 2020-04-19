package com.trzewik.information.producer.domain.information

trait PersonFormCreation {

    InformationService.PersonForm createPersonForm(PersonFormCreator creator = new PersonFormCreator()) {
        return new InformationService.PersonForm(
            name: creator.name,
            lastName: creator.lastName
        )
    }

    static class PersonFormCreator {
        String name = 'Test name'
        String lastName = 'Test last name'
    }
}