package com.lxw.gobang.common.utils;

import java.util.Date;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName CommonEntity.java
 * @Description TODO
 * @createTime 2021年12月03日 09:55:00
 */
public class CommonEntity {

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
