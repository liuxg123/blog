package com.mybatis.service.service.impl;

import com.mybatis.api.AllStepInterceptor;
import com.mybatis.api.UserOpsContext;
import com.mybatis.api.UserOpsInterceptor;
import org.springframework.stereotype.Service;

@AllStepInterceptor
@Service
public class UserOpsHook implements UserOpsInterceptor {

    private String name = "sdf";

    @Override
    public void preInvoke(UserOpsContext userOpsContext) {
        System.out.println("userOpsContext begin" + userOpsContext.toString() + name);
    }

    @Override
    public void postInvoke(UserOpsContext userOpsContext) {
        System.out.println("userOpsContext" + userOpsContext.toString());
    }

}
