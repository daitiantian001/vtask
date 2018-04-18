package com.lmnml.group.controller.app;

import com.lmnml.group.common.model.R;
import com.lmnml.group.common.model.Result;
import com.lmnml.group.controller.BaseController;
import com.lmnml.group.entity.PageInfo;
import com.lmnml.group.entity.app.VSystemCategory;
import com.lmnml.group.service.app.ITaskService;
import com.lmnml.group.util.StrKit;
import io.swagger.annotations.*;
import lombok.Data;
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
        map.put("pageInfo", new PageInfo(currentPage, num));
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
    @ApiOperation(value = "TODO 领取任务", notes = "任务详情中status为 0才可以调用该接口")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    public Result receiveTask(@RequestBody @Valid AppTaskInfo appTaskInfo) {
        taskService.receiveTack(appTaskInfo.getUserId(), appTaskInfo.getTaskId());
        return new Result(R.SUCCESS);
    }

    @PostMapping("submit")
    @ApiOperation(value = "TODO 提交任务")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    public Result submitTask(@RequestBody SubmitTask submitTask) {
//        VPlatformMission vPlatformMission = new VPlatformMission();
//        vPlatformMission.setStatus(5);
//        vPlatformMission.setId(submitTask.getId());
//        vPlatformMission.setDescription(submitTask.getDescription() == null ? "" : submitTask.getDescription());
//        vPlatformMission.setImages(submitTask.getImages() == null ? "" : submitTask.getImages());
//        vPlatformMission.setPublishTime(new Date());
//        vPlatformMission.setName(submitTask.getName() == null ? "" : submitTask.getName());
//        vPlatformMission.setMobile(submitTask.getMobile() == null ? "" : submitTask.getMobile());
//        taskService.submitTask(vPlatformMission);
        return new Result(R.SUCCESS);
    }

    @Data
    @ApiModel("提交任务模型model")
    public static class SubmitTask implements Serializable {
        private String id;
        private String userId;
        private String taskId;
        private String description;
        private String images;
        private String mobile;
        private String name;
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
}