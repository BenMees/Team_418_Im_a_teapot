package com.team418.api.user;

import com.team418.api.user.dto.CreateLibrarianDto;
import com.team418.api.user.dto.LibrarianDto;
import com.team418.domain.Feature;
import com.team418.domain.user.User;
import com.team418.services.UserService;
import com.team418.services.security.SecurityService;
import com.team418.services.security.exception.UnauthorizedException;
import com.team418.services.security.exception.UnknownUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(path="/users/librarians")
public class LibrarianController {

    private static final Logger logger = LoggerFactory.getLogger(LibrarianController.class);
    private UserService userService;
    private SecurityService securityService;
    public LibrarianController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @PostMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public LibrarianDto createLibrarian(@RequestBody CreateLibrarianDto createLibrarianDto, @RequestHeader String authorization){

        User user = securityService.validateAccessToFeature(authorization, Feature.CREATE_LIBRARIAN);

        return null;
    }

    @ExceptionHandler({UnknownUserException.class, UnauthorizedException.class})
    protected void invalidMovieIdException(UnknownUserException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(), exception.getMessage());
    }

}
