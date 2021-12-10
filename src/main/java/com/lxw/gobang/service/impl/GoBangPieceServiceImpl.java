package com.lxw.gobang.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lxw.gobang.common.socket.ImportDictValueSocket;
import com.lxw.gobang.entity.GoBangPiece;
import com.lxw.gobang.mapper.GoBangPieceMapper;
import com.lxw.gobang.service.GoBangPieceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName GoBangPieceServiceImpl.java
 * @Description TODO
 * @createTime 2021年12月07日 09:29:00
 */
@Service
public class GoBangPieceServiceImpl implements GoBangPieceService {

    @Autowired
    private GoBangPieceMapper goBangPieceMapper;

    @Autowired
    private ImportDictValueSocket socket;

    @Override
    public void add(GoBangPiece goBangPiece) {
        goBangPieceMapper.insert(goBangPiece);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("x",goBangPiece.getPlaceX());
        jsonObject.put("y",goBangPiece.getPlaceY());
        jsonObject.put("color",goBangPiece.getPieceColor());
//        try {
//            socket.sendInfo("downPiece",jsonObject);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
