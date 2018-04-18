package com.lmnml.group.controller.app;

import com.lmnml.group.common.model.R;
import com.lmnml.group.common.model.Result;
import com.lmnml.group.controller.BaseController;
import com.lmnml.group.entity.MyPageInfo;
import com.lmnml.group.entity.app.VPlatformUserTask;
import com.lmnml.group.entity.app.VSystemCategory;
import com.lmnml.group.service.app.ITaskService;
import com.lmnml.group.util.StrKit;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daitian on 2018/4/16.
 */
@RequestMapping("app/task")
@RestController
@Api(value = "任务接口", tags = {"任务接口"}, description = "app任务相关接口")
public class TaskController extends BaseController {

    @Autowired
    private ITaskService taskService;

    @GetMapping("category")
    @ApiOperation(value = "查询类别")
    @ApiResponses({
            @ApiResponse(code = 1, message = "成功"),
            @ApiResponse(code = 0, message = "失败")
    })
    public Result category() {
        List<VSystemCategory> vSystemCategories = taskService.categoty();
        if (vSystemCategories == null || vSystemCategories.size() == 0) {
            return new Result("数据为空!");
        }
        return new Result(R.SUCCESS, vSystemCategories);
    }

    @PostMapping("list")
    @ApiOperation(value = "任务列表")
    @ApiResponses({
            @ApiResponse(code = 1, message = "成功"),
            @ApiResponse(code = 0, message = "失败")
    })
    public Result taskList(@RequestBody @Valid AppTaskList taskList) {
        List list;
        Integer num;
        String categoryId = taskList.categoryId;
        Integer currentPage = taskList.getCurrentPage() ;
        if (StrKit.isBlank(categoryId)) {
            list = taskService.taskList(currentPage);
            num = taskService.total();
        } else {
            list = taskService.taskList(currentPage, categoryId);
            num = taskService.total(categoryId);
        }
        Map map = new HashMap();
        map.put("pageInfo", new MyPageInfo(currentPage, num));
        map.put("tasks", list);
        return new Result(R.SUCCESS, map);
    }

    @PostMapping("taskInfo")
    @ApiOperation(value = "任务详情", notes = "status: 0.领取任务 1.待审核 2.已完成 3.已经被抢光 4.审核不通过 5.待提交 6.已下架")
    @ApiResponses({
            @ApiResponse(code = 1, message = "成功"),
            @ApiResponse(code = 0, message = "失败")
    })
    public Result taskInfo(@RequestBody @Valid AppTaskInfo appTaskInfo) {
        Map map = taskService.appTaskInfo(appTaskInfo.getTaskId(), appTaskInfo.getUserId());
        return new Result(R.SUCCESS, map);
    }

    @PostMapping("receive")
    @ApiOperation(value = "领取任务", notes = "任务详情中status为 0才可以调用该接口")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    public Result receiveTask(@RequestBody @Valid AppTaskInfo appTaskInfo) {
        return taskService.receiveTack(appTaskInfo.getUserId(), appTaskInfo.getTaskId());
    }

    @PostMapping("submit")
    @ApiOperation(value = "提交任务")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    public Result submitTask(@RequestBody @Valid SubmitTask submitTask) {
        VPlatformUserTask vPlatformUserTask = new VPlatformUserTask();
        BeanUtils.copyProperties(submitTask,vPlatformUserTask);
        vPlatformUserTask.setId(submitTask.getUserTaskId());
        vPlatformUserTask.setStatus(1);//待审核
        return taskService.submitTask(vPlatformUserTask);
    }

    @PostMapping("updateSubmit")
    @ApiOperation(value = "修改提交内容",notes = "状态为4,5调用")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    public Result updateSubmit(@RequestBody @Valid SubmitTask submitTask) {
        VPlatformUserTask vPlatformUserTask = new VPlatformUserTask();
        BeanUtils.copyProperties(submitTask,vPlatformUserTask);
        vPlatformUserTask.setId(submitTask.getUserTaskId());
        vPlatformUserTask.setStatus(1);//待审核
        return taskService.updateSubmitTask(vPlatformUserTask);
    }

    @PostMapping("userTasks")
    @ApiOperation(value = "app我的任务列表")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    public Result userTasks(@RequestBody @Valid UserTaskModel userTaskModel) {
        VPlatformUserTask vPlatformUserTask = new VPlatformUserTask();
        BeanUtils.copyProperties(userTaskModel,vPlatformUserTask);
        return taskService.userTasks(vPlatformUserTask,userTaskModel.currentPage);
    }

//    @PostMapping("findSubmit")
//    @ApiOperation(value = "查看提交内容",notes = "状态为1调用")
//    @ApiResponses({
//            @ApiResponse(code = 0, message = "成功"),
//            @ApiResponse(code = 1, message = "失败")
//    })
//    public Result updateSubmit(@RequestBody @Valid SubmitTask submitTask) {
//        VPlatformUserTask vPlatformUserTask = new VPlatformUserTask();
//        BeanUtils.copyProperties(submitTask,vPlatformUserTask);
//        vPlatformUserTask.setId(submitTask.getUserTaskId());
//        vPlatformUserTask.setStatus(1);//待审核
//        return taskService.updateSubmitTask(vPlatformUserTask);
//    }

    @Data
    @ApiModel("提交任务模型model")
    public static class SubmitTask implements Serializable {
        @ApiModelProperty("用户领取任务记录id")
        private String userTaskId;
        @ApiModelProperty("用户id")
        @NotNull(message = "用户id不能为空")
        private String userId;
        @ApiModelProperty("任务id")
        @NotNull(message = "任务id不能为空")
        private String taskId;
        @ApiModelProperty("任务内容")
        private String content;
        @ApiModelProperty("任务截图")
        private String imgUrl;
    }

    @Data
    @ApiModel("app任务列表model")
    public static class AppTaskList implements Serializable {
        @ApiModelProperty("当前页从0开始")
        @NotNull
        private Integer currentPage;
        @ApiModelProperty("类型(categoryId)不传查所有")
        private String categoryId;
    }

    @Data
    @ApiModel("app任务详情model")
    public static class AppTaskInfo implements Serializable {
        @ApiModelProperty("用户id")
        @NotNull(message = "用户id不能为空!")
        private String userId;
        @ApiModelProperty("任务id")
        @NotNull(message = "任务id不能为空!")
        private String taskId;
    }

    @Data
    @ApiModel("app我的任务model")
    public static class UserTaskModel implements Serializable{
        @ApiModelProperty("用户Id")
        @NotNull(message = "用户id不能为空!")
        private String userId;
        @ApiModelProperty("状态 1.待审核 2.已完成 4.审核不通过 5.待提交 (不传查所有)")
        private String status;
        @ApiModelProperty("当前页")
        private Integer currentPage;
    }

}