package com.spring.cloud.service.service.impl;

import com.spring.cloud.service.entity.User;
import com.spring.cloud.service.repository.UserRepository;
import com.spring.cloud.service.service.UserService;
import com.spring.cloud.service.service.feign.ResultService;
import com.spring.cloud.service.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("UserService")
@SuppressWarnings("All")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ResultService resultService;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result regist(User user) {
        logger.warn("this is a test");
        User savedUser = userRepository.saveAndFlush(user);
        if(savedUser == null || savedUser.getId() ==null )  return resultService.createErrorWithDataAndMessage(user,"保存用户数据失败！");
        else return resultService.okWithMessage("User regist OK!");
    }
}
