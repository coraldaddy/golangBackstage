package com.lxw.gobang.entity;

import com.lxw.gobang.common.utils.CommonEntity;

/**
 * @description 游戏记录实体类
 * @author  lxw
 * @updateTime 2021/12/3 10:34
 */
public class GameRecord extends CommonEntity {
    private Long id;

    private String gameType;

    private String gameResult;

    private String homeNo;

    private String blackUser;

    private String writeUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getGameResult() {
        return gameResult;
    }

    public void setGameResult(String gameResult) {
        this.gameResult = gameResult;
    }

    public String getHomeNo() {
        return homeNo;
    }

    public void setHomeNo(String homeNo) {
        this.homeNo = homeNo;
    }

    public String getBlackUser() {
        return blackUser;
    }

    public void setBlackUser(String blackUser) {
        this.blackUser = blackUser;
    }

    public String getWriteUser() {
        return writeUser;
    }

    public void setWriteUser(String writeUser) {
        this.writeUser = writeUser;
    }
}
