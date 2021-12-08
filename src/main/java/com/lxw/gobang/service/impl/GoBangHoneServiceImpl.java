package com.lxw.gobang.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lxw.gobang.common.socket.ImportDictValueSocket;
import com.lxw.gobang.entity.GoBangHome;
import com.lxw.gobang.mapper.GoBangHomeMapper;
import com.lxw.gobang.service.GoBangHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;

/**
 * @description
 * @author  lxw
 * @updateTime 2021/12/6 17:31
 */
@Service
public class GoBangHoneServiceImpl implements GoBangHomeService {

    @Autowired
    private GoBangHomeMapper goBangHomeMapper;

    @Autowired
    private ImportDictValueSocket socket;

    @Override
    public void add(GoBangHome goBangHome) throws IOException {
        goBangHomeMapper.insert(goBangHome);
        JSONObject json = new JSONObject();
        json.put("data", "新建房间号:"+goBangHome.getId());
        socket.sendInfo("home",json);
    }
}
