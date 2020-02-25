package com.mybatis;

import com.mybatis.api.UserOpsContext;
import com.mybatis.api.UserOpsInterceptor;
import com.mybatis.service.entity.PageRequest;
import com.mybatis.service.entity.User;
import com.mybatis.service.service.UserService;
import com.mybatis.service.service.impl.UserOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/user")
public class UserController {

    @Autowired
    private UserOps userOps;

    @Autowired
    private List<UserOpsInterceptor> userOpsInterceptors;

    @GetMapping
    public ResponseEntity<?> list(PageRequest pageRequest, User user) {
        UserOpsContext<User> userUserOpsContext = new UserOpsContext<User>("user1", "list",
                pageRequest.getPageNumber(),pageRequest.getPageSize(), user);
        userOpsInterceptors.hashCode();
        return ResponseEntity.ok(userOps.invoke(userUserOpsContext));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody User user) {
        UserOpsContext<User> userUserOpsContext = new UserOpsContext<User>("user1", "save",
        null, null, user);
        userOps.invoke(userUserOpsContext);
        return ResponseEntity.ok("添加成功");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody User user) {
        user.setId(id);
        UserOpsContext<User> userUserOpsContext = new UserOpsContext<User>("user1", "update",
                null, null, user);
        userOps.invoke(userUserOpsContext);
        return ResponseEntity.ok("修改成功");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        User user = new User();
        user.setId(id);
        UserOpsContext<User> userUserOpsContext = new UserOpsContext<User>("user1", "delete",
                null, null, user);
        userOps.invoke(userUserOpsContext);
        return ResponseEntity.ok("删除成功");
    }

}
