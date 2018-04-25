package com.lmnml.group.dao.sys;

import com.lmnml.group.entity.web.VSystemUser;
import com.lmnml.group.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * Created by daitian on 2018/4/24.
 */
@Mapper
public interface VSystemUserMapper extends MyMapper<VSystemUser> {

    @Select("select *  from v_system_user where mobile=#{mobile} limit 1")
    VSystemUser findSysUserByMobile(String mobile);

    @Select("SELECT vsu.role_id roleId,vsr.name roleName,vsm.pid,vsrm.module_id moduleId,vsm.name,IFNULL(vsm.icon,'') icon,IFNULL(vsm.url,'') url\n" +
            "FROM v_system_user vsu LEFT JOIN v_system_role vsr ON vsu.role_id=vsr.id \n" +
            "LEFT JOIN v_system_rolemodule vsrm ON vsrm.role_id=vsr.id\n" +
            "LEFT JOIN v_system_module vsm ON vsm.id=vsrm.module_id\n" +
            "WHERE vsu.id=#{id} ORDER BY vsm.pid ASC")
    @ResultMap("com.lmnml.group.dao.sys.VSystemUserMapper.findSysList")
    Map findSysList(String id);
}
