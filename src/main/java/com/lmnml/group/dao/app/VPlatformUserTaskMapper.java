package com.lmnml.group.dao.app;

import com.lmnml.group.entity.app.VPlatformUserTask;
import com.lmnml.group.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by daitian on 2018/4/18.
 */
@Mapper
public interface VPlatformUserTaskMapper extends MyMapper<VPlatformUserTask> {
    @Select("SELECT status FROM v_platform_user_task WHERE id=#{id}")
    Integer findUserTaskStatusById(String id);

    @Select("SELECT ifnull(img_url,'') imgUrl,ifnull(wx_name,'') wxName,ifnull(real_name,'') realName,ifnull(content,'') content\n" +
            "FROM v_platform_user_task\n" +
            "WHERE task_id=#{taskId} AND status=1")
    List<Map> findExportTask(@Param("taskId") String taskId);
}
