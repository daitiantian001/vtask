package com.lmnml.group.dao.app;

import com.lmnml.group.entity.app.VPlatformMission;
import com.lmnml.group.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by daitian on 2018/4/16.
 */
@Mapper
public interface VPlatformMissionMapper extends MyMapper<VPlatformMission> {
    @Select("SELECT count(id)=0 FROM v_platform_mission vpm\n" +
            "WHERE vpm.user_id=#{userId} AND vpm.task_id=#{taskId} AND status !=1 AND NOW() > ADDDATE(create_time,interval 30 minute) \n")
    Boolean isReceiveTask(@Param("userId") String userId, @Param("taskId")String taskId);
}
