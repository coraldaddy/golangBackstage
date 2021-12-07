package com.lxw.gobang.service;

import com.lxw.gobang.entity.CoralUser;

import java.util.List;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName UserService.java
 * @Description TODO
 * @createTime 2021年12月03日 09:41:00
 */
public interface UserService {
    void addUser(CoralUser user);

    List<CoralUser> selectUser();
}
