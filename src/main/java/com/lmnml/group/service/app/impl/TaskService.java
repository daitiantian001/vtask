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
import com.lmnml.group.entity.app.*;
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
    @Autowired
    private VPlatFormUserMapper userMapper;
    @Autowired
    private VPlatformDealrecordMapper vPlatformDealrecordMapper;

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
        MyPageInfo myPageInfo = new MyPageInfo(currentPage, Integer.parseInt(pageInfo.getTotal() + ""));
        Map map = new HashMap();
        map.put("pageInfo", myPageInfo);
        map.put("userTasks", userTasks);
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
        if (taskStatus == 0) {
            VPlatformTask vPlatformTask = new VPlatformTask();
            vPlatformTask.setId(taskId);
            vPlatformTaskMapper.delete(vPlatformTask);
            VPlatformStep vPlatformStep = new VPlatformStep();
            vPlatformStep.setTaskId(taskId);
            vPlatformStepMapper.delete(vPlatformStep);
        } else {
            throw new MyTestException("订单已经支付,不能删除");
        }
    }

    @Override
    public void exportTask(String taskId, String userId, HttpServletResponse response) throws Exception {
        //查询数据
        List<Map> maps = vPlatformUserTaskMapper.findExportTask(taskId);
        ExcelUtil.export("用户任务数据导出", ExcelJSON.USER_TASK, maps, response);
    }

    @Override
    @Transactional
    public Result sysCategoryAdd(VSystemCategory vSystemCategory) {
        //查询
        VSystemCategory v = (VSystemCategory) vSystemCategoryMapper.selectOne(vSystemCategory);
        if (v != null) {
            return new Result("名称重复!");
        }
        vSystemCategory.setId(StrKit.ID());
        vSystemCategory.setStatus(0);
        vSystemCategoryMapper.insertSelective(vSystemCategory);
        return new Result(R.SUCCESS);
    }

    @Override
    @Transactional
    public Result sysCategoryUpdate(VSystemCategory vSystemCategory) {
        List<String> names = vSystemCategoryMapper.findName(vSystemCategory.getName());
        if (names.contains(vSystemCategory.getName())) {
            return new Result("名称已经存在,换个名称");
        }
        vSystemCategoryMapper.insertSelective(vSystemCategory);
        return new Result(R.SUCCESS);
    }

    @Override
    public Result sysTaskList(Integer currentPage, int i, VPlatformTask vPlatformTask, List<Integer> status) {
        Example example = new Example(VPlatformTask.class);
        String name = vPlatformTask.getName();
        vPlatformTask.setName(null);
        example.createCriteria().andEqualTo(vPlatformTask).andLike("name", StrKit.isBlank(name) ? null : '%' + name + '%').andIn("status", status);
        example.setOrderByClause("create_time desc");
        PageHelper.startPage(currentPage, i);
        List list = vPlatformTaskMapper.selectByExample(example);
        return new Result(R.SUCCESS, list);
    }

    @Override
    public Result sysUserTaskList(Integer currentPage, int i, VPlatformUserTask vPlatformTask, List<Integer> status) {
        Example example = new Example(VPlatformUserTask.class);
        String name = vPlatformTask.getName();
        vPlatformTask.setName(null);
        example.createCriteria().andEqualTo(vPlatformTask).andLike("name", StrKit.isBlank(name) ? null : '%' + name + '%').andIn("status", status);
        example.setOrderByClause("commit_time asc");
        PageHelper.startPage(currentPage, i);
        List list = vPlatformUserTaskMapper.selectByExample(example);
        return new Result(R.SUCCESS, list);
    }

    @Override
    public Result sysTaskCheck(String targetId, Integer result, String sUserId) {
        VPlatformTask vPlatformTask = new VPlatformTask();
        vPlatformTask.setId(targetId);
        if (result == 1) {
            vPlatformTask.setStatus(2);
        } else if (result == 2) {
            vPlatformTask.setStatus(5);
        }
        vPlatformTaskMapper.updateByPrimaryKeySelective(vPlatformTask);
        return new Result(R.SUCCESS);
    }

    @Override
    public Result plaUserTaskList(String taskId, Integer currentPage, String checkType) {
        List<Map> checks = vPlatformUserTaskMapper.findCheckListByTaskId(taskId, currentPage, checkType);
        Integer total = vPlatformUserTaskMapper.findCheckListTotalByTaskId(taskId, checkType);
        Map map = new HashMap();
        map.put("checks", checks);
        map.put("total", total);
        return new Result(R.SUCCESS, map);
    }

    @Override
    public Result updateTask(VPlatformTask vPlatformTask) {
        vPlatformTaskMapper.updateByPrimaryKeySelective(vPlatformTask);
        return new Result(R.SUCCESS);
    }

    @Override
    @Transactional
    public Result updateUserTask(String taskId) {
        //查询用户提交任务
        VPlatformUserTask vPlatformUserTask = (VPlatformUserTask) vPlatformUserTaskMapper.selectByPrimaryKey(taskId);
        if(vPlatformUserTask.getStatus()==2){
            return new Result("该任务已经审核过!");
        }
        //查询任务
        VPlatformTask vPlatformTask = (VPlatformTask) vPlatformTaskMapper.selectByPrimaryKey(vPlatformUserTask.getTaskId());
        //加钱
        VPlatformDealrecord vPlatformDealrecord = new VPlatformDealrecord();
        vPlatformDealrecord.setId(StrKit.ID());
        vPlatformDealrecord.setUserId(vPlatformUserTask.getUserId());
        vPlatformDealrecord.setCreateTime(new Date());
        vPlatformDealrecord.setType(4);//微任务佣金
        vPlatformDealrecord.setTaskId(vPlatformUserTask.getTaskId());
        vPlatformDealrecord.setPayType(1);//账户余额;
        vPlatformDealrecord.setStatus(2);//收人
        vPlatformDealrecord.setMoney(vPlatformTask.getPrice());
        //增加金额
        userMapper.updateAccount(vPlatformUserTask.getUserId(), +vPlatformUserTask.getPrice());
        //修改状态已完成
        vPlatformUserTask.setStatus(2);
        vPlatformUserTaskMapper.updateByPrimaryKeySelective(vPlatformUserTask);
        //生成记录
        vPlatformDealrecordMapper.insertSelective(vPlatformDealrecord);
        //提交次数+1
        vPlatformTask.setCheckTime(vPlatformTask.getCheckTime()+1);
        vPlatformTaskMapper.updateByPrimaryKeySelective(vPlatformTask);
        return new Result(R.SUCCESS);
    }

    @Override
    public void exportTaskList(String taskId,String name,HttpServletResponse response) {
        List<Map> list =vPlatformUserTaskMapper.exportTaskList(taskId);
        list.forEach(k->{
            k.get("imgUrl").toString().replaceAll(",","\r\n");

            System.out.println();
        });

        try {
            ExcelUtil.export(name,ExcelJSON.TASK_LIST,list,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
