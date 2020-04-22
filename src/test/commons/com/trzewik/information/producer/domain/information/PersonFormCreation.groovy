package com.trzewik.information.producer.domain.information

trait PersonFormCreation {

    InformationService.PersonForm createPersonForm(PersonFormCreator creator = new PersonFormCreator()) {
        return creator == null ? null : new InformationService.PersonForm(
            name: creator.name,
            lastName: creator.lastName
        )
    }

    static class PersonFormCreator {
        String name = 'Test form name'
        String lastName = 'Test form last name'

        static PersonFormCreator create(Map map) {
            return new PersonFormCreator(
                name: map.name,
                lastName: map.lastName
            )
        }
    }
}
