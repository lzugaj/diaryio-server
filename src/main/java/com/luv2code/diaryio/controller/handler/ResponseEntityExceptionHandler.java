package com.luv2code.diaryio.controller.handler;

import com.luv2code.diaryio.controller.handler.response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(final RuntimeException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return createResponseMessage(badRequest, exception, httpServletRequest);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ApiResponse> handleNullPointerException(final NullPointerException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return createResponseMessage(badRequest, exception, httpServletRequest);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return createResponseMessage(badRequest, exception, httpServletRequest);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundRequestException(final EntityNotFoundException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        return createResponseMessage(notFound, exception, httpServletRequest);
    }

    private ResponseEntity<ApiResponse> createResponseMessage(final HttpStatus httpStatus, final Exception exception, final HttpServletRequest httpServletRequest) {
        final ApiResponse apiResponse = ApiResponse.builder()
                .localDateTime(LocalDateTime.now())
                .httpStatusCode(httpStatus.value())
                .httpStatus(httpStatus)
                .message(getMessage(exception))
                .path(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(apiResponse, httpStatus);
    }

    private String getMessage(final Exception exception) {
        final String message;
        if (exception instanceof MethodArgumentNotValidException) {
            final List<String> messages = new ArrayList<>();
            for (final FieldError fieldError : ((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors()) {
                messages.add(fieldError.getDefaultMessage());
            }

            message = String.join(",", messages);
        } else {
            message = exception.getMessage();
        }

        return message;
    }
}
