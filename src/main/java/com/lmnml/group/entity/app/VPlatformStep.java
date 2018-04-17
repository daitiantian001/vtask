package com.lmnml.group.entity.app;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Id;

/**
 * Created by daitian on 2018/4/16.
 */
@Data
public class VPlatformStep {
    @Id
    private String id;
    private String taskId;
    private String taskExplain;
    private String imgExplain;
    private String taskLink;
    private Integer sort;
}
