package com.spring.cloud.service.controller;

import com.spring.cloud.service.entity.User;
import com.spring.cloud.service.service.UserService;
import com.spring.cloud.service.utils.Result;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags= "用户相关服务")
@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserService uerService;


    @ApiOperation(value = "用户注册服务", notes = "提供用户注册服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号（必需）,String型", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = " 密码（必需）,String型", required = true, dataType = "String"),
            @ApiImplicitParam(name = "phone", value = " 手机号（必需）,String型", required = true, dataType = "String")
    })
    @PostMapping(value = "regist")
    public Result regist(@ModelAttribute User user) {
        return uerService.regist(user);
    }

}
