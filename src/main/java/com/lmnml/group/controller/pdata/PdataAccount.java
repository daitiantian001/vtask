package com.lmnml.group.controller.pdata;

import com.lmnml.group.common.model.Result;
import com.lmnml.group.service.app.IUserService;
import com.lmnml.group.util.IpUtil;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public Result platDetail(@RequestBody @Valid PlatDetail platDetail) {
        return userService.platDetail(platDetail.getUserId(), platDetail.getStatus());
    }

    @PostMapping("recharge")
    @ApiOperation(value = "plat充值")
    public Result rechargeAccount(@RequestBody @Valid RechargeModel rechargeModel, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ip=IpUtil.getIp(request);
        return userService.rechargeAccount(rechargeModel.getUserId(),rechargeModel.getTotal(),rechargeModel.getType(),ip,response);
    }

    @PostMapping("pay")
    @ApiOperation(value = "CHECK plat支付")
    public Result payTask(@RequestBody @Valid PayTask payTask, HttpServletRequest request) throws Exception {
        String ip=IpUtil.getIp(request);
        return userService.payTask(payTask.getUserId(), payTask.getTaskId(),payTask.getType(),ip,"WX_PAY_01");
    }

    @PostMapping("cash")
    @ApiOperation(value = "TODO plat提现")
    public Result cashAccount(@RequestBody @Valid RechargeModel rechargeModel, HttpServletRequest request) throws Exception {
        String ip=IpUtil.getIp(request);
//        return userService.cashAccount(rechargeModel.getUserId(),rechargeModel.getTotal(),rechargeModel.getType(),ip);
        return null;
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
        @NotNull(message = "收支状态不能为空")
        private Integer status;
    }

    @Data
    @ApiModel("plat支付model")
    public static class PayTask implements Serializable {
        @ApiModelProperty("用户id")
        @NotNull(message = "用户不能为空!")
        private String userId;
        @ApiModelProperty("任务id")
        @NotNull(message = "任务id不能为空")
        private String taskId;
        @ApiModelProperty("支付类型 1.账户 2.微信 3.支付宝")
        @NotNull(message = "任务id不能为空")
        private Integer type;
    }

    @Data
    @ApiModel("plat充值model")
    public static class RechargeModel implements Serializable {
        @ApiModelProperty("用户id")
        @NotNull(message = "用户不能为空!")
        private String userId;
        @ApiModelProperty("支付类型 2.微信 3.支付宝")
        @NotNull(message = "任务id不能为空")
        private Integer type;
        @ApiModelProperty("充值金额（分）")
        @NotNull(message = "金额不能为空")
        private Integer total;
    }

}
