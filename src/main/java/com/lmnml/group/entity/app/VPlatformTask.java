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
    private Integer deviceType;
    private Integer submitType;
    private String textExplain;
    private String ImgExplain;
    private String contactorName;
    private String contactorMobile;
    private String contactorEmail;
    private Date createTime;
    private String userId;
    private Integer status;
    private Date updateTime;
    private Integer participate;
    private Date publishTime;
    private Integer sort;
    private String describeback;
    private Integer ratio;
    private String category;
}
