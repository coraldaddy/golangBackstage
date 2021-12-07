package com.lxw.gobang.service.impl;

import com.lxw.gobang.entity.CoralUser;
import com.lxw.gobang.mapper.UserMapper;
import com.lxw.gobang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName UserServiceImpl.java
 * @Description TODO
 * @createTime 2021年12月03日 09:41:00
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(CoralUser user) {
        userMapper.insert(user);
    }

    @Override
    public List<CoralUser> selectUser() {
        return userMapper.selectList(null);
    }
}
