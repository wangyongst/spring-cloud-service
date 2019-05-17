package com.spring.cloud.service.service.impl;

import com.spring.cloud.service.entity.User;
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
    //事务处理，查询操作不用写这个注解
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result regist(User user) {
        Result result = new Result();
        //先必须判断参数是否为空
        if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getPhone())){
            result = utilsService.parameterNotEnoughWithMessage("必须参数不能为空");
            return  result;
        }
        //日志使用方法
        logger.warn("this is a test");
        //数据库id生成方法-调用utils微服务，所有系统统一这种办法
        user.setId(utilsService.createId().getData().toString());
        //时间格式化方法-调用utils微服务，所有系统统一这种办法
        user.setCreatetime(utilsService.formatTime(System.currentTimeMillis()).getData().toString());
        //spring data jpa
        User savedUser = userRepository.save(user);
        //请求外部接口方法-调用utils微服务，所有系统统一这种办法
        Result bookResult  = utilsService.get("https://api.douban.com/v2/book/isbn/9787208157408");
        System.out.println(bookResult.getData().toString());
        //异常处理方法-调用utils微服务，所有系统统一这种办法
        if (savedUser == null || savedUser.getId() == null) {
            result = utilsService.createErrorWithMessage("创建用户数据失败！");
            result.setData(user);
            return result;
        } else {
            //组装返回结果方法
            result = utilsService.okWithMessage("用户注册成功！");
            result.setData(savedUser);
            return result;
        }
    }
}
