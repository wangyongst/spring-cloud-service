package com.spring.cloud.service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.cloud.service.entity.User;
import com.spring.cloud.service.mapper.UserMapper;
import com.spring.cloud.service.repository.UserRepository;
import com.spring.cloud.service.service.UserService;
import com.spring.cloud.service.service.feign.UtilsService;
import com.spring.cloud.service.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;


@Service("UserService")
@SuppressWarnings("All")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UtilsService utilsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    //事务处理，查询操作不用写这个注解
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result regist(User user) {
        //先必须判断参数是否为空
        if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getPhone())){
            return utilsService.parameterNotEnoughWithMessage("必须参数不能为空");
        }
        //日志使用方法
        logger.warn("this is a test");
        //数据库id生成方法-调用utils微服务，所有系统统一这种办法
        user.setId(utilsService.createId().getData().toString());
        //时间格式化方法-调用utils微服务，所有系统统一这种办法
        user.setCreatetime(utilsService.formatTime(System.currentTimeMillis()).getData().toString());
        //spring data jpa写法
        User savedUser = userRepository.save(user);
        //mybatis写法
        List<User> savedUsers = userMapper.findByUsername(user.getUsername());
        savedUsers.forEach(e->{
            System.out.println(e.getUsername() + "--" + e.getPassword());
        });
        //请求外部接口方法-调用utils微服务，所有系统统一这种办法
        Result bookResult  = utilsService.get("https://api.douban.com/v2/book/isbn/9787208157408");
        System.out.println(bookResult.getData().toString());
        //Json字符串转对象方法
        ObjectMapper mapper = new ObjectMapper();
        try {
            User test = mapper.readValue(bookResult.getData().toString(), User.class);
        } catch (IOException e) {
           return utilsService.ioExceptionWithMessage(e.toString());
        }
        //对象实例转Json方法
        try {
            String json = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            return utilsService.jsonProcessingExceptionWithMessage(e.toString());
        }
        //异常处理方法-调用utils微服务，所有系统统一这种办法
        if (savedUser == null || savedUser.getId() == null) {
            Result result = utilsService.createErrorWithMessage("创建用户数据失败！");
            result.setData(user);
            return result;
        } else {
            //组装返回结果方法
            Result result = utilsService.okWithMessage("用户注册成功！");
            result.setData(savedUser);
            return result;
        }
    }
}
