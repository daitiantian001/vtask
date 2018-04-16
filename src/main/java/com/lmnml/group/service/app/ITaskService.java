package com.lmnml.group.service.app;

import com.lmnml.group.entity.app.VPlatformStep;
import com.lmnml.group.entity.app.VPlatformTask;
import com.lmnml.group.entity.app.VSystemCategory;

import java.util.List;

/**
 * Created by daitian on 2018/4/16.
 */
public interface ITaskService {
    List<VSystemCategory> categoty();

    List taskList(Integer currentPage);

    Integer total(String type);

    VPlatformTask findTaskInfo(String taskId);

    List<VPlatformStep> findTaskStep(String taskId);

    Integer total();

    List taskList(Integer currentPage, String type);
}
