package com.team418.api;

import com.team418.exception.EmailAddressIsInvalidException;
import com.team418.exception.InssNotUniqueException;
import com.team418.exception.UnauthorizedException;
import com.team418.exception.UnknownUserException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(EmailAddressIsInvalidException.class)
    protected void emailAddressInvalidException(EmailAddressIsInvalidException emailException, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, emailException.getMessage());
        LOGGER.error(emailException.getMessage(), emailException);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected void unauthorizedHandler(UnauthorizedException exception, HttpServletResponse response) throws IOException {
        response.sendError(FORBIDDEN.value(), exception.getMessage());
        LOGGER.error(exception.getMessage(), exception);
    }

    @ExceptionHandler(UnknownUserException.class)
    protected void unknownUserHandler(UnknownUserException exception, HttpServletResponse response) throws IOException {
        response.sendError(UNAUTHORIZED.value(), exception.getMessage());
        LOGGER.error(exception.getMessage(), exception);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected void illegalArgumentHandler(IllegalArgumentException exception, HttpServletResponse response) throws IOException {
        response.sendError(BAD_REQUEST.value(), exception.getMessage());
        LOGGER.error(exception.getMessage(), exception);
    }

    @ExceptionHandler(InssNotUniqueException.class)
    protected void InssNotUniqueException(InssNotUniqueException inssException, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, inssException.getMessage());
        LOGGER.error(inssException.getMessage(), inssException);
    }
}
