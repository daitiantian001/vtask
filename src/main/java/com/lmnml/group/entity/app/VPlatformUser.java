package com.lmnml.group.entity.app;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * 平台用户实体类.
 * Created by daitian on 2018/4/15.
 */
@Data
@ApiModel
public class VPlatformUser {
    @ApiModelProperty("用户id")
    @Id
    private String id;
    @ApiModelProperty("用户名称")
    private String name;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("头像")
    private String photo;
    @ApiModelProperty("生日")
    private Date birthday;
    @ApiModelProperty("性别 1.男,2女")
    private Integer sex;
    @ApiModelProperty("微信openId")
    private String openId;
    @ApiModelProperty("微信unionId")
    private String unionId;
    @ApiModelProperty("支付宝Id")
    private String zfbId;
    @ApiModelProperty("认证类型 0.未认证 1.个人 2.企业 3.个人待审核 4.企业待审核")
    private Integer identifyType;
    @ApiModelProperty("认证名称")
    private String identifyName;
    @ApiModelProperty("简称")
    private String identifyShortName;
    @ApiModelProperty("地址")
    private String identifyAddress;
    @ApiModelProperty("身份描述")
    private String identifyDesc;
    @ApiModelProperty("联系人")
    private String contactorName;
    @ApiModelProperty("联系电话")
    private String contactorMobile;
    @ApiModelProperty("身份证号")
    private String identifyNum;
    @ApiModelProperty("图片")
    private String identifyPhoto;
    @ApiModelProperty("总金额")
    private Integer account;
    @ApiModelProperty("可用金额")
    private Integer usedAccount;
    @ApiModelProperty("冻结金额")
    private Integer frozenAccount;
    @ApiModelProperty("邀请人id")
    private String parentId;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("权限 1.可以发布任务/可领取任务 2.不能发布任务/不能领取任务")
    private Integer publishType;
    @ApiModelProperty("账户状态 0.未激活 1.激活")
    private Integer accountStatus;
    @ApiModelProperty("邀请码")
    private String inventCode;
    @ApiModelProperty("注册时间")
    private Date createTime;
    @ApiModelProperty("区域 0.某地区")
    private Integer state;
    @ApiModelProperty("用户类型 1.普通用户 2.商家")
    private Integer userType;
}
