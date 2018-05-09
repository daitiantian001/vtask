package com.lmnml.group.entity.app;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by daitian on 2018/4/19.
 */
@Data
public class VPlatformDealrecord {
    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("订单生成时间")
    private Date createTime;
    @ApiModelProperty("订单类型 1.充值 2.提现 3.微任务预支付 4.微任务佣金 5.微任务邀请佣金 6.误判充值补偿 7.微任务预支付退款")
    private Integer type;
    @ApiModelProperty("金额")
    private Integer money;
    @ApiModelProperty("第三方订单id")
    private String pId;
    @ApiModelProperty("类型 1.账户余额 2.微信 3.支付宝")
    private Integer payType;
    @ApiModelProperty("支付状态")
    private String payStatus;
    @ApiModelProperty("任务id")
    private String taskId;
    @ApiModelProperty("订单数")
    private String orderNum;
    @ApiModelProperty("支付人id")
    private String sellerId;
    @ApiModelProperty("收支类型 1支出 2收人")
    private Integer status;
}
