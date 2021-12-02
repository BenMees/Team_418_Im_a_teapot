package com.team418.api.user;

import com.team418.api.user.dto.CreateLibrarianDto;
import com.team418.api.user.dto.LibrarianDto;
import com.team418.domain.Feature;
import com.team418.domain.user.Librarian;
import com.team418.services.LibraryService;
import com.team418.services.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/librarians")
public class LibrarianController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianController.class);
    private final LibraryService libraryService;
    private final SecurityService securityService;

    public LibrarianController(LibraryService libraryService, SecurityService securityService) {
        this.libraryService = libraryService;
        this.securityService = securityService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public LibrarianDto createLibrarian(@RequestBody CreateLibrarianDto createLibrarianDto, @RequestHeader String authorization) {
        LOGGER.info("Create librarian");
        securityService.validate(authorization, Feature.CREATE_LIBRARIAN);
        Librarian librarian = LibrarianMapper.dtoToModel(createLibrarianDto);
        libraryService.addLibrarian(librarian);
        return LibrarianMapper.modelToDto(librarian);
    }

}
