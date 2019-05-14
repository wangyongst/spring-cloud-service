package com.spring.cloud.service.service.impl;

import com.spring.cloud.service.repository.UserRepository;
import com.spring.cloud.service.service.UserService;
import com.spring.cloud.service.utils.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Service("UserService")
@SuppressWarnings("All")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result regist() {
        Result result = new Result();
        result.setMessage("test is ok");
        return result;
    }
}
