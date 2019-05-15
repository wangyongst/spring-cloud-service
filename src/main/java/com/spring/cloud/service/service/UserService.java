package com.spring.cloud.service.service;


import com.spring.cloud.service.entity.User;
import com.spring.cloud.service.utils.Result;

public interface UserService {

    Result regist(User user);
}
