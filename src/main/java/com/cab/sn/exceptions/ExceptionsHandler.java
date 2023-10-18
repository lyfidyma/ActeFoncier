package com.cab.sn.exceptions;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
    protected ResponseEntity <Object> handleCustomAPIException(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ApiErrorResponse response = new ApiErrorResponse.ApiErrorResponseBuilder()
            .withStatus(status)
            .withDetail("Something went wrong")
            .withMessage(ex.getLocalizedMessage())
            .withError_code("502")
            .withError_code(status.BAD_GATEWAY.name())
            .atTime(LocalDateTime.now(ZoneOffset.UTC))
            .build();
        return new ResponseEntity <> (response, response.getStatus());
    }
}
