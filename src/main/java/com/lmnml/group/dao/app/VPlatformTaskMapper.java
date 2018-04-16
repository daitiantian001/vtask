package com.lmnml.group.dao.app;

import com.lmnml.group.entity.app.VPlatformTask;
import com.lmnml.group.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
