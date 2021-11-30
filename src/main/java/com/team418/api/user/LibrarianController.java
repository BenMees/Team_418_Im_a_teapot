package com.team418.api.user;

import com.team418.api.user.dto.CreateLibrarianDto;
import com.team418.api.user.dto.LibrarianDto;
import com.team418.domain.Feature;
import com.team418.domain.user.Librarian;
import com.team418.domain.user.User;
import com.team418.exception.EmailNotUniqueException;
import com.team418.services.UserService;
import com.team418.services.security.SecurityService;
import com.team418.exception.UnauthorizedException;
import com.team418.exception.UnknownUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(path = "/users/librarians")
public class LibrarianController {

    private static final Logger logger = LoggerFactory.getLogger(LibrarianController.class);
    private UserService userService;
    private SecurityService securityService;

    public LibrarianController(UserService userService, SecurityService securityService) {

        this.userService = userService;
        this.securityService = securityService;

    }

    @PostMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public LibrarianDto createLibrarian(@RequestBody CreateLibrarianDto createLibrarianDto, @RequestHeader String authorization) {
        logger.info("Create librarian");
        User user = securityService.validateAccessToFeature(authorization, Feature.CREATE_LIBRARIAN);
        Librarian librarian = LibrarianMapper.dtoToModel(createLibrarianDto);
        userService.addLibrarian(librarian);
        return LibrarianMapper.modelToDto(librarian);

    }

    @ExceptionHandler(UnauthorizedException.class)
    protected void invalidMovieIdException(UnauthorizedException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(), exception.getMessage());
        logger.error(exception.getMessage(),exception);
    }

    @ExceptionHandler(UnknownUserException.class)
    protected void invalidMovieIdException(UnknownUserException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(), exception.getMessage());
        logger.error(exception.getMessage(),exception);
    }

    @ExceptionHandler(EmailNotUniqueException.class)
    protected void invalidMovieIdException(EmailNotUniqueException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        logger.error(exception.getMessage(),exception);
    }

}