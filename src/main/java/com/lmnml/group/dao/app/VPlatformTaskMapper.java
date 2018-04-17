package com.lmnml.group.dao.app;

import com.lmnml.group.entity.app.VPlatformTask;
import com.lmnml.group.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by daitian on 2018/4/16.
 */
@Mapper
public interface VPlatformTaskMapper extends MyMapper<VPlatformTask> {
    @Select("SELECT vpt.id taskId,vpt.name,vsc.icon,vpt.price\n" +
            "FROM v_platform_task vpt LEFT JOIN v_system_category vsc ON vpt.category_id=vsc.id\n" +
            "WHERE vpt.status=1 AND vpt.end_time>NOW() ORDER BY vpt.create_time DESC limit #{currentPage},20")
    List<Map> findAllInfo(Integer currentPage);

    @Select("SELECT vpt.id taskId,vpt.name,vsc.icon,vpt.price\n" +
            "FROM v_platform_task vpt LEFT JOIN v_system_category vsc ON vpt.category_id=vsc.id\n" +
            "WHERE vpt.status=1 AND vpt.end_time>NOW() AND vpt.category_id=#{type} ORDER BY vpt.create_time DESC limit #{currentPage},20 ")
    List<Map> findAllInfo2(@Param("currentPage") Integer currentPage,@Param("type") String type);

    @Select("SELECT COUNT(vpt.id)\n" +
            "FROM v_platform_task vpt\n" +
            "WHERE vpt.status=1 AND vpt.end_time>NOW() AND vpt.category_id=#{type}")
    Integer totalNum2(String type);

    @Select("SELECT COUNT(vpt.id)\n" +
            "FROM v_platform_task vpt\n" +
            "WHERE vpt.status=1 AND vpt.end_time>NOW()")
    Integer totalNum();

    @Update("UPDATE v_platform_task SET last_num=last_num-1 where id=#{taskId}")
    void updateMin(String taskId);

    @Select("SELECT vps.id,vps.name,vps.price,vps.num,vps.last_num lastNum\n" +
            "FROM v_platform_task vps\n" +
            "WHERE vps.status=#{status} AND vps.user_id=#{userId}\n" +
            "ORDER BY vps.create_time DESC\n" +
            "LIMIT #{currentPage},10")
    List platTaskList(@Param("userId") String userId, @Param("status")Integer status, @Param("currentPage")Integer currentPage);

    @Select("SELECT count(vps.id)" +
            "FROM v_platform_task vps\n" +
            "WHERE vps.status=#{status} AND vps.user_id=#{userId}")
    Integer platTaskListTotal(@Param("userId") String userId, @Param("status")Integer status, @Param("currentPage")Integer currentPage);

    @Select("SELECT vpt.name,vpt.category,DATE_FORMAT(vpt.create_time,'%Y/%m/%d') createTime,DATE_FORMAT(vpt.end_time,'%Y年%m月%d日 %H:%i') endTime,vpt.status,vpt.device_type deviceType,vpt.submit_type submitType\n" +
            "FROM v_platform_task vpt\n" +
            "WHERE id=#{taskId}")
    Map pTaskInfo(String taskId);

    @Select("SELECT vpt.name,vpt.category,DATE_FORMAT(vpt.create_time,'%Y/%m/%d') createTime,DATE_FORMAT(vpt.end_time,'%Y年%m月%d日 %H:%i') endTime,vpt.status,vpt.device_type deviceType,vpt.submit_type submitType,vpt.last_num lastNum,vpt.price,vpc.status\n" +
            "FROM v_platform_task vpt\n" +
            "WHERE id=#{taskId}")
    Map appTaskInfo(String taskId);

    @Select("SELECT IFNULL(task_explain,\"\") taskEcplain,IFNULL(img_explain,\"\") imgExplain,IFNULL(task_link,\"\") taskLink,sort FROM v_platform_step WHERE task_id=#{taskId} order by sort asc")
    List appTaskSteps(String taskId);

    @Select("SELECT status \n" +
            "FROM v_platform_user_task\n" +
            "WHERE user_id={userId} AND task_id=#{taskId}")
    Integer findAppUserStatus(@Param("userId") String userId, @Param("taskId")String taskId);

}
