package com.lxw.gobang.controller;

import com.lxw.gobang.common.utils.MyResult;
import com.lxw.gobang.entity.GoBangHome;
import com.lxw.gobang.entity.GoBangPiece;
import com.lxw.gobang.service.GoBangHomeService;
import com.lxw.gobang.service.GoBangPieceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName GoBangHomeController.java
 * @Description TODO
 * @createTime 2021年12月06日 17:25:00
 */
@RestController
@RequestMapping("/goBangPiece")
public class GoBangPieceController {

    @Autowired
    private GoBangPieceService goBangPieceService;

    @PostMapping("/add")
    public MyResult addGoBangHome(@RequestBody GoBangPiece goBangPiece) throws IOException {
        goBangPieceService.add(goBangPiece);
        return MyResult.success();
    }
}
