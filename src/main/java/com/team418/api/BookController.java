package com.team418.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    private final static Logger TEST_LOGGER = LoggerFactory.getLogger(BookController.class);

    public BookController() {
        TEST_LOGGER.info("BookController Creation");
    }

}
