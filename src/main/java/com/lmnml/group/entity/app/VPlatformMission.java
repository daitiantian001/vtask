package com.lmnml.group.entity.app;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * Created by daitian on 2018/4/16.
 */
@Data
public class VPlatformMission {
    @Id
    private String id;
    private String taskId;
    private String userId;
    private String description;
    private String images;
    private Date createTime;
    private Integer status;
    private String name;
    private String mobile;
    private Date publishTime;
}
