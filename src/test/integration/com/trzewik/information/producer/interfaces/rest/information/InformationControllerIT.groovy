package com.trzewik.information.producer.interfaces.rest.information


import com.trzewik.information.producer.domain.information.InformationCreation
import com.trzewik.information.producer.domain.information.InformationRepository
import com.trzewik.information.producer.domain.information.InformationService
import com.trzewik.information.producer.interfaces.rest.RestInterfacesConfiguration
import com.trzewik.information.producer.interfaces.rest.RestInterfacesTestConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles(['test', RestInterfacesTestConfiguration.TEST_CONFIG])
@SpringBootTest(
    classes = [RestInterfacesConfiguration.class, RestInterfacesTestConfiguration.class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class InformationControllerIT extends Specification implements ResponseVerifier, InformationControllerRequestSender, InformationFormsCreation, InformationCreation {

    @LocalServerPort
    int port

    @Autowired
    InformationService informationService

    def 'should get information by id and return information representation'() {
        given:
            def information = createInformation()
        when:
            def response = getInformationRequest(information.id)
        then:
            1 * informationService.get(_) >> information
        then:
            response.statusCode() == 200
        then:
            verifyInformation(response, information)
    }

    def 'should return not found http status when NotFoundException is thrown by get'() {
        given:
            def id = 'Wrong-test-id'
        and:
            def expectedError = new ErrorDto(
                "Can not find information with id: [${id}] in repository.",
                HttpStatus.NOT_FOUND)
        when:
            def response = getInformationRequest(id)
        then:
            1 * informationService.get(_) >> { throw new InformationRepository.NotFoundException(id) }
        then:
            response.statusCode() == 404
        then:
            verifyError(response, expectedError)
    }

    def 'should return internal server error http status when RuntimeException is thrown by get'() {
        given:
            def id = 'Some-test-id'
        and:
            def exceptionMessage = 'RuntimeException was thrown'
        and:
            def expectedError = new ErrorDto(
                exceptionMessage,
                HttpStatus.INTERNAL_SERVER_ERROR)
        when:
            def response = getInformationRequest(id)
        then:
            1 * informationService.get(_) >> { throw new RuntimeException(exceptionMessage) }
        then:
            response.statusCode() == 500
        then:
            verifyError(response, expectedError)
    }

    def 'should create new information and return information representation'() {
        given:
            def informationForm = createInformationForm()
        and:
            def information = createInformation(createInformationCreator(informationForm))
        when:
            def response = createInformationRequest(informationForm)
        then:
            1 * informationService.create(_) >> information
        then:
            response.statusCode() == 201
        then:
            verifyInformation(response, information)
    }

    def 'should return bad request http status when NullPointerException is thrown by create'() {
        given:
            def informationForm = createInformationForm(InformationFormCreator.create([:]))
        and:
            def expectedError = new ErrorDto(
                '',
                HttpStatus.BAD_REQUEST)
        when:
            def response = createInformationRequest(informationForm)
        then:
            response.statusCode() == 400
        then:
            verifyError(response, expectedError)
    }

    def 'should return internal server error http status when Exception is thrown by create'() {
        given:
            def informationForm = createInformationForm()
        and:
            def exceptionMessage = 'Runtime exception was thrown'
        and:
            def expectedError = new ErrorDto(
                exceptionMessage,
                HttpStatus.INTERNAL_SERVER_ERROR)
        when:
            def response = createInformationRequest(informationForm)
        then:
            1 * informationService.create(_) >> { throw new RuntimeException(exceptionMessage) }
        then:
            response.statusCode() == 500
        then:
            verifyError(response, expectedError)
    }

    def 'should put existing information and return information representation'() {
        given:
            def informationForm = createInformationForm()
        and:
            def information = createInformation(createInformationCreator(informationForm))
        when:
            def response = putInformationRequest(information.id, informationForm)
        then:
            1 * informationService.replace(_) >> information
        then:
            response.statusCode() == 200
        then:
            verifyInformation(response, information)
    }

    def 'should return not found http status when NotFoundException is thrown by put'() {
        given:
            def id = 'Wrong-test-id'
        and:
            def informationForm = createInformationForm()
        and:
            def expectedError = new ErrorDto(
                "Can not find information with id: [${id}] in repository.",
                HttpStatus.NOT_FOUND)
        when:
            def response = putInformationRequest(id, informationForm)
        then:
            1 * informationService.replace(_) >> { throw new InformationRepository.NotFoundException(id) }
        then:
            response.statusCode() == 404
        then:
            verifyError(response, expectedError)
    }

    def 'should return bad request http status when NullPointerException is thrown by put'() {
        given:
            def id = 'Some-test-id'
        and:
            def informationForm = createInformationForm(InformationFormCreator.create([:]))
        and:
            def expectedError = new ErrorDto(
                '',
                HttpStatus.BAD_REQUEST)
        when:
            def response = putInformationRequest(id, informationForm)
        then:
            response.statusCode() == 400
        then:
            verifyError(response, expectedError)
    }

    def 'should return internal server error http status when Exception is thrown by put'() {
        given:
            def id = 'Some-test-id'
        and:
            def informationForm = createInformationForm()
        and:
            def exceptionMessage = 'Runtime exception was thrown'
        and:
            def expectedError = new ErrorDto(
                exceptionMessage,
                HttpStatus.INTERNAL_SERVER_ERROR)
        when:
            def response = putInformationRequest(id, informationForm)
        then:
            1 * informationService.replace(_) >> { throw new RuntimeException(exceptionMessage) }
        then:
            response.statusCode() == 500
        then:
            verifyError(response, expectedError)
    }

    def 'should patch existing information and return information representation'() {
        given:
            def informationForm = createInformationForm()
        and:
            def information = createInformation(createInformationCreator(informationForm))
        when:
            def response = patchInformationRequest(information.id, informationForm)
        then:
            1 * informationService.update(_) >> information
        then:
            response.statusCode() == 200
        then:
            verifyInformation(response, information)
    }

    def 'should return not found http status when NotFoundException is thrown by patch'() {
        given:
            def id = 'Wrong-test-id'
        and:
            def informationForm = createInformationForm()
        and:
            def expectedError = new ErrorDto(
                "Can not find information with id: [${id}] in repository.",
                HttpStatus.NOT_FOUND)
        when:
            def response = patchInformationRequest(id, informationForm)
        then:
            1 * informationService.update(_) >> { throw new InformationRepository.NotFoundException(id) }
        then:
            response.statusCode() == 404
        then:
            verifyError(response, expectedError)
    }

    def 'should return bad request http status when NullPointerException is thrown by patch'() {
        given:
            def id = 'Some-test-id'
        and:
            def informationForm = createInformationForm()
        and:
            def expectedError = new ErrorDto(
                '',
                HttpStatus.BAD_REQUEST)
        when:
            def response = patchInformationRequest(id, informationForm)
        then:
            1 * informationService.update(_) >> { throw new NullPointerException() }
        then:
            response.statusCode() == 400
        then:
            verifyError(response, expectedError)
    }

    def 'should return internal server error http status when Exception is thrown by patch'() {
        given:
            def id = 'Some-test-id'
        and:
            def informationForm = createInformationForm()
        and:
            def exceptionMessage = 'Runtime exception was thrown'
        and:
            def expectedError = new ErrorDto(
                exceptionMessage,
                HttpStatus.INTERNAL_SERVER_ERROR)
        when:
            def response = patchInformationRequest(id, informationForm)
        then:
            1 * informationService.update(_) >> { throw new RuntimeException(exceptionMessage) }
        then:
            response.statusCode() == 500
        then:
            verifyError(response, expectedError)
    }

    def 'should delete existing information and return information representation'() {
        given:
            def information = createInformation()
        when:
            def response = deleteInformationRequest(information.id)
        then:
            1 * informationService.delete(_) >> information
        then:
            response.statusCode() == 200
        then:
            verifyInformation(response, information)
    }

    def 'should return not found http status when NotFoundException is thrown by delete'() {
        given:
            def id = 'Wrong-test-id'
        and:
            def expectedError = new ErrorDto(
                "Can not find information with id: [${id}] in repository.",
                HttpStatus.NOT_FOUND)
        when:
            def response = deleteInformationRequest(id)
        then:
            1 * informationService.delete(_) >> { throw new InformationRepository.NotFoundException(id) }
        then:
            response.statusCode() == 404
        then:
            verifyError(response, expectedError)
    }

    def 'should return internal server error http status when RuntimeException is thrown by delete'() {
        given:
            def id = 'Some-test-id'
        and:
            def exceptionMessage = 'RuntimeException was thrown'
        and:
            def expectedError = new ErrorDto(
                exceptionMessage,
                HttpStatus.INTERNAL_SERVER_ERROR)
        when:
            def response = deleteInformationRequest(id)
        then:
            1 * informationService.delete(_) >> { throw new RuntimeException(exceptionMessage) }
        then:
            response.statusCode() == 500
        then:
            verifyError(response, expectedError)
    }
}
