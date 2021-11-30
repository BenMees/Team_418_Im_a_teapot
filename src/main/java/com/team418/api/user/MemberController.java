package com.team418.api.user;

import com.team418.api.BookController;
import com.team418.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "users/members")
public class MemberController {
    private final static Logger TEST_LOGGER = LoggerFactory.getLogger(BookController.class);
    private final UserService userService;

    public MemberController(UserService userService) {
        this.userService = userService;
        TEST_LOGGER.info("MemberController");
    }


}
