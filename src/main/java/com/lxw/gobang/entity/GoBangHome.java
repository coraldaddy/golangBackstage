package com.lxw.gobang.entity;

import com.lxw.gobang.common.utils.CommonEntity;

/**
 * @description 房间实体类
 * @author  lxw
 * @updateTime 2021/12/3 10:40
 */
public class GoBangHome extends CommonEntity {
    private Long id;

    private String homeNo;

    private String homeName;

    private Integer maxPeople;

    private String homeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHomeNo() {
        return homeNo;
    }

    public void setHomeNo(String homeNo) {
        this.homeNo = homeNo;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getHomeType() {
        return homeType;
    }

    public void setHomeType(String homeType) {
        this.homeType = homeType;
    }
}
