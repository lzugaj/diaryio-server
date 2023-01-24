package com.luv2code.diaryio.controller.handler.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record ApiResponse(@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime localDateTime,
                          Integer httpStatusCode, HttpStatus httpStatus, String message, String path) {

    public ApiResponse(final LocalDateTime localDateTime,
                       final Integer httpStatusCode,
                       final HttpStatus httpStatus,
                       final String message,
                       final String path) {
        this.localDateTime = localDateTime;
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.message = message;
        this.path = path;
    }
}
