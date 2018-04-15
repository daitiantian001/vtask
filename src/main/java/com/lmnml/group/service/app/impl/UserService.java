package com.lmnml.group.service.app.impl;

import com.lmnml.group.dao.app.VPlatFormUserMapper;
import com.lmnml.group.entity.app.VPlatformUser;
import com.lmnml.group.service.app.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by daitian on 2018/4/15.
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private VPlatFormUserMapper userMapper;

    @Override
    public VPlatformUser findUserByPwdAndMob(VPlatformUser vPlatformUser) {
        return (VPlatformUser) userMapper.selectOne();
    }
}
