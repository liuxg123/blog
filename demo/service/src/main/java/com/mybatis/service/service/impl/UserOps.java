package com.mybatis.service.service.impl;

import com.mybatis.api.AllStepInterceptor;
import com.mybatis.api.UserOpsContext;
import com.mybatis.api.UserOpsInterceptor;
import com.mybatis.service.service.Ops;
import com.mybatis.service.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserOps implements Ops {

    @Autowired
    private UserOpsInvokeService userOpsInvokeService;

    @Autowired
    private UserService userService;

    @Override
    public Object invoke(UserOpsContext userOpsContext){
        userOpsInvokeService.preInvoke(AllStepInterceptor.class, userOpsContext);
        Object invoke = userService.invoke(userOpsContext);
        userOpsInvokeService.postInvoke(AllStepInterceptor.class, userOpsContext);
        return invoke;
    }

}
