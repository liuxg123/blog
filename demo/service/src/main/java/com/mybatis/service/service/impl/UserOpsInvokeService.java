package com.mybatis.service.service.impl;

import com.mybatis.api.AllStepInterceptor;
import com.mybatis.api.UserOpsContext;
import com.mybatis.api.UserOpsInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserOpsInvokeService {

    @Autowired
    private List<UserOpsInterceptor> userOpsInterceptors;

    @Autowired
    private UserOpsHook userOpsHook;

    public void preInvoke(Class annotationClass, UserOpsContext userOpsContext) {
       if (!CollectionUtils.isEmpty(userOpsInterceptors)){
            userOpsInterceptors.stream().forEach(userOpsInterceptor -> {
                if (userOpsInterceptor.getClass().getAnnotation(annotationClass) != null) {
                    userOpsInterceptor.preInvoke(userOpsContext);
                }
            });
        }
    }

    public void postInvoke(final Class annotationClass, UserOpsContext userOpsContext) {
        if (!CollectionUtils.isEmpty(userOpsInterceptors)){
            userOpsInterceptors.stream().forEach(userOpsInterceptor -> {
                if (userOpsInterceptor.getClass().getAnnotation(annotationClass) != null) {
                    userOpsInterceptor.postInvoke(userOpsContext);
                }
            });
        }
    }

}
