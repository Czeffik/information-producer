package com.trzewik.information.producer.domain.information

import spock.lang.Specification
import spock.lang.Unroll

class InformationTest extends Specification implements InformationCreation {

    @Unroll
    def 'information and information to compare should #DESCRIPTION'() {
        expect:
            (INFORMATION == INFORMATION_TO_COMPARE) == EXPECTED
        where:
            INFORMATION         | INFORMATION_TO_COMPARE                                                                                       || EXPECTED | DESCRIPTION
            createInformation() | createInformation()                                                                                          || true     | 'be equals'
            createInformation() | createInformation(new InformationCreator(description: 'Different description'))                              || false    | 'not be equals'
            createInformation() | createInformation(new InformationCreator(message: 'Different message'))                                      || false    | 'not be equals'
            createInformation() | createInformation(new InformationCreator(personCreator: new PersonCreator(id: 'Different id')))              || false    | 'not be equals'
            createInformation() | createInformation(new InformationCreator(personCreator: new PersonCreator(name: 'Different name')))          || false    | 'not be equals'
            createInformation() | createInformation(new InformationCreator(personCreator: new PersonCreator(lastName: 'Different last name'))) || false    | 'not be equals'
            createInformation() | createInformation(new InformationCreator(carCreators: [new CarCreator(id: 'Different id')]))                 || false    | 'not be equals'
            createInformation() | createInformation(new InformationCreator(carCreators: [new CarCreator(brand: 'Different brand')]))           || false    | 'not be equals'
            createInformation() | createInformation(new InformationCreator(carCreators: [new CarCreator(model: 'Different model')]))           || false    | 'not be equals'
            createInformation() | createInformation(new InformationCreator(carCreators: [new CarCreator(color: 'Different color')]))           || false    | 'not be equals'

    }

}
