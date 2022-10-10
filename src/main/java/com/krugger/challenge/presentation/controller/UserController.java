package com.krugger.challenge.presentation.controller;

import com.krugger.challenge.presentation.presenter.UserPresenter;
import com.krugger.challenge.service.UserService;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Generated
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/loginUser")
    public UserPresenter loginUser(@RequestParam("username") String username,
                                   @RequestParam("password") String password) {
        return userService.loginUser(username, password);

    }
}
