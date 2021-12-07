package com.lxw.gobang.service;

import com.lxw.gobang.entity.GoBangHome;

import java.io.IOException;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName GoBangHomeService.java
 * @Description TODO
 * @createTime 2021年12月06日 17:29:00
 */
public interface GoBangHomeService {
    void add(GoBangHome goBangHome) throws IOException;
}
