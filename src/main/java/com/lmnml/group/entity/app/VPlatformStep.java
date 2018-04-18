package com.lmnml.group.entity.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;

/**
 * Created by daitian on 2018/4/16.
 */
@Data
public class VPlatformStep {
    @Id
    private String id;
    @ApiModelProperty("任务id")
    private String taskId;
    @ApiModelProperty("任务提示")
    private String taskExplain;
    @ApiModelProperty("图片提示")
    private String imgExplain;
    @ApiModelProperty("任务链接")
    private String taskLink;
    @ApiModelProperty("顺序")
    private Integer sort;
}
