package com.mybatis.service.mapper.mybatis;

import com.github.pagehelper.Page;
import com.mybatis.service.entity.User;

public interface UserMapper {

    Page<User> list(User query);

    void save(User user);

    void update(User user);

    void delete(String id);

    void deleteAll();

}
