package com.lmnml.group.controller.sys;

import com.lmnml.group.common.model.Result;
import com.lmnml.group.service.sys.ISysOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by daitian on 2018/5/6.
 */
@RequestMapping("sys/order")
@RestController
@Api(value = "sys订单接口", tags = {"sys订单接口"}, description = "sys订单相关接口")
public class SysOrderController {

    @Autowired
    ISysOrderService sysOrderService;

    @ApiOperation(value = "sys订单列表")
    @PostMapping("list")
    public Result sysOrderList(@RequestBody @Valid SysOrderListModel orderListModel) {
//        VPlatformTask vPlatformTask = new VPlatformTask();
//        vPlatformTask.setName(sysTaskCheckListModel.getName());
//        return taskService.sysTaskList(sysTaskCheckListModel.getCurrentPage(),7,vPlatformTask, sysTaskCheckListModel.getStatus());
        return  null;
    }

    //转账

    @Data
    @ApiModel("sys任务审核列表model")
    public static class SysOrderListModel implements Serializable {
        @ApiModelProperty("当前页")
        @NotNull(message = "id不能为空!")
        private Integer currentPage;
        @ApiModelProperty("商家名称")
        private String name;
        @ApiModelProperty("状态 2.正在招")
        private List<Integer> status;
    }

}
