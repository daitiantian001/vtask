package com.lmnml.group.controller.app;

import com.lmnml.group.controller.BaseController;
import com.lmnml.group.entity.app.VPlatformStep;
import com.lmnml.group.entity.app.VPlatformTask;
import com.lmnml.group.entity.app.VSystemCategory;
import com.lmnml.group.service.app.ITaskService;
import com.lmnml.group.util.StrKit;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiOperation(value = "任务列表", notes = "失败获取exception中信息")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页",required = true,defaultValue = "0",name = "currentPage"),
            @ApiImplicitParam(value = "类型(categoryId)不传查所有",name = "type"),
    })
    public Object taskList(Integer currentPage, String type) {
        List list;
        Integer num;
        if(StrKit.isBlank(type)){
            list= taskService.taskList(currentPage);
            num = taskService.total();
        }else{
            list= taskService.taskList(currentPage,type);
            num = taskService.total(type);

        }

        return R.OK_NUM(num, list);
    }

    @PostMapping("taskInfo")
    @ApiOperation(value = "任务详情", notes = "失败获取exception中信息")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    public Object taskInfo(String taskId) {
        //查询任务详情
        VPlatformTask vPlatformTask = taskService.findTaskInfo(taskId);
        //查询任务步骤
        List<VPlatformStep> vPlatformStep =taskService.findTaskStep(taskId);
        //TODO 是否已经领取过该任务
        Map map = new HashMap<>();
        map.put("taskInfo",vPlatformTask);
        map.put("taskStep",vPlatformStep);
        return map;
    }
}
