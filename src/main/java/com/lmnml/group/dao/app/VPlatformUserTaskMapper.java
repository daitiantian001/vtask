package com.lmnml.group.dao.app;

import com.lmnml.group.entity.app.VPlatformUserTask;
import com.lmnml.group.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by daitian on 2018/4/18.
 */
@Mapper
public interface VPlatformUserTaskMapper extends MyMapper<VPlatformUserTask> {
    @Select("SELECT status FROM v_platform_user_task WHERE id=#{id}")
    Integer findUserTaskStatusById(String id);
}
