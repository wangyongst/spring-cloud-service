package com.spring.cloud.service.service.impl;

import com.spring.cloud.service.entity.User;
import com.spring.cloud.service.repository.UserRepository;
import com.spring.cloud.service.service.UserService;
import com.spring.cloud.service.service.feign.UtilsService;
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
    private UtilsService utilsService;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result regist(User user) {
        Result result = new Result();
        logger.warn("this is a test");
        user.setId(utilsService.createId().getData().toString());
        user.setCreatetime(utilsService.formatTime(System.currentTimeMillis()).getData().toString());
        User savedUser = userRepository.save(user);
        Result bookResult  = utilsService.get("https://api.douban.com/v2/book/isbn/9787208157408");
        System.out.println(bookResult.getData().toString());
        if (savedUser == null || savedUser.getId() == null) {
            result = utilsService.createErrorWithMessage("创建用户数据失败！");
            result.setData(user);
            return result;
        } else {
            result = utilsService.okWithMessage("用户注册成功！");
            result.setData(savedUser);
            return result;
        }
    }
}
