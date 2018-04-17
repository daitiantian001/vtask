package com.lmnml.group.dao.app;

import com.lmnml.group.entity.app.VPlatformStep;
import com.lmnml.group.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by daitian on 2018/4/16.
 */
@Mapper
public interface VPlatformStepMapper extends MyMapper<VPlatformStep> {
    @Select("SELECT IFNULL(task_explain,\"\") taskEcplain,IFNULL(img_explain,\"\") imgExplain,IFNULL(task_link,\"\") taskLink,sort\n" +
            "FROM v_platform_step\n" +
            "WHERE task_id=#{taskId}" +
            "order by sort asc")
    List findAll(String taskId);
}
