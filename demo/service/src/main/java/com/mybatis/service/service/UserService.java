package com.mybatis.service.service;

import com.mybatis.api.UserOpsContext;
import com.mybatis.service.entity.User;

public interface UserService<T> {

    T invoke(UserOpsContext<User> userOpsContext);

}
