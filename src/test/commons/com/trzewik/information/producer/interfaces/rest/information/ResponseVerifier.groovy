package com.trzewik.information.producer.interfaces.rest.information

import com.trzewik.information.producer.domain.information.Car
import com.trzewik.information.producer.domain.information.Information
import com.trzewik.information.producer.domain.information.Person
import groovy.json.JsonSlurper
import io.restassured.response.Response

trait ResponseVerifier {

    JsonSlurper slurper = new JsonSlurper()

    boolean verifyError(Response responseError, ErrorDto expected){
        def parsedBody = parseToStringBody(responseError)
        assert parsedBody.message == expected.message
        assert parsedBody.code == expected.code
        assert parsedBody.reason == expected.reason
        return true
    }

    boolean verifyInformation(Response responseInformation, Information expected) {
        def parsedBody = parseToStringBody(responseInformation)
        assert parsedBody.id == expected.id
        assert parsedBody.description == expected.description
        assert parsedBody.message == expected.message
        assert verifyPerson(parsedBody.person, expected.person)
        assert verifyCars(parsedBody.cars, expected.cars)
        return true
    }

    boolean verifyPerson(Object responsePerson, Person expected) {
        assert responsePerson.name == expected.name
        assert responsePerson.lastName == expected.lastName
        return true
    }

    boolean verifyCars(Object responseCars, List<Car> expectedCars) {
        assert responseCars.size() == expectedCars.size()
        responseCars.eachWithIndex { Object car, int i ->
            assert car.color == expectedCars.get(i).color
            assert car.model == expectedCars.get(i).model
            assert car.brand == expectedCars.get(i).brand
        }
        return true
    }

    def parseToStringBody(Response response){
        def body = response.body().asString()
        return slurper.parseText(body)
    }
}
