package com.lxw.gobang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lxw.gobang.entity.GameRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description
 * @author  lxw
 * @updateTime 2021/12/6 17:31
 */
@Mapper
public interface GameRecordMapper extends BaseMapper<GameRecord> {
}
