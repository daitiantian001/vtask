package com.lmnml.group.dao.app;

import com.lmnml.group.entity.app.VPlatformUser;
import com.lmnml.group.util.MyMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by daitian on 2018/4/15.
 */
@Mapper
public interface VPlatFormUserMapper extends MyMapper<VPlatformUser> {

    @Select("select *  from v_platform_user where mobile=#{mobile} AND user_type=#{userType} limit 1")
    VPlatformUser findUserByMobile(@Param("mobile") String mobile,@Param("userType") Integer userType);

    @Select("SELECT vpu.account,vpu.frozen_account frozenAccount,vpu.used_account usedAccount\n" +
            "FROM v_platform_user vpu \n" +
            "WHERE vpu.id=#{userId}")
    Map platAccount(String userId);

    @Select("SELECT id,DATE_FORMAT(create_time,'%Y-%m-%d %H:%i') createTime,type,money,pay_status payStatus\n" +
            "FROM v_platform_dealrecord v\n" +
            "WHERE user_id=#{userId} AND status=#{status} \n" +
            "ORDER BY create_time DESC\n")
    List<Map> platDetail(@Param("userId") String userId, @Param("status")Integer status);
}
