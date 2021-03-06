package com.lmnml.group.service.app;

import com.lmnml.group.common.model.Result;
import com.lmnml.group.entity.app.*;

import javax.servlet.http.HttpServletResponse;
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

    Result receiveTack(String userId, String taskId);

    Result sendTask(VPlatformTask vPlatformTask, List<VPlatformStep> vPlatformStep);

    Map platTaskList(String userId, Integer status, Integer currentPage);

    Map pTaskInfo(String pTask);

    Map appTaskInfo(String taskId, String userId);

    Result submitTask(VPlatformUserTask vPlatformUserTask);

    Result updateSubmitTask(VPlatformUserTask vPlatformUserTask);

    Result userTasks(VPlatformUserTask vPlatformUserTask, Integer currentPage);

    void checkTask(String userTaskId, String note, int i);

    void delTask(String taskId);

    void exportTask(String taskId, String userId, HttpServletResponse response) throws Exception;

    Result sysCategoryAdd(VSystemCategory vSystemCategory);

    Result sysCategoryUpdate(VSystemCategory vSystemCategory);

    Result sysTaskList(Integer currentPage, int pageSize, VPlatformTask vPlatformTask, List<Integer> status);

    Result sysUserTaskList(Integer currentPage, int i, VPlatformUserTask vPlatformTask, List<Integer> status);

    Result sysTaskCheck(String targetId, Integer result, String sUserId, String radio);

    Result plaUserTaskList(String taskId, Integer currentPage, String checkType);

    Result updateTask(VPlatformTask vPlatformTask);

    Result updateUserTask(String taskId);

    void exportTaskList(String taskId, String name, HttpServletResponse response);

    Result ctlTask(String taskId, Integer status);
}
