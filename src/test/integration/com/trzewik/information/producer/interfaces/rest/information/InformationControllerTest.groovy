package com.trzewik.information.producer.interfaces.rest.information


import com.trzewik.information.producer.domain.information.InformationCreation
import com.trzewik.information.producer.domain.information.InformationFormCreation
import com.trzewik.information.producer.domain.information.InformationRepository
import com.trzewik.information.producer.domain.information.InformationService
import com.trzewik.information.producer.interfaces.rest.RestInterfacesConfiguration
import com.trzewik.information.producer.interfaces.rest.RestInterfacesConfigurationTest
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles(['test', 'rest-interface-test-config'])
@SpringBootTest(
    classes = [RestInterfacesConfiguration.class, RestInterfacesConfigurationTest.class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class InformationControllerTest extends Specification implements InformationControllerRequestSender, InformationFormCreation, InformationCreation {

    @LocalServerPort
    int serverPort

    @Autowired
    InformationService informationService

    JsonSlurper slurper = new JsonSlurper()


    def 'should get information by id and return information representation'() {
        given:
            def information = createInformation()
        when:
            def response = getInformationRequest(information.id)
        then:
            1 * informationService.get(information.id) >> information
        then:
            with(response) {
                statusCode() == 200
                contentType.contains('application/json')
            }
        when:
            def body = response.body().asString()
            def parsedBody = slurper.parseText(body)
        then:
            parsedBody.description == information.description
            parsedBody.message == information.message
    }

    def 'should return 404 and not found message when NotFoundException is thrown by get'() {
        given:
            def id = 'Wrong-test-id'
        when:
            def response = getInformationRequest(id)
        then:
            informationService.get(id) >> { throw new InformationRepository.NotFoundException(id) }
        then:
            with(response) {
                statusCode() == 404
                contentType.contains('application/json')
            }
        when:
            def body = response.body().asString()
            def parsedBody = slurper.parseText(body)
        then:
            parsedBody.code == 404
            parsedBody.message == "Can not find information with id: [${id}] in repository."
            parsedBody.reason == 'Not Found'
    }

    @Override
    int getPort() {
        return serverPort
    }

}
