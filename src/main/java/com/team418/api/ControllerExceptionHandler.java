package com.team418.api;

import com.team418.exception.*;
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
    protected void inssNotUniqueException(InssNotUniqueException inssException, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, inssException.getMessage());
        LOGGER.error(inssException.getMessage(), inssException);
    }

    @ExceptionHandler(MoreThanOneIsbnMatchException.class)
    protected void moreThenOneIsbnMatchException(MoreThanOneIsbnMatchException moreThanOneIsbnMatchException, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, moreThanOneIsbnMatchException.getMessage());
        LOGGER.error(moreThanOneIsbnMatchException.getMessage(), moreThanOneIsbnMatchException);
    }

    @ExceptionHandler(NoBookFoundWithIsbnException.class)
    protected void noBookFoundException(NoBookFoundWithIsbnException noBookFoundException, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, noBookFoundException.getMessage());
        LOGGER.error(noBookFoundException.getMessage(), noBookFoundException);
    }

    @ExceptionHandler(MemberNotFoundByInssException.class)
    protected void noMemberFoundException(MemberNotFoundByInssException memberNotFoundByInssException, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, memberNotFoundByInssException.getMessage());
        LOGGER.error(memberNotFoundByInssException.getMessage(), memberNotFoundByInssException);
    }

    @ExceptionHandler(NoBookAvailableForNowException.class)
    protected void bookIsNotAvailable(NoBookAvailableForNowException bookNotAvailableException, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_EXPECTATION_FAILED, bookNotAvailableException.getMessage());
        LOGGER.error(bookNotAvailableException.getMessage(), bookNotAvailableException);
    }

    @ExceptionHandler(CreateBookWithAlreadyExistingIsbnException.class)
    protected void isbnAlreadyPresent(CreateBookWithAlreadyExistingIsbnException isbnAlreadyExists, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_CONFLICT, isbnAlreadyExists.getMessage());
        LOGGER.error(isbnAlreadyExists.getMessage(), isbnAlreadyExists);
    }

    @ExceptionHandler(LendingIsAlreadyReturnedException.class)
    protected void lendingIsAlreadyReturnedException(LendingIsAlreadyReturnedException isbnAlreadyExists, HttpServletResponse response) throws IOException {
        response.sendError(179, isbnAlreadyExists.getMessage());
        LOGGER.error(isbnAlreadyExists.getMessage(), isbnAlreadyExists);
    }

    @ExceptionHandler(NoLendingFoundException.class)
    protected void noLendingFoundException(NoLendingFoundException isbnAlreadyExists, HttpServletResponse response) throws IOException {
        response.sendError(299, isbnAlreadyExists.getMessage());

        LOGGER.error(isbnAlreadyExists.getMessage(), isbnAlreadyExists);
    }
}

