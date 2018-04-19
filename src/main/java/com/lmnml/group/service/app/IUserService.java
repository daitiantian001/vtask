package com.lmnml.group.service.app;

import com.lmnml.group.common.model.Result;
import com.lmnml.group.entity.app.MsgCode;
import com.lmnml.group.entity.app.VPlatformUser;

/**
 * Created by daitian on 2018/4/15.
 */
public interface IUserService {
    VPlatformUser findUserByMobile(String mobile, Integer userType);

    void insertMsgCode(MsgCode msgCode);

    String findMsgCode(String mobile);

    void insertUser(VPlatformUser vPlatformUser);

    Boolean canSend(String mobile);

    Result updateUserInfo(VPlatformUser vPlatformUser);

    Result platAccount(String userId);

    Result platDetail(String userId, Integer status);

    Result appBindAccount(String userId, Integer type, String openId);
}
