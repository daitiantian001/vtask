package com.lmnml.group.entity.app;


import lombok.Data;

import java.util.Date;

/**
 * 平台用户实体类.
 * Created by daitian on 2018/4/15.
 */
@Data
public class VPlatFormUser {
    private String id;
    private String name;
    private String mobile;
    private String photo;
    private Date birthday;
    private int sex;
    private String openId;
    private String unionId;
    private String zfbId;
    private int identifyType;
    private String identifyName;
    private String identifyShortName;
    private String identifyAddress;
    private String identifyDesc;
    private String contactorName;
    private String contactorMobile;
    private String identifyPhoto;
    private int account;
    private int usedAccount;
    private int frozenAccount;
    private String parentId;
    private String password;
    private int publishType;
    private int accountStatus;
    private String inventCode;
    private Date createTime;
    private String msgCode;
    private int state;
}
