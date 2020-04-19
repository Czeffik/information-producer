package com.trzewik.information.producer.domain.information

trait PersonCreation {

    Person createPerson(PersonCreator creator = new PersonCreator()) {
        return new Person(
            creator.id,
            creator.name,
            creator.lastName
        )
    }

    static class PersonCreator {
        String id = 'Test-person-id'
        String name = 'Test name'
        String lastName = 'Test last name'

        PersonCreator() {}

        PersonCreator(String id, Person person) {
            this.id = id
            this.name = person.name
            this.lastName = person.lastName
        }

        PersonCreator(InformationService.PersonForm personForm) {
            this.id = UUID.randomUUID().toString()
            this.name = personForm.name
            this.lastName = personForm.lastName
        }
    }

}
