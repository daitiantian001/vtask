package com.lmnml.group.service.app;

import com.lmnml.group.entity.app.MsgCode;
import com.lmnml.group.entity.app.VPlatformUser;

/**
 * Created by daitian on 2018/4/15.
 */
public interface IUserService {
    VPlatformUser findUserByMobile(String mobile);

    void insertMsgCode(MsgCode msgCode);

    String findMsgCode(String mobile);

    void insertUser(VPlatformUser vPlatformUser);

    Boolean canSend(String mobile);
}
