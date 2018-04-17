package com.lmnml.group.service.app;

import com.lmnml.group.controller.pdata.PdataTaskController;
import com.lmnml.group.entity.app.VPlatformMission;
import com.lmnml.group.entity.app.VPlatformStep;
import com.lmnml.group.entity.app.VPlatformTask;
import com.lmnml.group.entity.app.VSystemCategory;

import java.util.List;
import java.util.Map;

/**
 * Created by daitian on 2018/4/16.
 */
public interface ITaskService {
    List<VSystemCategory> categoty();

    List taskList(Integer currentPage);

    Integer total(String type);

    Integer total();

    List taskList(Integer currentPage, String type);

    void receiveTack(String userId, String taskId);

    boolean submitTask(VPlatformMission vPlatformMission);

    void sendTask(VPlatformTask vPlatformTask, List<VPlatformStep> vPlatformStep);

    Map platTaskList(String userId, Integer status, Integer currentPage);

    Map pTaskInfo(String pTask);

    Map appTaskInfo(String taskId, String userId);
}
