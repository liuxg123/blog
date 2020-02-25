package com.mybatis.service.service;

import com.mybatis.api.UserOpsContext;

public interface Ops {

    Object invoke(UserOpsContext userOpsContext) throws Exception;

}
