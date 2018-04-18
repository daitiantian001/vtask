package com.lmnml.group.controller.pdata;

import com.lmnml.group.common.model.R;
import com.lmnml.group.common.model.Result;
import com.lmnml.group.controller.BaseController;
import com.lmnml.group.entity.app.MsgCode;
import com.lmnml.group.entity.app.VPlatformUser;
import com.lmnml.group.service.app.IUserService;
import com.lmnml.group.util.AliyunSms;
import com.lmnml.group.util.CookieTools;
import com.lmnml.group.util.MD5;
import com.lmnml.group.util.StrKit;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daitian on 2018/4/16.
 */
@RestController
@RequestMapping("pdata/user")
@Api(value = "平台用户接口", tags = {"平台用户接口"}, description = "平台用户相关接口")
public class PdataController extends BaseController {

    @Autowired
    private IUserService userService;

    @PostMapping("login")
    @ApiOperation(value = "plat用户登录",notes = "登录成功cookie放用户信息puserId")
    @ApiResponses({
            @ApiResponse(code = 1, message = "成功"),
            @ApiResponse(code = 0, message = "失败")
    })
    public Object userLogin(@RequestBody @Valid PlatUserRegister platUserRegister, HttpServletResponse response) {
        //查询用户
        VPlatformUser vPlatformUser = userService.findUserByMobile(platUserRegister.getMobile(),1);
        if (vPlatformUser == null) {
            return new Result("手机号未注册!");
        }
        if (!MD5.Byte32(platUserRegister.getPassword()).equals(vPlatformUser.getPassword())) {
            return new Result("密码错误!");
        }
        CookieTools.addCookie(response, "puserId", vPlatformUser.getId());
        return new Result(R.SUCCESS, vPlatformUser);
    }

    @PostMapping("register")
    @ApiOperation(value = "plat用户注册")
    @ApiResponses({
            @ApiResponse(code = 1, message = "成功"),
            @ApiResponse(code = 0, message = "失败")
    })
    public Object userRegister(@RequestBody @Valid PlatUserRegister platUserRegister) {
        //查询用户
        VPlatformUser vPlatformUser = userService.findUserByMobile(platUserRegister.getMobile(),2);
        if (vPlatformUser != null) {
            return new Result("该手机号已注册!");
        }
        String code = userService.findMsgCode(platUserRegister.getMsgCode());
        if (!platUserRegister.getMsgCode().equals(code)) {
            return new Result("验证码错误!");
        }
        vPlatformUser = new VPlatformUser();
        vPlatformUser.setId(StrKit.ID());
        vPlatformUser.setName("赚客_" + platUserRegister.getMobile());
        vPlatformUser.setMobile(platUserRegister.getMobile());
        vPlatformUser.setPhoto("http://yuejinimg.oss-cn-beijing.aliyuncs.com/app_icon_default_photo.png");
        vPlatformUser.setBirthday(new Date());
        vPlatformUser.setIdentifyType(0);
        vPlatformUser.setSex(0);
        vPlatformUser.setAccount(0);
        vPlatformUser.setFrozenAccount(0);
        vPlatformUser.setUsedAccount(0);
        vPlatformUser.setParentId(platUserRegister.getInventCode());
        vPlatformUser.setPassword(MD5.Byte32(platUserRegister.getPassword()));
        vPlatformUser.setPublishType(1);
        vPlatformUser.setAccountStatus(0);
        vPlatformUser.setInventCode(new SimpleDateFormat("MMddHHmmss").format(new Date()) + AliyunSms.getRandNum(1000, 99999));
        vPlatformUser.setCreateTime(new Date());
        vPlatformUser.setState(0);
        vPlatformUser.setUserType(2);
        userService.insertUser(vPlatformUser);
        return new Result(R.SUCCESS);
    }


    @PostMapping(value = "msgCode")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    @ApiOperation(value = "获取验证码")
    public Result sendMsg(@RequestBody @Valid PlatMobile mobile) {

        //先查询
        VPlatformUser vPlatformUser = userService.findUserByMobile(mobile.getMobile(),1);
        if (vPlatformUser != null) {
            return new Result("该手机号已注册为商户!");
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
                return new Result("发送次数频繁,请稍后再试!");
            }
            //存储msgCode
            MsgCode msgCode = new MsgCode(mobile.getMobile(), code, new Date());
            userService.insertMsgCode(msgCode);
            return new Result(R.SUCCESS);
        }
        return new Result("请两分钟后再发送!");
    }
    @Data
    @ApiModel("palt注册model")
    public static class PlatUserRegister implements Serializable {
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
    @ApiModel("plat手机号model")
    public static class PlatMobile implements Serializable {
        @ApiModelProperty("手机号")
        @NotNull(message = "手机号不能为空!")
        @Pattern(regexp = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$", message = "请输入手机号!")
        private String mobile;
    }
}
