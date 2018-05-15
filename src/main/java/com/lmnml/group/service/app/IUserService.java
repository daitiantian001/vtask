package com.lmnml.group.service.app;

import com.lmnml.group.common.model.Result;
import com.lmnml.group.entity.app.MsgCode;
import com.lmnml.group.entity.app.VPlatformUser;
import com.lmnml.group.entity.web.VSystemUser;
import io.swagger.models.auth.In;

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

    Result platDetail(String userId, Integer status,Integer currentPage);

    Result appBindAccount(String userId, Integer type, String openId);

    Result payTask(String userId, String taskId, Integer type, String ip, String productId) throws Exception;

    void wxPay(Map<String, String> m);

    Result rechargeAccount(String userId, Integer total, Integer type, String ip, HttpServletResponse response) throws Exception;

    VSystemUser findSysUserByMobile(String mobile);

    Result findSysList(String id);

    String aliPay(Map<String, String> m, HttpServletRequest request, HttpServletResponse resp);

    Result sysUserCheckList(Integer currentPage, int pageSize, VPlatformUser vPlatformUser);

    Result sysUserOff(VPlatformUser vPlatformUser);

    Result sysUserCheck(VPlatformUser vPlatformUser);

    Result sysUserList(Integer currentPage, int i, VPlatformUser vPlatformUser);

    Result cashAccount(String userId, Integer total);
}
