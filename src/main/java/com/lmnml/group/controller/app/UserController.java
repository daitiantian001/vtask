package com.lmnml.group.controller.app;

import com.lmnml.group.common.model.R;
import com.lmnml.group.common.model.Result;
import com.lmnml.group.controller.BaseController;
import com.lmnml.group.entity.app.MsgCode;
import com.lmnml.group.entity.app.VPlatformUser;
import com.lmnml.group.service.app.IUserService;
import com.lmnml.group.util.AliyunSms;
import com.lmnml.group.util.MD5;
import com.lmnml.group.util.StrKit;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daitian on 2018/4/15.
 */
@RestController
@RequestMapping("app/user")
@Api(value = "用户接口", tags = {"用户接口"}, description = "app用户接口")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "app用户登录")
    @PostMapping("login")
    @ApiResponses({
            @ApiResponse(code = 1, message = "成功"),
            @ApiResponse(code = 0, message = "失败", response = Result.class),
    })
    public Result userLogin(@RequestBody @Valid UserLogin userLogin) {
        //查询用户
        VPlatformUser vPlatformUser = userService.findUserByMobile(userLogin.getMobile(),1);
        if (vPlatformUser == null) {
            return new Result("手机号未注册!");
        }
        if (!MD5.Byte32(userLogin.getPassword()).equals(vPlatformUser.getPassword())) {
            return new Result("密码错误!");
        }
        return new Result(R.SUCCESS, vPlatformUser);
    }

    @ApiOperation(value = "app用户注册")
    @PostMapping("register")
    @ApiResponses({
            @ApiResponse(code = 1, message = "成功"),
            @ApiResponse(code = 0, message = "失败", response = Result.class)
    })
    public Result userRegister(@RequestBody @Valid UserRegister userRegister) {
        //查询用户
        VPlatformUser vPlatformUser = userService.findUserByMobile(userRegister.getMobile(),1);
        if (vPlatformUser != null) {
            return new Result("该手机号已注册!");
        }
        if(vPlatformUser.getAccountStatus()==0){
            return new Result("未激活!");
        }

        if(vPlatformUser.getAccountStatus()==2){
            return new Result("该账户被禁用,请联系客服!");
        }

        String code = userService.findMsgCode(userRegister.getMsgCode());
        if (!userRegister.getMsgCode().equals(code)) {
            return new Result("验证码错误!");
        }
        vPlatformUser = new VPlatformUser();
        vPlatformUser.setId(StrKit.ID());
        vPlatformUser.setName("赚客_" + userRegister.getMobile());
        vPlatformUser.setMobile(userRegister.getMobile());
        vPlatformUser.setPhoto("http://yuejinimg.oss-cn-beijing.aliyuncs.com/app_icon_default_photo.png");
        vPlatformUser.setBirthday(new Date());
        vPlatformUser.setIdentifyType(0);
        vPlatformUser.setSex(0);
        vPlatformUser.setAccount(0);
        vPlatformUser.setFrozenAccount(0);
        vPlatformUser.setUsedAccount(0);
        vPlatformUser.setParentId(userRegister.getInventCode());
        vPlatformUser.setPassword(MD5.Byte32(userRegister.getPassword()));
        vPlatformUser.setPublishType(1);
        vPlatformUser.setAccountStatus(0);
        vPlatformUser.setInventCode(new SimpleDateFormat("MMddHHmmss").format(new Date()) + AliyunSms.getRandNum(1000, 99999));
        vPlatformUser.setCreateTime(new Date());
        vPlatformUser.setState(0);
        vPlatformUser.setUserType(1);
        userService.insertUser(vPlatformUser);
        return new Result(R.SUCCESS);
    }


    @PostMapping(value = "msgCode")
    @ApiResponses({
            @ApiResponse(code = 1, message = "成功"),
            @ApiResponse(code = 0, message = "失败", response = Result.class)
    })
    @ApiOperation(value = "获取验证码", notes = "失败获取exception中信息")
    public Result sendMsg(@RequestBody @Valid Mobile mobile) {

        //先查询
        VPlatformUser vPlatformUser = userService.findUserByMobile(mobile.getMobile(),1);
        if (vPlatformUser != null) {
            return new Result("该手机号已注册为用户!");
        }

        Boolean flag = userService.canSend(mobile.getMobile());

        if (flag == null || flag) {
            String code = null;
            try {
                code = AliyunSms.sendSms(mobile.getMobile());
            } catch (Exception e) {
                return new Result("阿里云通讯异常!");
            }
            if (StrKit.isBlank(code)) {
                return new Result("发送次数频繁,稍后再试!");
            }
            //存储msgCode
            MsgCode msgCode = new MsgCode(mobile.getMobile(), code, new Date());
            userService.insertMsgCode(msgCode);
            return new Result(R.SUCCESS);
        }
        return new Result("请两分钟后再发送!");
    }

    @PostMapping(value = "updateUserInfo")
    @ApiOperation(value = "修改个人信息",response = Result.class)
    public Result updateUserInfo(@RequestBody @Valid UpdateUserInfo updateUserInfo) {
        VPlatformUser vPlatformUser=new VPlatformUser();
        BeanUtils.copyProperties(updateUserInfo,vPlatformUser);
        vPlatformUser.setId(updateUserInfo.getUserId());
        return userService.updateUserInfo(vPlatformUser);
    }

    @Data
    @ApiModel("app登录model")
    public static class UserLogin implements Serializable {
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
    @ApiModel("app注册model")
    public static class UserRegister implements Serializable {
        @ApiModelProperty("手机号")
        @NotNull(message = "手机号不能为空!")
        @Pattern(regexp = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$", message = "请输入手机号!")
        private String mobile;
        @ApiModelProperty("验证码")
        @NotNull(message = "验证码不能为空!")
        private String msgCode;
        @ApiModelProperty("密码")
        @NotNull(message = "密码不能为空!")
        private String password;
        private String inventCode;
    }

    @Data
    @ApiModel("app手机号model")
    public static class Mobile implements Serializable {
        @ApiModelProperty("手机号")
        @NotNull(message = "手机号不能为空!")
        @Pattern(regexp = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$", message = "请输入手机号!")
        private String mobile;
    }

    @Data
    @ApiModel("app修改个人信息model")
    public static class UpdateUserInfo implements Serializable {
        @ApiModelProperty("姓名")
        private String name;
        @ApiModelProperty("头像")
        private String photo;
        @ApiModelProperty("生日")
        private Date birthday;
        @ApiModelProperty("微信openId")
        private String openId;
        @ApiModelProperty("支付宝Id")
        private String zfbId;
        @ApiModelProperty("用户Id")
        private String userId;
    }
}
