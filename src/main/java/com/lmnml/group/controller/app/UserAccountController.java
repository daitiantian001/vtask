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
    @ApiOperation(value = "appt账单明细")
    public Result appDetail(@RequestBody @Valid AppDetail appDetail) {
        return userService.platDetail(appDetail.getUserId(), appDetail.getStatus());
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
}
