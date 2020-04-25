package com.trzewik.information.producer

import com.trzewik.information.producer.interfaces.rest.swagger.SwaggerRequestSender
import groovy.json.JsonSlurper
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles('test')
@SpringBootTest(
    classes = [App],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class AppFT extends Specification implements SwaggerRequestSender, FileOperator {
    @LocalServerPort
    int serverPort

    JsonSlurper slurper = new JsonSlurper()

    def 'should return 200 and swagger json file on swagger api docs endpoint'() {
        when:
            def response = getApiDocsRequest()
        then:
            with(response) {
                statusCode() == 200
                contentType.contains('application/json')
            }
        when:
            def body = response.body().asString()
            def parsedBody = slurper.parseText(body)
        then:
            parsedBody.tags.size() == 1
        and:
            parsedBody.tags.collect { it.name }.contains('information-controller')
        and:
            saveFile(body)
    }

    def 'should return 200 and swagger ui on swagger ui endpoint'() {
        when:
            def response = getSwaggerUIRequest()
        then:
            with(response) {
                statusCode() == 200
                contentType.contains('text/html')
            }
    }

    @Override
    int getPort() {
        return serverPort
    }
}
