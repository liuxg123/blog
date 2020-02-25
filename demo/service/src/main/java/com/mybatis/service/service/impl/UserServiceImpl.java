package com.mybatis.service.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mybatis.api.UserOpsContext;
import com.mybatis.service.entity.User;
import com.mybatis.service.mapper.mybatis.UserMapper;
import com.mybatis.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService<Object> {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserOpsInvokeService userOpsInvokeService;

    public Page<User> list(UserOpsContext<User> userUserOpsContext) {
        PageHelper.startPage(userUserOpsContext.getPageNum(), userUserOpsContext.getPageSize());
        return userMapper.list(userUserOpsContext.getEntity());
    }

    public Object save(UserOpsContext<User> userUserOpsContext) {
        userMapper.save(userUserOpsContext.getEntity());
        return null;
    }

    public Object update(UserOpsContext<User> userUserOpsContext) {
        userMapper.update(userUserOpsContext.getEntity());
        return null;
    }


    public Object delete(UserOpsContext<User> userUserOpsContext) {
        userMapper.delete(userUserOpsContext.getEntity().getId());
        return null;
    }

    public Object deleteAll() {
        userMapper.deleteAll();
        return null;
    }

    @Override
    public Object invoke(UserOpsContext<User> userOpsContext) {
        switch (userOpsContext.getOps()) {
            case "list":
                return list(userOpsContext);
            case "save":
                return save(userOpsContext);
            case "update":
                return update(userOpsContext);
            case "delete":
                return delete(userOpsContext);
            case "deleteAll":
                return deleteAll();
            default:
                return null;
        }
    }
}
