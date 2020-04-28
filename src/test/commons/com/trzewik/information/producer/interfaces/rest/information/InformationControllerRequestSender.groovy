package com.trzewik.information.producer.interfaces.rest.information


import com.trzewik.information.producer.domain.information.InformationRepository
import com.trzewik.information.producer.domain.information.InformationService
import com.trzewik.information.producer.interfaces.rest.RequestSender
import groovy.json.JsonBuilder
import io.restassured.http.ContentType
import io.restassured.response.Response

trait InformationControllerRequestSender extends RequestSender {

    Response getInformationRequest(String id) {
        return request("/information/${id}")
            .get()
            .thenReturn()
    }

    Response createInformationRequest(InformationService.InformationForm form) {
        return request("/information")
            .contentType(ContentType.JSON)
            .body(new JsonBuilder(form).toPrettyString())
            .post()
            .thenReturn()
    }

    Response updateInformationRequest(String id, InformationService.InformationForm form) throws InformationRepository.NotFoundException {
        return request("/information/${id}")
            .body(new JsonBuilder(form).toPrettyString())
            .put()
            .thenReturn()
    }

    Response patchInformationRequest(String id, InformationService.InformationForm form) throws InformationRepository.NotFoundException {
        return request("/information/${id}")
            .body(new JsonBuilder(form).toPrettyString())
            .patch()
            .thenReturn()
    }

    Response deleteInformationRequest(String id) throws InformationRepository.NotFoundException {
        return request("/information/${id}")
            .delete()
            .thenReturn()
    }

}
