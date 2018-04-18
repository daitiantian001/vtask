package com.lmnml.group.controller.pdata;

import com.lmnml.group.common.model.R;
import com.lmnml.group.common.model.Result;
import com.lmnml.group.controller.BaseController;
import com.lmnml.group.entity.app.VPlatformStep;
import com.lmnml.group.entity.app.VPlatformTask;
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
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by daitian on 2018/4/16.
 */
@RequestMapping("pdata/task")
@RestController
@Api(value = "平台任务接口", tags = {"平台任务接口"}, description = "平台任务相关接口")
public class PdataTaskController extends BaseController {

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

    @PostMapping(value = "sendTask")
    @ApiOperation(value = "发布任务")
    @ApiResponses({
            @ApiResponse(code = 1, message = "成功"),
            @ApiResponse(code = 0, message = "失败", response = Result.class)
    })
    public Result sendTask(@RequestBody @Valid SendTask sendTask) {
        VPlatformTask vPlatformTask = new VPlatformTask();
        BeanUtils.copyProperties(sendTask, vPlatformTask);
        String taskId = StrKit.ID();
        vPlatformTask.setId(taskId);
        vPlatformTask.setCreateTime(new Date());
        vPlatformTask.setStatus(0);//待付款
        vPlatformTask.setLastNum(sendTask.getNum());
        List<VPlatformStep> vPlatformSteps = new ArrayList<>();
        sendTask.getTaskSteps().forEach(k -> {
            VPlatformStep vPlatformStep = new VPlatformStep();
            BeanUtils.copyProperties(k, vPlatformStep);
            vPlatformStep.setId(StrKit.ID());
            vPlatformStep.setTaskId(taskId);
            vPlatformSteps.add(vPlatformStep);
        });
        taskService.sendTask(vPlatformTask, vPlatformSteps);
        return new Result(R.SUCCESS);
    }

    //TODO 二次发布任务,删除任务，主动下架任务,支付任务,任务详情

    @PostMapping("list")
    @ApiOperation(value = "任务列表", notes = "0.待付款 1.待审核 2.正在招 3.已下架 4.二次审核 -1.不通过")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    public Result ptaskList(@RequestBody PlatTaskList taskList) {
        Map map = taskService.platTaskList(taskList.getUserId(), taskList.getStatus(), taskList.getCurrentPage());
        return new Result(R.SUCCESS, map);
    }

    @PostMapping("taskInfo")
    @ApiOperation(value = "任务详情")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    public Result pTaskInfo(@RequestBody @Valid PTask pTask) {
        Map map = taskService.pTaskInfo(pTask.getTaskId());
        return new Result(R.SUCCESS, map);
    }

    @Data
    @ApiModel("plat发布任务model")
    public static class SendTask implements Serializable {
        private String categoryId;
        private String name;
        private Integer num;
        private Integer price;
        private Date endTime;
        private Integer deviceType;
        private Integer submitType;
        private String textExplain;
        private String ImgExplain;
        private String contactorName;
        private String contactorMobile;
        private String contactorEmail;
        private String userId;
        private Integer participate;
        private String describeback;
        private Integer ratio;
        private String category;
        List<PlatTaskStep> taskSteps;
    }

    @Data
    @ApiModel("plat任务步骤model")
    public static class PlatTaskStep implements Serializable {
        @ApiModelProperty("任务描述")
        private String taskExplain;
        @ApiModelProperty("图片描述")
        private String imgExplain;
        @ApiModelProperty("链接")
        private String taskLink;
        @ApiModelProperty("顺序")
        private Integer sort;
    }

    @Data
    @ApiModel("plat任务列表model")
    public static class PlatTaskList implements Serializable {
        @Size(min = -2, max = 7, message = "状态必须是数字")
        private Integer status;
        @NotNull(message = "当前页不能为空")
        private Integer currentPage;
        @NotNull(message = "用户id不能为空")
        private String userId;
    }

    @Data
    @ApiModel("plat任务主键model")
    public static class PTask implements Serializable {
        @ApiModelProperty("任务id")
        @NotNull(message = "请输入任务id")
        private String taskId;
    }
}
