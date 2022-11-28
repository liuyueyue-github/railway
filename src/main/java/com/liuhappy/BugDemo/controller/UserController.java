package com.liuhappy.BugDemo.controller;

import com.liuhappy.BugDemo.service.UserService;
import com.liuhappy.BugDemo.vo.Product;
import com.liuhappy.BugDemo.vo.User;
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
