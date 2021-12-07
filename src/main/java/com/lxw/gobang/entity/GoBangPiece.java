package com.lxw.gobang.entity;

import com.lxw.gobang.common.utils.CommonEntity;

/**
 * @description 棋子实体类
 * @author  lxw
 * @updateTime 2021/12/3 10:12
 */
public class GoBangPiece extends CommonEntity {

    private Long id;

    private String pieceNo;

    private String placeX;

    private String placeY;

    private String pieceStatus;

    private String pieceType;

    private String homeNo;

    private Integer pieceNum;

    public Integer getPieceNum() {
        return pieceNum;
    }

    public void setPieceNum(Integer pieceNum) {
        this.pieceNum = pieceNum;
    }

    public String getHomeNo() {
        return homeNo;
    }

    public void setHomeNo(String homeNo) {
        this.homeNo = homeNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPieceNo() {
        return pieceNo;
    }

    public void setPieceNo(String pieceNo) {
        this.pieceNo = pieceNo;
    }

    public String getPlaceX() {
        return placeX;
    }

    public void setPlaceX(String placeX) {
        this.placeX = placeX;
    }

    public String getPlaceY() {
        return placeY;
    }

    public void setPlaceY(String placeY) {
        this.placeY = placeY;
    }

    public String getPieceStatus() {
        return pieceStatus;
    }

    public void setPieceStatus(String pieceStatus) {
        this.pieceStatus = pieceStatus;
    }

    public String getPieceType() {
        return pieceType;
    }

    public void setPieceType(String pieceType) {
        this.pieceType = pieceType;
    }
}
