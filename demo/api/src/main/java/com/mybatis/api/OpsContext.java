package com.mybatis.api;

public interface OpsContext {

    void setOps(String key, Object value);

    <T> T getOps(String key, Class<T> tClass);

    String getUsername();

    String getOps();

}
