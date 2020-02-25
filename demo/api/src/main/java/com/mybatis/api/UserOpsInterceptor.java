package com.mybatis.api;

public interface UserOpsInterceptor {

    default void preInvoke(UserOpsContext userOpsContext) {

    }

    default void postInvoke(UserOpsContext userOpsContext) {

    }

}
