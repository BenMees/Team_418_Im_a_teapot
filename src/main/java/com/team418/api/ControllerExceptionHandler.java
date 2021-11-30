package com.team418.api;

import com.team418.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    protected void invalidMovieIdException(UnauthorizedException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(), exception.getMessage());
        logger.error(exception.getMessage(), exception);
    }

    @ExceptionHandler(UnknownUserException.class)
    protected void invalidMovieIdException(UnknownUserException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(), exception.getMessage());
        logger.error(exception.getMessage(), exception);
    }

    @ExceptionHandler(EmailNotUniqueException.class)
    protected void invalidMovieIdException(EmailNotUniqueException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        logger.error(exception.getMessage(), exception);
    }

    @ExceptionHandler(EmailAddressIsInvalidException.class)
    protected void emailAddressInvalidException(EmailAddressIsInvalidException emailException, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, emailException.getMessage());
    }

    @ExceptionHandler(InssNotUniqueException.class)
    protected void InssNotUniqueException(InssNotUniqueException inssException, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, inssException.getMessage());
    }
}

