package com.lmnml.group.controller.pdata;

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
@RequestMapping("pdata/account")
@RestController
@Api(value = "平台钱包接口", tags = {"平台钱包接口"}, description = "plat账户相关接口")
public class PdataAccount {

    @Autowired
    private IUserService userService;

    @PostMapping("total")
    @ApiOperation(value = "plat我的钱包")
    public Result platAccount(@RequestBody @Valid PlatAccount platAccount) {
        return userService.platAccount(platAccount.getUserId());
    }

    @PostMapping("detail")
    @ApiOperation(value = "plat账单明细")
    public Result platDetail(@RequestBody @Valid PlatDetail platdDetail) {
        return userService.platDetail(platdDetail.getUserId(), platdDetail.getStatus());
    }

    @Data
    @ApiModel("plat用户钱包model")
    public static class PlatAccount implements Serializable {
        @ApiModelProperty("用户id")
        @NotNull(message = "用户不能为空!")
        private String userId;
    }

    @Data
    @ApiModel("plat用户账单明细model")
    public static class PlatDetail implements Serializable {
        @ApiModelProperty("用户id")
        @NotNull(message = "用户不能为空!")
        private String userId;
        @ApiModelProperty("支出/收人 1支出 2.收人")
        @NotNull(message = "用户不能为空!")
        private Integer status;
    }
}
