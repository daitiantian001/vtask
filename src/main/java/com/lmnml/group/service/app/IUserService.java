package com.lmnml.group.service.app;

import com.lmnml.group.common.model.Result;
import com.lmnml.group.entity.app.MsgCode;
import com.lmnml.group.entity.app.VPlatformUser;
import com.lmnml.group.entity.web.VSystemUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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

    Result payTask(String userId, String taskId, Integer type, String ip, String openId) throws Exception;

    void wxPay(Map<String, String> m, HttpServletRequest request, HttpServletResponse resp);

    Result rechargeAccount(String userId, Integer total, Integer type, String ip, String openId) throws Exception;

    void wx2Pay(Map<String, String> m, HttpServletRequest request, HttpServletResponse resp);

    VSystemUser findSysUserByMobile(String mobile);

    Result findSysList(String id);

    Result sysUserCheckList(Integer currentPage, Integer type);

    Result sysUserCheck(String targetId, Integer type, Integer sysCheckModelType, String sUserId);
}
