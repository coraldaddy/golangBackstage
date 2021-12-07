package com.lxw.gobang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lxw.gobang.common.utils.CommonEntity;

/**
 * @description 用户信息
 * @author  lxw
 * @updateTime 2021/12/3 9:44
 */
public class CoralUser extends CommonEntity {

    @TableId(value ="id",type = IdType.INPUT)
    private long id;

    private String userNo;

    private String userName;

    private String userPhone;

    private String userPwd;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
