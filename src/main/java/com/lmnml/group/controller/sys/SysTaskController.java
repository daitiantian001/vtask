package com.lmnml.group.controller.sys;

import com.lmnml.group.common.model.R;
import com.lmnml.group.common.model.Result;
import com.lmnml.group.entity.app.VSystemCategory;
import com.lmnml.group.service.app.ITaskService;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by daitian on 2018/4/25.
 */
@RequestMapping("sys/task")
@RestController
@Api(value = "sys商户任务审核接口", tags = {"sys商户任务审核接口"}, description = "sys商户任务审核接口")
public class SysTaskController {

    @Autowired
    private ITaskService taskService;

    @ApiOperation(value = "sys增加类别")
    @PostMapping("add")
    public Result sysCategoryAdd(@RequestBody @Valid SysCategoryAdd sysCategoryAdd) {
        VSystemCategory vSystemCategory = new VSystemCategory();
        BeanUtils.copyProperties(sysCategoryAdd, vSystemCategory);
        return taskService.sysCategoryAdd(vSystemCategory);
    }

    @ApiOperation(value = "sys修改类别")
    @PostMapping("update")
    public Result sysCategoryUpdate(@RequestBody @Valid SysCategoryUpdate sysCategoryUpdate) {
        VSystemCategory vSystemCategory = new VSystemCategory();
        BeanUtils.copyProperties(sysCategoryUpdate, vSystemCategory);
        return taskService.sysCategoryUpdate(vSystemCategory);
    }

    @ApiOperation(value = "sys任务审核列表")
    @PostMapping("list")
    public Result sysTaskList(@RequestBody @Valid SysTaskCheckListModel sysTaskCheckListModel) {
        return taskService.sysTaskList(sysTaskCheckListModel.getCurrentPage());
    }

    @PostMapping("taskInfo")
    @ApiOperation(value = "sys任务详情")
    public Result pTaskInfo(@RequestBody @Valid PTask pTask) {
        Map map = taskService.pTaskInfo(pTask.getTaskId());
        return new Result(R.SUCCESS, map);
    }

    @ApiOperation(value = "sys商户任务审核")
    @PostMapping("check")
    public Result sysTaskCheck(@RequestBody @Valid SysTaskCheck sysTaskCheck) {
        return taskService.sysTaskCheck(sysTaskCheck.getTargetId(), sysTaskCheck.getResult(), sysTaskCheck.getSUserId());
    }

    @Data
    @ApiModel("sys类别添加model")
    public static class SysCategoryAdd implements Serializable {
        @ApiModelProperty("图标url")
        @NotNull(message = "请上传图标")
        private String icon;
        @ApiModelProperty("请输入名称")
        @NotNull(message = "名称不能为空")
        private String name;
    }

    @Data
    @ApiModel("sys类别修改model")
    public static class SysCategoryUpdate implements Serializable {
        @ApiModelProperty("图标url")
        @NotNull(message = "请上传图标")
        private String icon;
        @ApiModelProperty("请输入名称")
        @NotNull(message = "名称不能为空")
        private String name;
        @ApiModelProperty("请输入id")
        @NotNull(message = "id不能为空")
        private String id;
        @ApiModelProperty("状态 0.正常 1.禁用")
        @NotNull(message = "状态不能为空")
        private Integer status;
    }

    @Data
    @ApiModel("sys任务审核列表model")
    public static class SysTaskCheckListModel implements Serializable {
        @ApiModelProperty("当前页")
        @NotNull(message = "id不能为空!")
        private Integer currentPage;
    }

    @Data
    @ApiModel("sys任务审核model")
    public static class SysTaskCheck implements Serializable {
        @ApiModelProperty("审核结果1.通过 2.拒绝")
        @NotNull(message = "审核结果不能为空")
        private Integer result;
        @ApiModelProperty("系统用户id")
        @NotNull(message = "系统用户id,不能为空")
        private String sUserId;
        @ApiModelProperty("审核对象id")
        @NotNull(message = "对象id不能为空")
        private String targetId;
    }

    @Data
    @ApiModel("plat任务主键model")
    public static class PTask implements Serializable {
        @ApiModelProperty("任务id")
        @NotNull(message = "请输入任务id")
        private String taskId;
    }

}
