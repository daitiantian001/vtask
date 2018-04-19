package com.lmnml.group.controller.app;

import com.lmnml.group.common.model.Result;
import com.lmnml.group.service.app.IUserService;
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
import java.io.Serializable;

/**
 * Created by daitian on 2018/4/19.
 */
@RequestMapping("app/account")
@RestController
@Api(value = "账户接口", tags = {"账户接口"}, description = "app账户相关接口")
public class UserAccountController {

    @Autowired
    private IUserService userService;

    @PostMapping("total")
    @ApiOperation(value = "app我的钱包")
    public Result appAccount(@RequestBody @Valid AppAccount appAccount) {
        return userService.platAccount(appAccount.getUserId());
    }

    @PostMapping("detail")
    @ApiOperation(value = "app账单明细")
    public Result appDetail(@RequestBody @Valid AppDetail appDetail) {
        return userService.platDetail(appDetail.getUserId(), appDetail.getStatus());
    }

    @PostMapping("bind")
    @ApiOperation(value = "app绑定账户")
    public Result appBindAccount(@RequestBody @Valid AppBindAccount appBindAccount) {
        return userService.appBindAccount(appBindAccount.getUserId(), appBindAccount.getType(),appBindAccount.getOpenId());
    }

    @Data
    @ApiModel("plat用户钱包model")
    public static class AppAccount implements Serializable {
        @ApiModelProperty("用户id")
        @NotNull(message = "用户不能为空!")
        private String userId;
    }

    @Data
    @ApiModel("plat用户账单明细model")
    public static class AppDetail implements Serializable {
        @ApiModelProperty("用户id")
        @NotNull(message = "用户不能为空!")
        private String userId;
        @ApiModelProperty("支出/收人 1支出 2.收人")
        @NotNull(message = "用户不能为空!")
        private Integer status;
    }

    @Data
    @ApiModel("plat用户钱包model")
    public static class AppBindAccount implements Serializable {
        @ApiModelProperty("用户id")
        @NotNull(message = "用户不能为空!")
        private String userId;
        @ApiModelProperty("1.微信openid 2.支付宝id")
        @NotNull(message = "请输入绑定账户类型")
        private Integer type;
        @ApiModelProperty("第三方id")
        @NotNull(message = "第三方id不能为空")
        private String openId;
    }
}
