package com.lmnml.group.service.app.impl;

import com.lmnml.group.common.exception.MyTestException;
import com.lmnml.group.common.model.Attach;
import com.lmnml.group.common.model.R;
import com.lmnml.group.common.model.Result;
import com.lmnml.group.common.model.WxPay;
import com.lmnml.group.common.pay.PayUtil;
import com.lmnml.group.dao.app.MsgCodeMapper;
import com.lmnml.group.dao.app.VPlatFormUserMapper;
import com.lmnml.group.dao.app.VPlatformDealrecordMapper;
import com.lmnml.group.dao.app.VPlatformTaskMapper;
import com.lmnml.group.entity.app.MsgCode;
import com.lmnml.group.entity.app.VPlatformDealrecord;
import com.lmnml.group.entity.app.VPlatformUser;
import com.lmnml.group.service.app.IUserService;
import com.lmnml.group.util.JsonUtil;
import com.lmnml.group.util.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daitian on 2018/4/15.
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private VPlatFormUserMapper userMapper;

    @Autowired
    private MsgCodeMapper msgCodeMapper;

    @Autowired
    private VPlatformTaskMapper vPlatformTaskMapper;

    @Autowired
    private VPlatformDealrecordMapper vPlatformDealrecordMapper;

    @Override
    public VPlatformUser findUserByMobile(String mobile, Integer userType) {
        return userMapper.findUserByMobile(mobile, userType);
    }

    @Override
    @Transactional
    public void insertMsgCode(MsgCode msgCode) {
        Object flag = msgCodeMapper.selectOne(new MsgCode(msgCode.getMobile()));
        if (flag == null) {
            msgCodeMapper.insertSelective(msgCode);
        } else {
            msgCodeMapper.updateByPrimaryKey(msgCode);
        }
    }

    @Override
    public String findMsgCode(String mobile) {
        return msgCodeMapper.selectCode(mobile);
    }

    @Override
    @Transactional
    public void insertUser(VPlatformUser vPlatformUser) {
        userMapper.insertSelective(vPlatformUser);
    }

    @Override
    public Boolean canSend(String mobile) {
        return msgCodeMapper.canSend(mobile);
    }

    @Override
    public Result updateUserInfo(VPlatformUser vPlatformUser) {
        userMapper.updateByPrimaryKeySelective(vPlatformUser);
        return new Result(R.SUCCESS);
    }

    @Override
    public Result platAccount(String userId) {
        Map map = userMapper.platAccount(userId);
        return new Result(R.SUCCESS, map);
    }

    @Override
    public Result platDetail(String userId, Integer status) {
        List list = userMapper.platDetail(userId, status);
        return new Result(R.SUCCESS, list);
    }

    @Override
    @Transactional
    public Result appBindAccount(String userId, Integer type, String openId) {
        VPlatformUser vPlatformUser = new VPlatformUser();
        vPlatformUser.setId(userId);
        if (type == 1) {
            vPlatformUser.setOpenId(openId);
        } else if (type == 2) {
            vPlatformUser.setZfbId(openId);
        }
        userMapper.insertSelective(vPlatformUser);
        return new Result(R.SUCCESS);
    }

    @Override
    @Transactional
    public Result payTask(String userId, String taskId, Integer type, String ip, String openId) throws Exception {
        //根据taskId查询总额
        Integer money = vPlatformTaskMapper.findTotalPriceByTd(taskId);
        Map result = new HashMap();
        String id = StrKit.ID();
        switch (type) {
            case 1:
                //查询可用余额
                Map<String, Integer> map = userMapper.platAccount(userId);
                if (map.get("usedAccount") >= money) {
                    VPlatformDealrecord vPlatformDealrecord = new VPlatformDealrecord();
                    vPlatformDealrecord.setId(id);
                    vPlatformDealrecord.setUserId(userId);
                    vPlatformDealrecord.setCreateTime(new Date());
                    vPlatformDealrecord.setType(3);//微信任务预支付
                    vPlatformDealrecord.setTaskId(taskId);
                    vPlatformDealrecord.setStatus(1);//支出
                    vPlatformDealrecord.setIp(ip);
                    //扣除金额
                    userMapper.updateAccount(userId, -money);
                    //生成记录
                    vPlatformDealrecordMapper.insert(vPlatformDealrecord);
                    //修改状态
                    vPlatformTaskMapper.updateTaskStatus(taskId, 1);//待审核
                    return new Result(R.SUCCESS);
                }
                return new Result("账户余额不足!");
            case 2:
                //微信支付
                String attach = JsonUtil.toJson(new Attach(ip, taskId, id, userId));//附带信息 ip,targetId,id
                Map<String, String> stringStringMap = PayUtil.jsPay(WxPay.jsPay("赚客-微任务支付", StrKit.ID(), money, attach, ip, openId));
                result.put("payType", 2);
                result.put("payInfo", stringStringMap);
                return new Result(R.SUCCESS, result);
            case 3:
                //支付宝支付

            default:
                return new Result(R.NET_ERROR);

        }
    }

    @Override
    @Transactional
    public void wxPay(Map<String, String> m, HttpServletRequest request, HttpServletResponse resp) {
        try {
            if (m.get("result_code").equals("SUCCESS")) {
                String sign = m.get("sign");
                String sign1 = PayUtil.getSign(m);
                String json = m.get("attach");
                Attach attach = JsonUtil.fromJson(json, Attach.class);
                if (sign.equals(sign1)) {
                    //TODO ; 判断金额是否相等
                    Integer total = Integer.parseInt(m.get("total"));
                    VPlatformDealrecord vPlatformDealrecord = new VPlatformDealrecord();
                    vPlatformDealrecord.setId(StrKit.ID());
                    vPlatformDealrecord.setUserId(attach.getUserId());
                    vPlatformDealrecord.setCreateTime(new Date());
                    vPlatformDealrecord.setType(3);//微信任务预支付
                    vPlatformDealrecord.setTaskId(attach.getTargetId());
                    vPlatformDealrecord.setStatus(1);//支出
                    vPlatformDealrecord.setIp(attach.getIp());
                    //扣除金额
                    userMapper.updateAccount(attach.getUserId(), -total);
                    //生成记录
                    vPlatformDealrecordMapper.insert(vPlatformDealrecord);
                    //修改状态
                    vPlatformTaskMapper.updateTaskStatus(attach.getTargetId(), 1);//待审核
                    resp.getWriter().write(PayUtil.setXML("SUCCESS", "OK"));
                } else {
                    throw new MyTestException(String.format("%s:时间:%s", "微信支付异常", new Date()));
                }
            } else {
                throw new MyTestException(String.format("%s:时间:%s", "微信支付异常", new Date()));
            }
        } catch (Exception e) {
            throw new MyTestException(String.format("%s:时间:%s", "微信支付异常", new Date()));
        }
    }

    @Override
    @Transactional
    public void wx2Pay(Map<String, String> m, HttpServletRequest request, HttpServletResponse resp) {
        try {
            if (m.get("result_code").equals("SUCCESS")) {
                String sign = m.get("sign");
                String sign1 = PayUtil.getSign(m);
                String json = m.get("attach");
                Attach attach = JsonUtil.fromJson(json, Attach.class);
                if (sign.equals(sign1)) {
                    //TODO ; 判断金额是否相等
                    Integer total = Integer.parseInt(m.get("total"));
                    VPlatformDealrecord vPlatformDealrecord = new VPlatformDealrecord();
                    vPlatformDealrecord.setId(StrKit.ID());
                    vPlatformDealrecord.setUserId(attach.getUserId());
                    vPlatformDealrecord.setCreateTime(new Date());
                    vPlatformDealrecord.setType(1);//微信充值
                    vPlatformDealrecord.setPId(attach.getTargetId());
                    vPlatformDealrecord.setStatus(2);//收入
                    vPlatformDealrecord.setIp(attach.getIp());
                    //增加金额
                    userMapper.updateAccount(attach.getUserId(), total);
                    //生成记录
                    vPlatformDealrecordMapper.insert(vPlatformDealrecord);
                    resp.getWriter().write(PayUtil.setXML("SUCCESS", "OK"));
                } else {
                    throw new MyTestException(String.format("%s:时间:%s", "微信支付异常", new Date()));
                }
            } else {
                throw new MyTestException(String.format("%s:时间:%s", "微信支付异常", new Date()));
            }
        } catch (Exception e) {
            throw new MyTestException(String.format("%s:时间:%s", "微信支付异常", new Date()));
        }
    }

    @Override
    public Result rechargeAccount(String userId, Integer total, Integer type, String ip, String openId) throws Exception {
        Map result = new HashMap();
        String id =StrKit.ID();
        //充值
        switch (type) {
            case 2:
                //微信支付
                String attach = JsonUtil.toJson(new Attach(ip,id,id,userId));//附带信息 ip,targetId,id
                Map<String, String> stringStringMap = PayUtil.jsPay(WxPay.js2Pay("赚客-微任务支付", StrKit.ID(), total, attach, ip, openId));
                result.put("payType", 2);
                result.put("payInfo", stringStringMap);
                return new Result(R.SUCCESS, result);
            case 3:
                return new Result(R.SUCCESS);
            default:
                return new Result(R.NET_ERROR);
        }
    }
}
