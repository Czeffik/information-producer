package com.trzewik.information.producer.interfaces.rest.swagger

import com.trzewik.information.producer.interfaces.rest.RequestSender
import io.restassured.response.Response

trait SwaggerRequestSender extends RequestSender {
    Response getApiDocsRequest() {
        return request("/v2/api-docs")
            .get()
            .thenReturn()
    }

    Response getSwaggerUIRequest() {
        return request("/swagger-ui.html")
            .get()
            .thenReturn()
    }
}
