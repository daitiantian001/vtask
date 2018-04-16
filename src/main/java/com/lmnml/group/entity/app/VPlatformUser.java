package com.lmnml.group.entity.app;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("身份类型")
    private Integer identifyType;
    private String identifyName;
    private String identifyShortName;
    private String identifyAddress;
    private String identifyDesc;
    private String contactorName;
    private String contactorMobile;
    private String identifyPhoto;
    private Integer account;
    private Integer usedAccount;
    private Integer frozenAccount;
    private String parentId;
    private String password;
    private Integer publishType;
    private Integer accountStatus;
    private String inventCode;
    private Date createTime;
    private String msgCode;
    private Integer state;
}
