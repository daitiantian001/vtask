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
import com.lmnml.group.util.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void receiveTack(String userId, String taskId) {
        //查询是否领取任务
        Integer status=vPlatformTaskMapper.findAppUserStatus(userId,taskId);
        if (status == null) {
            //TODO
            insertTask(userId, taskId);
        }
    }

    @Override
    public void sendTask(VPlatformTask vPlatformTask, List<VPlatformStep> vPlatformStep) {
        vPlatformTaskMapper.insertSelective(vPlatformTask);
        vPlatformStepMapper.insert(vPlatformStep);
    }

    @Override
    public Map platTaskList(String userId, Integer status, Integer currentPage) {
        List vt = vPlatformTaskMapper.platTaskList(userId, status, currentPage);
        Integer vtTotal = vPlatformTaskMapper.platTaskListTotal(userId, status, currentPage);
        Map map = new HashMap();
        map.put("tasks", vt);
        map.put("taskTotal", vtTotal);
        return map;
    }

    @Override
    public Map pTaskInfo(String taskId) {
        Map taskInfo = vPlatformTaskMapper.pTaskInfo(taskId);
        List taskSteps = vPlatformStepMapper.findAll(taskId);
        Map map = new HashMap();
        map.put("taskInfo", taskInfo);
        map.put("taskSteps", taskSteps);
        return map;
    }

    @Override
    public boolean submitTask(VPlatformMission vPlatformMission) {
        vPlatformMissionMapper.updateByPrimaryKeySelective(vPlatformMission);
        return true;
    }

    private void insertTask(String userId, String taskId) {
//        VPlatformMission vPlatformMission = new VPlatformMission();
//        vPlatformMission.setUserId(userId);
//        vPlatformMission.setTaskId(taskId);
//        vPlatformMission.setId(StrKit.ID());
//        vPlatformMission.setStatus(5);
//        vPlatformMission.setCreateTime(new Date());

//        vPlatformMissionMapper.insertSelective(vPlatformMission);
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
    public Map appTaskInfo(String taskId, String userId) {
        //查询详情
        Map taskInfo = vPlatformTaskMapper.appTaskInfo(taskId);
        //查询步骤
        List taskSteps = vPlatformTaskMapper.appTaskSteps(taskId);
        //查询状态
        Integer status = vPlatformTaskMapper.findAppUserStatus(userId, taskId);
        Map map = new HashMap();
        map.put("taskInfo", taskInfo);
        map.put("taskSteps", taskSteps);
        if(Integer.parseInt(taskInfo.get("lastNum").toString())<=0){
            map.put("status",3);
            return map;
        }
        if(status==null){
            map.put("status",0);
            return map;
        }
        map.put("status",  status);
        return map;
    }
}
