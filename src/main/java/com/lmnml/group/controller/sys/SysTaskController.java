package com.lmnml.group.controller.sys;

import com.lmnml.group.common.model.R;
import com.lmnml.group.common.model.Result;
import com.lmnml.group.entity.app.VPlatformTask;
import com.lmnml.group.entity.app.VPlatformUser;
import com.lmnml.group.entity.app.VPlatformUserTask;
import com.lmnml.group.entity.app.VSystemCategory;
import com.lmnml.group.service.app.ITaskService;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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

    @ApiOperation(value = "sys任务列表")
    @PostMapping("list")
    public Result sysTaskList(@RequestBody @Valid SysTaskCheckListModel sysTaskCheckListModel) {
        VPlatformTask vPlatformTask = new VPlatformTask();
        vPlatformTask.setName(sysTaskCheckListModel.getName());
        return taskService.sysTaskList(sysTaskCheckListModel.getCurrentPage(),7,vPlatformTask, sysTaskCheckListModel.getStatus());
    }

    @ApiOperation(value = "sys会员任务列表")
    @PostMapping("userList")
    public Result sysUserTaskList(@RequestBody @Valid SysTaskCheckListModel sysTaskCheckListModel) {
        VPlatformUserTask vPlatformUser = new VPlatformUserTask();
        vPlatformUser.setName(sysTaskCheckListModel.getName());
        return taskService.sysUserTaskList(sysTaskCheckListModel.getCurrentPage(),7,vPlatformUser, sysTaskCheckListModel.getStatus());
    }

    @ApiOperation(value = "sys审核任务列表")
    @PostMapping("clist")
    public Result sysCheckTaskList(@RequestBody @Valid SysTaskCheckListModel sysTaskCheckListModel) {
        VPlatformTask vPlatformTask = new VPlatformTask();
        vPlatformTask.setName(sysTaskCheckListModel.getName());
        return taskService.sysTaskList(sysTaskCheckListModel.getCurrentPage(),7,vPlatformTask,sysTaskCheckListModel.getStatus());
    }

    @PostMapping("taskInfo")
    @ApiOperation(value = "sys任务详情")
    public Result pTaskInfo(@RequestBody @Valid PTask pTask) {
        Map map = taskService.pTaskInfo(pTask.getTaskId());
        return new Result(R.SUCCESS, map);
    }

    @PostMapping("updateTask")
    @ApiOperation(value = "sys更新任务状态")
    public Result updateTask(@RequestBody @Valid UpTask upTask) {
        VPlatformTask vPlatformTask = new VPlatformTask();
        vPlatformTask.setId(upTask.getTaskId());
        vPlatformTask.setStatus(upTask.getType());
        if(upTask.getUpdateTime()!=null){
            vPlatformTask.setEndTime(upTask.getUpdateTime());
        }
        return taskService.updateTask(vPlatformTask);
    }

    @PostMapping("updateUserTask")
    @ApiOperation(value = "sys强制审核任务")
    public Result updateUserTask(@RequestBody @Valid PTask pTask) {
        return taskService.updateUserTask(pTask.getTaskId());
    }

    @ApiOperation(value = "sys商户任务审核")
    @PostMapping("check")
    public Result sysTaskCheck(@RequestBody @Valid SysTaskCheck sysTaskCheck) {
        return taskService.sysTaskCheck(sysTaskCheck.getTargetId(), sysTaskCheck.getResult(), sysTaskCheck.getSUserId(),sysTaskCheck.getRadio());
    }

    @GetMapping("export/{taskId}/{name}")
    @ApiOperation(value = "sys导出任务数据")
    public void exportTask(@PathVariable("taskId") String taskId,@PathVariable("name") String name, HttpServletResponse response) throws Exception {
        taskService.exportTaskList(taskId,name,response);
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
    @ApiModel("sys任务列表model")
    public static class SysTaskListModel implements Serializable {
        @ApiModelProperty("当前页")
        @NotNull(message = "id不能为空!")
        private Integer currentPage;
        @ApiModelProperty("商家名称")
        private String name;
        @ApiModelProperty("状态 2.正在招")
        private Integer status;
    }
    @Data
    @ApiModel("sys任务审核列表model")
    public static class SysTaskCheckListModel implements Serializable {
        @ApiModelProperty("当前页")
        @NotNull(message = "id不能为空!")
        private Integer currentPage;
        @ApiModelProperty("商家名称")
        private String name;
        @ApiModelProperty("状态 2.正在招")
        private List<Integer> status;
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
        @ApiModelProperty("佣金比例")
        private String radio;
    }

    @Data
    @ApiModel("plat任务主键model")
    public static class PTask implements Serializable {
        @ApiModelProperty("任务id")
        @NotNull(message = "请输入任务id")
        private String taskId;
    }

    @Data
    @ApiModel("plat更新任务model")
    public static class UpTask implements Serializable {
        @ApiModelProperty("任务id")
        @NotNull(message = "请输入任务id")
        private String taskId;
        @ApiModelProperty("操作类型")
        @NotNull(message = "类型不能为空")
        private Integer type;
        @ApiModelProperty("时间")
        private Date updateTime;
    }

}
