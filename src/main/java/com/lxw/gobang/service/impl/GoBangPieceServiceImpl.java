package com.lxw.gobang.service.impl;

import com.lxw.gobang.entity.GoBangPiece;
import com.lxw.gobang.mapper.GoBangPieceMapper;
import com.lxw.gobang.service.GoBangPieceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void add(GoBangPiece goBangPiece) {
        goBangPieceMapper.insert(goBangPiece);

    }
}
