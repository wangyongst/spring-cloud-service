package com.spring.cloud.service.controller;

import com.spring.cloud.service.service.UserService;
import com.spring.cloud.service.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;

@CrossOrigin("*")
@RestController
public class UserController {
    @Autowired
    public UserService uerService;
    @PostMapping(value = "/regitst")
    public Result isbn(@ModelAttribute Book book) {
        return uerService.regist();
    }

}
