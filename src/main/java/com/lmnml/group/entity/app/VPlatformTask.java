package com.lmnml.group.entity.app;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * Created by daitian on 2018/4/16.
 */
@Data
public class VPlatformTask {
    @Id
    private String id;
    private String categoryId;
    private String name;
    private Integer num;
    private Integer lastNum;
    private Integer price;
    private Date endTime;
    private Integer checkTime;
    //0不限 1安卓 2ios
    private Integer deviceType;
    private Integer submitType;
    private String textExplain;
    private String ImgExplain;
    private String contactorName;
    private String contactorMobile;
    private String contactorEmail;
    private Date createTime;
    private String userId;
    //1.待付款 2.审核中 3.进行中 4.已下架
    private Integer status;
    private Date updateTime;
    private Integer participate;
    private Date publishTime;
    private Integer sort;
    private String describeback;
    private Integer ratio;
    private String category;
    private Integer ctlStatus;//1.正常,2.暂停 3.关闭
    private Integer ctlType; //是否自动审核过 1否 2是
    private Integer ctlPrice; //是否自动审核过 1否 2是
}
