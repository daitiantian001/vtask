package com.lmnml.group.controller.account;

import com.lmnml.group.common.model.WxPay;
import com.lmnml.group.common.pay.PayUtil;
import com.lmnml.group.dao.app.VPlatformDealrecordMapper;
import com.lmnml.group.dao.app.VPlatformTaskMapper;
import com.lmnml.group.dao.app.VPlatformUserTaskMapper;
import com.lmnml.group.entity.app.VPlatformDealrecord;
import com.lmnml.group.entity.app.VPlatformTask;
import com.lmnml.group.entity.app.VPlatformUserTask;
import com.lmnml.group.service.app.ITaskService;
import com.lmnml.group.util.DateKit;
import com.lmnml.group.util.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by daitian on 2018/5/20.
 */
@Component
public class TimeTaskController {

    @Autowired
    private VPlatformTaskMapper vPlatformTaskMapper;

    @Autowired
    private VPlatformUserTaskMapper vPlatformUserTaskMapper;

    @Autowired
    private VPlatformDealrecordMapper vPlatformDealrecordMapper;

    @Autowired
    private ITaskService taskService;

    //修改任务状态,30秒执行一次
    @Scheduled(fixedRate = 30000)
    public void run() {
        vPlatformTaskMapper.updateExpireTaskStatus();
    }

    //自动审核.
    @Scheduled(fixedRate = 30000)
    @Transactional
    public void self() {
        Example example = new Example(VPlatformTask.class);
        example.createCriteria().andEqualTo("submitType", 2).andEqualTo("status", 3).andEqualTo("ctlType", 1).andGreaterThanOrEqualTo("endTime", DateKit.getDay(-3));
        List<VPlatformTask> list = vPlatformTaskMapper.selectByExample(example);
        List<String> ids = new ArrayList();
        list.forEach(k -> ids.add(k.getId()));

        for (String id : ids) {
            VPlatformTask vPlatformTask = new VPlatformTask();
            vPlatformTask.setId(id);
            vPlatformTask.setCtlType(2);
            vPlatformTaskMapper.updateByPrimaryKeySelective(vPlatformTask);
            vPlatformUserTaskMapper.updateAllTask(id);
            taskService.checkTask(id, "自动审核", 2);
        }
    }

    //自动退款.(滞后周期)
//    @Scheduled(fixedRate = 60000 * 60 * 12)
//    @Transactional
    public void selfR() throws Exception {
        Example example = new Example(VPlatformTask.class);
        example.createCriteria().andEqualTo("status", 3).andEqualTo("ctlPrice", 1).andBetween("endTime", DateKit.getDay(-3), new Date());
        List<VPlatformTask> list = vPlatformTaskMapper.selectByExample(example);
        for (VPlatformTask vPlatformTask : list) {
            VPlatformTask v = new VPlatformTask();
            vPlatformTask.setId(vPlatformTask.getId());
            vPlatformTask.setCtlPrice(2);
            vPlatformTaskMapper.updateByPrimaryKeySelective(vPlatformTask);
            //查询总数量
            Integer num = vPlatformTask.getNum();
            Example e = new Example(VPlatformUserTask.class);
            e.createCriteria().andEqualTo("taskId", vPlatformTask.getId()).andEqualTo("status", 2);
            //查询已经完成的数量
            Integer n = vPlatformUserTaskMapper.selectCountByExample(e);
            //退款数量/价格
            Integer total = (num - n) * vPlatformTask.getPrice();
            //查询订单号
            Example ex = new Example(VPlatformDealrecord.class);
            ex.createCriteria().andEqualTo("taskId", vPlatformTask.getId()).andEqualTo("user_id", vPlatformTask.getUserId());
            List<VPlatformDealrecord> list1 = vPlatformDealrecordMapper.selectByExample(ex);
            VPlatformDealrecord vPlatformTask1 = list1.get(0);
            String orderId = vPlatformTask1.getId();

//            VPlatformDealrecord vPlatformDealrecord = new VPlatformDealrecord();
//            vPlatformDealrecord.setId(StrKit.ID());
//            vPlatformDealrecord.setType();
            //调用退款接口  orderId, vPlatformTask.getId(),num*vPlatformTask.getPrice(),total
            //1,2,3
            switch (vPlatformTask1.getPayType()) {
                case 1:
                    break;
                case 2:
                    PayUtil.smPayBack(WxPay.smPayBack(orderId, vPlatformTask.getId(),num*vPlatformTask.getPrice(),total));
                    break;
                case 3:

                    break;
                default:
                    break;
            }

        }

    }

}
