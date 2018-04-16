package com.lmnml.group.controller.app;

import com.lmnml.group.controller.BaseController;
import com.lmnml.group.entity.app.VPlatformMission;
import com.lmnml.group.entity.app.VPlatformStep;
import com.lmnml.group.entity.app.VPlatformTask;
import com.lmnml.group.entity.app.VSystemCategory;
import com.lmnml.group.service.app.ITaskService;
import com.lmnml.group.util.StrKit;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Date;
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
    @ApiOperation(value = "查询类别", notes = "失败获取exception中信息")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    public Object category() {
        List<VSystemCategory> vSystemCategories = taskService.categoty();
        if (vSystemCategories == null || vSystemCategories.size() == 0) {
            return R.NO("数据为空！");
        }
        return R.OK(vSystemCategories);
    }

    @PostMapping("list")
    @ApiOperation(value = "任务列表", notes = "currentPage=0 类型(categoryId)不传查所有")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    public Object taskList(Integer currentPage, String categoryId) {
        List list;
        Integer num;
        if(StrKit.isBlank(categoryId)){
            list= taskService.taskList(currentPage);
            num = taskService.total();
        }else{
            list= taskService.taskList(currentPage,categoryId);
            num = taskService.total(categoryId);

        }

        return R.OK_NUM(num, list);
    }

    @PostMapping("taskInfo")
    @ApiOperation(value = "任务详情", notes = "status: 0.领取任务 1.待审核 2.已完成 3.已经被抢光 4.审核不通过 5.待提交")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    public Object taskInfo(String userId,String taskId) {
        //查询任务详情
        VPlatformTask vPlatformTask = taskService.findTaskInfo(taskId);
        //查询任务步骤
        List<VPlatformStep> vPlatformStep =taskService.findTaskStep(taskId);

        Map map = new HashMap<>();
        map.put("taskInfo",vPlatformTask);
        map.put("taskStep",vPlatformStep);

        Integer lastNum = vPlatformTask.getLastNum();
        if(lastNum==0){
            map.put("status",3);
            return map;
        }

        Integer status =taskService.findTaskStatus(userId,taskId);
        map.put("status",status==null?0:status);
        return map;
    }

    @PostMapping("receive")
    @ApiOperation(value = "领取任务", notes = "任务详情中status为 0,4,5才可以调用该接口")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    public Object receiveTask(String userId,String taskId) {
        boolean flag=taskService.receiveTack(userId,taskId);
        if(flag){
            return R.OK();
        }
        return R.NO("任务领取失败!");
    }

    @PostMapping("submit")
    @ApiOperation(value = "提交任务", notes = "失败获取exception中信息")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    public Object submitTask(@RequestBody SubmitTask submitTask) {
        VPlatformMission vPlatformMission=new VPlatformMission();
        vPlatformMission.setStatus(5);
        vPlatformMission.setId(submitTask.getId());
        vPlatformMission.setDescription(submitTask.getDescription()==null?"":submitTask.getDescription());
        vPlatformMission.setImages(submitTask.getImages()==null?"":submitTask.getImages());
        vPlatformMission.setPublishTime(new Date());
        vPlatformMission.setName(submitTask.getName()==null?"":submitTask.getName());
        vPlatformMission.setMobile(submitTask.getMobile()==null?"":submitTask.getMobile());
        taskService.submitTask(vPlatformMission);
        return R.OK();
    }
    @Data
    @ApiModel("提交任务模型")
    public static class SubmitTask implements Serializable {
        private String id;
        private String userId;
        private String taskId;
        private String description;
        private String images;
        private String mobile;
        private String name;
    }
}
