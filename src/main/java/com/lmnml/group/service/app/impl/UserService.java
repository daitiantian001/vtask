package com.lmnml.group.service.app.impl;

import com.lmnml.group.common.exception.MyTestException;
import com.lmnml.group.common.model.*;
import com.lmnml.group.common.pay.AliPayUtil;
import com.lmnml.group.common.pay.PayUtil;
import com.lmnml.group.dao.app.MsgCodeMapper;
import com.lmnml.group.dao.app.VPlatFormUserMapper;
import com.lmnml.group.dao.app.VPlatformDealrecordMapper;
import com.lmnml.group.dao.app.VPlatformTaskMapper;
import com.lmnml.group.dao.sys.VSystemUserMapper;
import com.lmnml.group.entity.app.MsgCode;
import com.lmnml.group.entity.app.VPlatformDealrecord;
import com.lmnml.group.entity.app.VPlatformUser;
import com.lmnml.group.entity.web.VSystemUser;
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
    private VSystemUserMapper vSystemUserMapper;

    @Autowired
    private MsgCodeMapper msgCodeMapper;

    @Autowired
    private VPlatformTaskMapper vPlatformTaskMapper;

    @Autowired
    private VPlatformDealrecordMapper vPlatformDealrecordMapper;

    @Override
    public VPlatformUser findUserByMobile(String mobile, Integer userType) {
        VPlatformUser vPlatformUser = new VPlatformUser();
        vPlatformUser.setMobile(mobile);
        vPlatformUser.setUserType(userType);
        return (VPlatformUser) userMapper.selectOne(vPlatformUser);
    }

    @Override
    public VSystemUser findSysUserByMobile(String mobile) {
        return vSystemUserMapper.findSysUserByMobile(mobile);
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
        Integer total=userMapper.platDetailTotal(userId,status);
        Map map =new HashMap();
        map.put("details",list);
        map.put("total",total);
        return new Result(R.SUCCESS, map);
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
        String attach = JsonUtil.toJson(new Attach(taskId,userId));//附带信息 targetId,id
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
                    vPlatformDealrecord.setPayType(1);//余额
                    vPlatformDealrecord.setStatus(1);//支出
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
                Map<String, String> stringStringMap = PayUtil.jsPay(WxPay.jsPay("赚客-微任务支付", id, money, attach, ip, openId));
                result.put("payType", 2);
                result.put("payInfo", stringStringMap);
                return new Result(R.SUCCESS, result);
            case 3:
                //支付宝支付
                String payInfo = AliPayUtil.jsPay(new AliPay("赚客-微任务支付宝支付",id,money,attach,ip));
                result.put("payType",3);
                result.put("payInfo",payInfo);
                return new Result(R.SUCCESS,result);

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
                    vPlatformDealrecord.setId(m.get("out_trade_no"));
                    vPlatformDealrecord.setUserId(attach.getUserId());
                    vPlatformDealrecord.setCreateTime(new Date());
                    vPlatformDealrecord.setType(3);//微信任务预支付
                    vPlatformDealrecord.setTaskId(attach.getTargetId());
                    vPlatformDealrecord.setPayType(2);//微信;
                    vPlatformDealrecord.setStatus(1);//支出
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
                    vPlatformDealrecord.setPId(m.get("out_trade_no"));
                    vPlatformDealrecord.setPayType(2);//微信;;
                    vPlatformDealrecord.setStatus(2);//收入
                    //增加金额
                    userMapper.updateAccount(attach.getUserId(), total);
                    //生成记录
                    vPlatformDealrecordMapper.insert(vPlatformDealrecord);
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
    public void aliPay(Map<String, String> m, HttpServletRequest request, HttpServletResponse resp) {
        if(AliPayUtil.verify(m)){
            if(m.get("trade_status").equals("TRADE_FINISHED") || m.get("trade_status").equals("TRADE_SUCCESS")){
                String json = m.get("attach");
                Attach attach = JsonUtil.fromJson(json, Attach.class);
                //TODO ; 判断金额是否相等
                Integer total = Integer.parseInt(m.get("total"));
                VPlatformDealrecord vPlatformDealrecord = new VPlatformDealrecord();
                vPlatformDealrecord.setId(StrKit.ID());
                vPlatformDealrecord.setUserId(attach.getUserId());
                vPlatformDealrecord.setCreateTime(new Date());
                vPlatformDealrecord.setType(3);//微任务预支付
                vPlatformDealrecord.setPId(m.get("out_trade_no"));
                vPlatformDealrecord.setTaskId(attach.getTargetId());
                vPlatformDealrecord.setPayType(3);//支付宝
                vPlatformDealrecord.setStatus(1);//支出
                vPlatformDealrecord.setSellerId(m.get("seller_id"));
                //扣除金额
                userMapper.updateAccount(attach.getUserId(), -total);
                //生成记录
                vPlatformDealrecordMapper.insert(vPlatformDealrecord);
                //修改状态
                vPlatformTaskMapper.updateTaskStatus(attach.getTargetId(), 1);//待审核

            }else {
                throw new MyTestException(String.format("%s:时间:%s", "支付宝支付异常", new Date()));
            }
        }else{
            throw new MyTestException(String.format("%s:时间:%s", "支付宝支付异常", new Date()));
        }

    }

    @Override
    @Transactional
    public void aliPay2(Map<String, String> m, HttpServletRequest request, HttpServletResponse resp) {
        if(AliPayUtil.verify(m)){
            if(m.get("trade_status").equals("TRADE_FINISHED") || m.get("trade_status").equals("TRADE_SUCCESS")){
                String json = m.get("attach");
                Attach attach = JsonUtil.fromJson(json, Attach.class);
                //TODO ; 判断金额是否相等
                Integer total = Integer.parseInt(m.get("total"));
                VPlatformDealrecord vPlatformDealrecord = new VPlatformDealrecord();
                vPlatformDealrecord.setId(StrKit.ID());
                vPlatformDealrecord.setUserId(attach.getUserId());
                vPlatformDealrecord.setCreateTime(new Date());
                vPlatformDealrecord.setTaskId(attach.getTargetId());
                vPlatformDealrecord.setType(1);//充值
                vPlatformDealrecord.setPayType(3);//支付宝
                vPlatformDealrecord.setPId(m.get("out_trade_no"));
                vPlatformDealrecord.setStatus(1);//支出
                vPlatformDealrecord.setSellerId(m.get("seller_id"));
                //增加金额
                userMapper.updateAccount(attach.getUserId(), total);
                //生成记录
                vPlatformDealrecordMapper.insert(vPlatformDealrecord);

            }else {
                throw new MyTestException(String.format("%s:时间:%s", "支付宝支付异常", new Date()));
            }
        }else{
            throw new MyTestException(String.format("%s:时间:%s", "支付宝支付异常", new Date()));
        }

    }

    @Override
    public Result rechargeAccount(String userId, Integer total, Integer type, String ip, HttpServletResponse response) throws Exception {
        Map result = new HashMap();
        String id = StrKit.ID();
        String attach = JsonUtil.toJson(new Attach(id,userId));//附带信息 targetId,userId
        //充值
        switch (type) {
            case 2:
                //微信支付
                Map<String, String> stringStringMap = PayUtil.jsPay(WxPay.smPay("赚客-微任务微信支付", id, total, attach, ip,"WX_RECHARGE"));
                result.put("payType", 2);
                result.put("payInfo", stringStringMap);
                return new Result(R.SUCCESS, result);
            case 3:
                String payInfo = AliPayUtil.smPay(new AliPay("赚客-微任务支付宝支付",id,total,attach,ip));
                result.put("payType",3);
                result.put("payInfo",payInfo);
                return new Result(R.SUCCESS,result);
            default:
                return new Result(R.NET_ERROR);
        }
    }

    @Override
    public Result findSysList(String id) {
        Map map = vSystemUserMapper.findSysList(id);
        return new Result(R.SUCCESS, map);
    }

    @Override
    public Result sysUserCheckList(Integer currentPage, Integer type) {
        Map map = new HashMap();
        if (type == 3) {//个人
            List list = userMapper.sysUserCheckList(currentPage);
            Integer integer = userMapper.sysUserCheckListNum();
            map.put("checks", list);
            map.put("total", integer);
            return new Result(R.SUCCESS, map);
        } else if (type == 4) {//企业
            List list = userMapper.sysUserCheckList2(currentPage);
            Integer integer = userMapper.sysUserCheckListNum2();
            map.put("checks", list);
            map.put("total", integer);
            return new Result(R.SUCCESS, map);
        }
        return new Result(R.NET_ERROR);
    }

    @Override
    public Result sysUserCheck(String targetId, Integer type, Integer sysCheckModelType, String sUserId) {
        VPlatformUser vPlatformUser = new VPlatformUser();
        vPlatformUser.setId(targetId);
        if(type==1){
            vPlatformUser.setIdentifyType(sysCheckModelType==3?1:2);
        }else if(type==2){
            vPlatformUser.setIdentifyType(5);
        }
        userMapper.updateByPrimaryKeySelective(vPlatformUser);
        return new Result(R.SUCCESS);
    }
}
