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

    @Select("SELECT ifnull(img_url,'') imgUrl,price,DATE_FORMAT(create_time,'%Y-%m-%d %H:%i') createTime,ifnull(DATE_FORMAT(end_Time,'%Y-%m-%d %H:%i'),'') endTime,ifnull(DATE_FORMAT(commit_time,'%Y-%m-%d %H:%i'),'') commitTime,ifnull(DATE_FORMAT(finish_time,'%Y-%m-%d %H:%i'),'') finishTime,ifnull(content,'') content,ifnull(category,'') category\n" +
            "FROM v_platform_user_task\n" +
            "WHERE task_id=#{taskId} AND status=2")
    List<Map> exportTaskList(@Param("taskId") String taskId);

    @Select("SELECT vput.id,vpu.photo,vpu.name,vput.content,vput.img_url imgUrl,DATE_FORMAT(vput.create_time,'%Y-%m-%d %H:%i') createTime\n" +
            "FROM v_platform_user_task vput\n" +
            "LEFT JOIN v_platform_user vpu ON vpu.id=vput.user_id\n" +
            "WHERE vput.status=#{checkType} AND vput.task_id=#{taskId}\n" +
            "ORDER BY vput.create_time DESC\n" +
            "LIMIT #{currentPage},100")
    List<Map> findCheckListByTaskId(@Param("taskId") String taskId, @Param("currentPage") Integer currentPage, @Param("checkType") String checkType);

    @Select("SELECT count(vput.id)\n" +
            "FROM v_platform_user_task vput\n" +
            "LEFT JOIN v_platform_user vpu ON vpu.id=vput.user_id\n" +
            "WHERE vput.status=#{checkType} AND vput.task_id=#{taskId}\n")
    Integer findCheckListTotalByTaskId(@Param("taskId") String taskId, @Param("checkType") String checkType);
}
