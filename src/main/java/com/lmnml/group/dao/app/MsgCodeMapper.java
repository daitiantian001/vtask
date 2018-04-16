package com.lmnml.group.dao.app;

import com.lmnml.group.entity.app.MsgCode;
import com.lmnml.group.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by daitian on 2018/4/16.
 */
@Mapper
public interface MsgCodeMapper extends MyMapper<MsgCode> {
    @Select("SELECT m_code from msg_code where mobile=#{mobile}")
    String selectCode(String mobile);

    @Select("SELECT NOW()>ADDDATE(u_time,interval 120 second) FROM msg_code WHERE mobile=#{mobile}")
    Boolean canSend(String mobile);
}
