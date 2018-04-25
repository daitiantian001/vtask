package com.lmnml.group.entity.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;

/**
 * Created by daitian on 2018/4/22.
 */
@Data
public class VSystemUser {
    @ApiModelProperty("主键")
    @Id
    private String id;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("状态 0.待激活 1.正常")
    private Integer status;
    @ApiModelProperty("角色")
    private String roleId;
}
