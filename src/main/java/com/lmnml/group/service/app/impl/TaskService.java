package com.lmnml.group.service.app.impl;

import com.lmnml.group.dao.app.VPlatformMissionMapper;
import com.lmnml.group.dao.app.VPlatformStepMapper;
import com.lmnml.group.dao.app.VPlatformTaskMapper;
import com.lmnml.group.dao.app.VSystemCategoryMapper;
import com.lmnml.group.entity.app.VPlatformMission;
import com.lmnml.group.entity.app.VPlatformStep;
import com.lmnml.group.entity.app.VPlatformTask;
import com.lmnml.group.entity.app.VSystemCategory;
import com.lmnml.group.service.app.ITaskService;
import com.lmnml.group.util.DateKit;
import com.lmnml.group.util.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    private VPlatformMissionMapper vPlatformMissionMapper;

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
    public Integer findTaskStatus(String userId, String taskId) {
        VPlatformMission vPlatformMission = new VPlatformMission();
        vPlatformMission.setUserId(userId);
        vPlatformMission.setTaskId(taskId);
        List select = vPlatformMissionMapper.select(vPlatformMission);
        if (select == null || select.size() == 0) {
            return 0;
        }
        VPlatformMission v = (VPlatformMission) select.get(0);
        if (DateKit.getMin().compareTo(v.getCreateTime()) >= 0) {
            return 0;
        }
        return v.getStatus();
    }
    @Override
    public boolean receiveTack(String userId, String taskId) {
        //查询是否领取任务
        VPlatformMission vPlatformMission = new VPlatformMission();
        vPlatformMission.setUserId(userId);
        vPlatformMission.setTaskId(taskId);
        List select = vPlatformMissionMapper.select(vPlatformMission);
        if (select == null || select.size() == 0) {
            insertTask(userId, taskId);
            return true;
        }
        VPlatformMission v = (VPlatformMission) select.get(0);
        Integer status = v.getStatus();
        if(status !=null &&(4==status||5==status)){
            v.setCreateTime(new Date());
            vPlatformMissionMapper.updateByPrimaryKeySelective(v);
            return true;
        }
        return false;

    }

    @Override
    public boolean submitTask(VPlatformMission vPlatformMission) {
        vPlatformMissionMapper.updateByPrimaryKeySelective(vPlatformMission);
        return true;
    }

    private void insertTask(String userId, String taskId) {
        VPlatformMission vPlatformMission = new VPlatformMission();
        vPlatformMission.setUserId(userId);
        vPlatformMission.setTaskId(taskId);
        vPlatformMission.setId(StrKit.ID());
        vPlatformMission.setStatus(5);
        vPlatformMission.setCreateTime(new Date());
        vPlatformMissionMapper.insertSelective(vPlatformMission);
        vPlatformTaskMapper.updateMin(taskId);
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
        return (VPlatformTask) o;
    }

    @Override
    public List<VPlatformStep> findTaskStep(String taskId) {
        VPlatformStep vPlatformStep = new VPlatformStep();
        vPlatformStep.setTaskId(taskId);
        List select = vPlatformStepMapper.select(vPlatformStep);
        return select;
    }
}
