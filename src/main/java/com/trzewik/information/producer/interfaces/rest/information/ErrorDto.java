package com.trzewik.information.producer.interfaces.rest.information;

import jdk.internal.joptsimple.internal.Strings;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorDto {
    private final String message;
    private final int code;
    private final String reason;

    ErrorDto(String message, HttpStatus status) {
        this.message = message == null ? Strings.EMPTY : message;
        this.code = status.value();
        this.reason = status.getReasonPhrase();
    }
}
