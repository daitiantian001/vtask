package com.lmnml.group.entity.app;


import lombok.Data;

import java.util.Date;

/**
 * 平台用户实体类.
 * Created by daitian on 2018/4/15.
 */
@Data
public class VPlatformUser {
    private String id;
    private String name;
    private String mobile;
    private String photo;
    private Date birthday;
    private Integer sex;
    private String openid;
    private String unionid;
    private String zfbid;
    private Integer identifytype;
    private String identifyname;
    private String identifyshortname;
    private String identifyaddress;
    private String identifydesc;
    private String contactorname;
    private String contactormobile;
    private String identifyphoto;
    private Integer account;
    private Integer usedaccount;
    private Integer frozenaccount;
    private String parentid;
    private String password;
    private Integer publishtype;
    private Integer accountstatus;
    private String inventcode;
    private Date createtime;
    private String msgcode;
    private Integer state;
}
