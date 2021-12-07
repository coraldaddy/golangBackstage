package com.lxw.gobang.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lxw.gobang.entity.GameRecord;
import com.lxw.gobang.entity.GoBangHome;
import com.lxw.gobang.mapper.GameRecordMapper;
import com.lxw.gobang.service.GameRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @description
 * @author  lxw
 * @updateTime 2021/12/7 9:26
 */
@Service
public class GameRecordServiceImpl implements GameRecordService {

    @Autowired
    private GameRecordMapper gameRecordMapper;

    @Override
    public void add(GameRecord gameRecord) {
        gameRecordMapper.insert(gameRecord);
    }
}
