package com.lmnml.group.service.app.impl;

import com.lmnml.group.dao.app.VPlatformStepMapper;
import com.lmnml.group.dao.app.VPlatformTaskMapper;
import com.lmnml.group.dao.app.VSystemCategoryMapper;
import com.lmnml.group.entity.app.VPlatformStep;
import com.lmnml.group.entity.app.VPlatformTask;
import com.lmnml.group.entity.app.VSystemCategory;
import com.lmnml.group.service.app.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by daitian on 2018/4/16.
 */
@Service
public class TaskService implements ITaskService {

    @Autowired
    private VSystemCategoryMapper vSystemCategoryMapper;

    @Autowired
    private VPlatformTaskMapper vPlatformTaskMapper;

    @Autowired
    private VPlatformStepMapper vPlatformStepMapper;

    @Override
    public List<VSystemCategory> categoty() {
        VSystemCategory VSystemCategory = new VSystemCategory();
        VSystemCategory.setStatus(0);
        return vSystemCategoryMapper.select(VSystemCategory);
    }

    @Override
    public List taskList(Integer currentPage) {
        return vPlatformTaskMapper.findAllInfo(currentPage);
    }

    @Override
    public List taskList(Integer currentPage, String type) {
        return vPlatformTaskMapper.findAllInfo2(currentPage, type);
    }

    @Override
    public Integer total(String type) {
        return vPlatformTaskMapper.totalNum2(type);
    }

    @Override
    public Integer total() {
        return vPlatformTaskMapper.totalNum();
    }

    @Override
    public VPlatformTask findTaskInfo(String taskId) {
        VPlatformTask vPlatformTask = new VPlatformTask();
        vPlatformTask.setId(taskId);
        Object o = vPlatformTaskMapper.selectOne(vPlatformTask);
        return (VPlatformTask)o ;
    }

    @Override
    public List<VPlatformStep> findTaskStep(String taskId) {
        VPlatformStep vPlatformStep = new VPlatformStep();
        vPlatformStep.setTaskId(taskId);
        List select = vPlatformStepMapper.select(vPlatformStep);
        return select;
    }
}
