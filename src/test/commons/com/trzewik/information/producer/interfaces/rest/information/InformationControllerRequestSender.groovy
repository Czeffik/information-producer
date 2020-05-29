package com.trzewik.information.producer.interfaces.rest.information

import com.trzewik.information.producer.domain.information.InformationRepository
import com.trzewik.information.producer.interfaces.rest.RequestSender
import groovy.json.JsonBuilder
import io.restassured.http.ContentType
import io.restassured.response.Response

trait InformationControllerRequestSender extends RequestSender {

    Response getInformationRequest(String id) throws InformationRepository.NotFoundException {
        return request("/information/${id}")
            .get()
            .thenReturn()
    }

    Response createInformationRequest(InformationController.InformationForm form) {
        return request("/information")
            .contentType(ContentType.JSON)
            .body(toJson(form))
            .post()
            .thenReturn()
    }

    Response putInformationRequest(String id, InformationController.InformationForm form) throws InformationRepository.NotFoundException {
        return request("/information/${id}")
            .contentType(ContentType.JSON)
            .body(toJson(form))
            .put()
            .thenReturn()
    }

    Response patchInformationRequest(String id, InformationController.InformationForm form) throws InformationRepository.NotFoundException {
        return request("/information/${id}")
            .contentType(ContentType.JSON)
            .body(toJson(form))
            .patch()
            .thenReturn()
    }

    Response deleteInformationRequest(String id) throws InformationRepository.NotFoundException {
        return request("/information/${id}")
            .delete()
            .thenReturn()
    }

    String toJson(Object object) {
        return new JsonBuilder(object).toPrettyString()
    }

}
