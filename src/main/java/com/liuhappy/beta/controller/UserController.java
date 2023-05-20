package com.liuhappy.beta.controller;

import com.liuhappy.beta.service.UserService;
import com.liuhappy.beta.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Grin
 * @Date 2022/8/17
 * @Description
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("user/registerUser")
    public boolean registerUser(@RequestBody User user) {
        return true;
    }
}
