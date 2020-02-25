package com.mybatis.api;

public class UserOpsContext<T> implements OpsContext {

    private String username;

    private String ops;

    private Integer pageNum;

    private Integer pageSize;

    private T entity;

    public UserOpsContext(String username, String ops, Integer pageNum, Integer pageSize, T entity) {
        this.username = username;
        this.ops = ops;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.entity = entity;
    }

    @Override
    public void setOps(String key, Object value) {

    }

    @Override
    public <T> T getOps(String key, Class<T> tClass) {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getOps() {
        return ops;
    }

    public T getEntity() {
        return entity;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
