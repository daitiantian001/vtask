package com.lmnml.group.service.app.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmnml.group.common.excel.ExcelJSON;
import com.lmnml.group.common.excel.ExcelUtil;
import com.lmnml.group.common.exception.MyTestException;
import com.lmnml.group.common.model.R;
import com.lmnml.group.common.model.Result;
import com.lmnml.group.dao.app.*;
import com.lmnml.group.entity.MyPageInfo;
import com.lmnml.group.entity.app.VPlatformStep;
import com.lmnml.group.entity.app.VPlatformTask;
import com.lmnml.group.entity.app.VPlatformUserTask;
import com.lmnml.group.entity.app.VSystemCategory;
import com.lmnml.group.service.app.ITaskService;
import com.lmnml.group.util.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private VPlatformUserTaskMapper vPlatformUserTaskMapper;

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
    @Transactional
    public Result receiveTack(String userId, String taskId) {
        Integer taskStatus = vPlatformTaskMapper.findTaskStatus(taskId);
        if (taskStatus == 3) {
            return new Result("任务已经下架,不能领取");
        } else if (taskStatus == -1) {
            return new Result("任务被系统管理员关闭,不能领取");
        }
        //查询是否领取任务
        Integer status = vPlatformTaskMapper.findAppUserStatus(userId, taskId);
        if (status == null) {
            //查询价格
            Integer price = vPlatformTaskMapper.findTaskPrice(taskId);
            String userTaskId = insertTask(userId, taskId, price);
            Map map = new HashMap();
            map.put("userTaskId", userTaskId);
            return new Result(R.SUCCESS, map);
        }
        return new Result("已经领取过该任务");
    }

    @Override
    @Transactional
    public void sendTask(VPlatformTask vPlatformTask, List<VPlatformStep> vPlatformStep) {
        vPlatformTaskMapper.insertSelective(vPlatformTask);
        vPlatformStep.forEach(k -> vPlatformStepMapper.insertSelective(k));
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

    private String insertTask(String userId, String taskId, Integer price) {
        VPlatformUserTask vPlatformUserTask = new VPlatformUserTask();
        String id = StrKit.ID();
        vPlatformUserTask.setId(id);
        vPlatformUserTask.setUserId(userId);
        vPlatformUserTask.setTaskId(taskId);
        vPlatformUserTask.setStatus(5);
        vPlatformUserTask.setCreateTime(new Date());
        vPlatformUserTask.setPrice(price);
        vPlatformUserTaskMapper.insertSelective(vPlatformUserTask);
        vPlatformTaskMapper.updateMin(taskId);
        return id;
    }

    @Override
    @Transactional
    public Result submitTask(VPlatformUserTask vPlatformUserTask) {

        Integer taskStatus = vPlatformTaskMapper.findTaskStatus(vPlatformUserTask.getTaskId());

        Integer userTaskStatus = vPlatformUserTaskMapper.findUserTaskStatusById(vPlatformUserTask.getId());
        if (taskStatus == 3) {
            return new Result("任务已经下架,不能提交");
        } else if (taskStatus == -1) {
            return new Result("任务被系统管理员关闭,不能提交");
        }
        if (userTaskStatus == 2) {
            return new Result("您已经完成该任务,不能提交");
        }
        vPlatformUserTaskMapper.updateByPrimaryKeySelective(vPlatformUserTask);
        return new Result(R.SUCCESS);
    }

    @Override
    public Result updateSubmitTask(VPlatformUserTask vPlatformUserTask) {
        return submitTask(vPlatformUserTask);
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
        if (Integer.parseInt(taskInfo.get("lastNum").toString()) <= 0) {
            map.put("status", 3);
            return map;
        }
        if (status == null) {
            map.put("status", 0);
            return map;
        }
        map.put("status", status);
        return map;
    }

    @Override
    public Result userTasks(VPlatformUserTask vPlatformUserTask, Integer currentPage) {
        PageHelper.startPage(currentPage + 1, 20);
        Example example = new Example(VPlatformUserTask.class);
        example.createCriteria().andEqualTo("userId", vPlatformUserTask.getUserId()).andEqualTo("status", vPlatformUserTask.getStatus());
        List userTasks = vPlatformUserTaskMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(userTasks);
        MyPageInfo myPageInfo = new MyPageInfo(currentPage , Integer.parseInt(pageInfo.getTotal() + ""));
        Map map = new HashMap();
        map.put("pageInfo",myPageInfo);
        map.put("userTasks",userTasks);
        return new Result(R.SUCCESS, myPageInfo);
    }

    @Override
    public void checkTask(String userTaskId, String note, int i) {
        VPlatformUserTask vPlatformUserTask = new VPlatformUserTask();
        vPlatformUserTask.setId(userTaskId);
        vPlatformUserTask.setNote(note);
        vPlatformUserTask.setStatus(i);
        vPlatformUserTaskMapper.updateByPrimaryKeySelective(vPlatformUserTask);
    }

    @Override
    @Transactional
    public void delTask(String taskId) {

        Integer taskStatus = vPlatformTaskMapper.findTaskStatus(taskId);
        if(taskStatus==0){
            VPlatformTask vPlatformTask = new VPlatformTask();
            vPlatformTask.setId(taskId);
            vPlatformTaskMapper.delete(vPlatformTask);
            VPlatformStep vPlatformStep=new VPlatformStep();
            vPlatformStep.setTaskId(taskId);
            vPlatformStepMapper.delete(vPlatformStep);
        }else{
            throw new MyTestException("订单已经支付,不能删除");
        }
    }

    @Override
    public void exportTask(String taskId, String userId, HttpServletResponse response) throws Exception {
        //查询数据
        List<Map> maps=vPlatformUserTaskMapper.findExportTask(taskId);
        ExcelUtil.export("用户任务数据导出", ExcelJSON.USER_TASK,maps,response);
    }
}
