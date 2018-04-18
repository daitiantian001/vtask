package com.lmnml.group.entity.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * Created by daitian on 2018/4/18.
 */
@Data
public class VPlatformUserTask {
    @Id
    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("任务id")
    private String taskId;
    @ApiModelProperty("单价")
    private Integer price;
    @ApiModelProperty("0.领取任务 1.待审核 2.已完成 3.已被抢光 4.审核不通过 5.待提交")
    private Integer status;
    @ApiModelProperty("任务说明")
    private String describeBack;
    @ApiModelProperty("图片url")
    private String imgUrl;
    @ApiModelProperty("文字内容")
    private String content;
    @ApiModelProperty("领取时间")
    private Date createTime;
    @ApiModelProperty("微信名称")
    private String  wxName;
    @ApiModelProperty("真实姓名")
    private String  realName;
    @ApiModelProperty("提交时间")
    private Date commitTime;
    @ApiModelProperty("审核完成时间")
    private Date finishTime;
    @ApiModelProperty("")
    private String id_card;
}
