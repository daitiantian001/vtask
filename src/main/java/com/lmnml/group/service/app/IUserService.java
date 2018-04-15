package com.lmnml.group.service.app;

import com.lmnml.group.entity.app.VPlatformUser;

/**
 * Created by daitian on 2018/4/15.
 */
public interface IUserService {
    VPlatformUser findUserByPwdAndMob(VPlatformUser vPlatformUser);
}
