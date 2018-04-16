package com.lmnml.group.dao.app;

import com.lmnml.group.entity.app.VPlatformUser;
import com.lmnml.group.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by daitian on 2018/4/15.
 */
@Mapper
public interface VPlatFormUserMapper extends MyMapper<VPlatformUser> {

    @Select("select *  from v_platform_user where mobile=#{mobile} limit 1")
    VPlatformUser findUserByMobile(@Param("mobile") String mobile);
}
