package com.lmnml.group.controller.sys;

import com.lmnml.group.common.model.R;
import com.lmnml.group.common.model.Result;
import com.lmnml.group.controller.BaseController;
import com.lmnml.group.entity.app.VPlatformUser;
import com.lmnml.group.entity.web.VSystemUser;
import com.lmnml.group.service.app.IUserService;
import com.lmnml.group.util.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by daitian on 2018/4/24.
 */
@RequestMapping("sys/user")
@RestController
@Api(value = "sys用户接口", tags = {"sys用户接口"}, description = "sys用户接口")
public class SysUserController extends BaseController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "sys用户登录")
    @PostMapping("login")
    public Result sysUserLogin(@RequestBody @Valid SysUserLogin sysUserLogin) {
        //查询用户
        VSystemUser vSystemUser = userService.findSysUserByMobile(sysUserLogin.getMobile());
        if (vSystemUser == null) {
            return new Result("手机号未注册!");
        }

        if (vSystemUser.getStatus() == 0) {
            return new Result("未激活!");
        }

        if (vSystemUser.getStatus() == 2) {
            return new Result("该账户被禁用,请联系客服!");
        }

        if (!MD5.Byte32(sysUserLogin.getPassword()).equals(vSystemUser.getPassword())) {
            return new Result("密码错误!");
        }
        return new Result(R.SUCCESS, vSystemUser);
    }

    //注销.

    @ApiOperation(value = "sys根据用户id查询菜单")
    @PostMapping("home")
    public Result sysUserHome(@RequestBody @Valid SysListModel sysListModel) {
        return userService.findSysList(sysListModel.getId());
    }

    @ApiOperation(value = "sys商户资料审核列表")
    @PostMapping("list")
    public Result sysUserCheckList(@RequestBody @Valid SysCheckListModel sysCheckListModel) {
        VPlatformUser vPlatformUser= new VPlatformUser();
        vPlatformUser.setIdentifyType(sysCheckListModel.type);
        vPlatformUser.setUserType(2);//商户
        vPlatformUser.setPublishType(sysCheckListModel.getPublishType());
        vPlatformUser.setName(sysCheckListModel.getName());
        return userService.sysUserCheckList(sysCheckListModel.getCurrentPage(),7,vPlatformUser);
    }

    @ApiOperation(value = "sys封停/解封商户")
    @PostMapping("off")
    public Result sysUserOff(@RequestBody @Valid SysUserOffModel sysUserOffModel) {
        VPlatformUser vPlatformUser= new VPlatformUser();
        vPlatformUser.setId(sysUserOffModel.getId());
        vPlatformUser.setPublishType(sysUserOffModel.getType());//封停
        return userService.sysUserOff(vPlatformUser);
    }

    @ApiOperation(value = "sys商户资料审核")
    @PostMapping("check")
    public Result sysUserCheck(@RequestBody @Valid SysCheckModel sysCheckModel) {
        VPlatformUser vPlatformUser = new VPlatformUser();
        vPlatformUser.setId(sysCheckModel.getId());
        vPlatformUser.setIdentifyType(sysCheckModel.getType());
        return userService.sysUserCheck(vPlatformUser);
    }


    @ApiOperation(value = "sys会员列表")
    @PostMapping("userList")
    public Result sysUserList(@RequestBody @Valid SysUserListModel sysUserListModel) {
        VPlatformUser vPlatformUser= new VPlatformUser();
        vPlatformUser.setUserType(1);//商户
        vPlatformUser.setName(sysUserListModel.name);
        return userService.sysUserList(sysUserListModel.getCurrentPage(),7,vPlatformUser);
    }


    @Data
    @ApiModel("sys登录model")
    public static class SysUserLogin implements Serializable {
        @ApiModelProperty("手机号")
        @NotNull(message = "手机号不能为空!")
        @Pattern(regexp = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$", message = "请输入手机号!")
        private String mobile;
        @ApiModelProperty("密码")
        @NotNull(message = "密码不能为空!")
        @Size(min = 6, message = "密码最少6位")
        private String password;
    }

    @Data
    @ApiModel("sys主页model")
    public static class SysListModel implements Serializable {
        @ApiModelProperty("id")
        @NotNull(message = "id不能为空!")
        private String id;
    }
    @Data
    @ApiModel("sys封停/解封model")
    public static class SysUserOffModel implements Serializable {
        @ApiModelProperty("id")
        @NotNull(message = "id不能为空!")
        private String id;
        @ApiModelProperty("id")
        @NotNull(message = "id不能为空!")
        private Integer type;
    }

    @Data
    @ApiModel("sys审核列表model")
    public static class SysCheckListModel implements Serializable {
        @ApiModelProperty("当前页")
        @NotNull(message = "id不能为空!")
        private Integer currentPage;
        @ApiModelProperty("审核类型  3.个人 4.商户")
        @NotNull(message = "id不能为空!")
        private Integer type;
        @ApiModelProperty("商家名称")
        private String name;
        @ApiModelProperty("权限类型")
        private Integer publishType;
    }

    @Data
    @ApiModel("sys会员列表model")
    public static class SysUserListModel implements Serializable {
        @ApiModelProperty("当前页")
        @NotNull(message = "id不能为空!")
        private Integer currentPage;
        @ApiModelProperty("商家名称")
        private String name;
//        @ApiModelProperty("权限类型")
//        private Integer publishType;
    }

    @Data
    @ApiModel("sys审核model")
    public static class SysCheckModel implements Serializable {
        @ApiModelProperty("审核结果1.个人通过 2.企业通过 5.个人拒绝 6.企业拒绝")
        @NotNull(message = "审核结果不能为空")
        private Integer type;
//        @ApiModelProperty("系统用户id")
//        @NotNull(message = "系统用户id,不能为空")
//        private String sUserId;
        @ApiModelProperty("审核对象id")
        @NotNull(message = "对象id不能为空")
        private String id;
    }
}
