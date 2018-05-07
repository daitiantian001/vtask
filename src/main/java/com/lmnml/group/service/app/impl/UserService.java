package com.lmnml.group.service.app.impl;

import com.github.pagehelper.PageHelper;
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
import tk.mybatis.mapper.entity.Example;

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
        Integer total = userMapper.platDetailTotal(userId, status);
        Map map = new HashMap();
        map.put("details", list);
        map.put("total", total);
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
    public void wxPay(Map<String, String> m, HttpServletRequest request, HttpServletResponse resp) {
        try {
            if (m.get("result_code").equals("SUCCESS")) {
                String sign = m.get("sign");
                String sign1 = PayUtil.getSign(m);
                String json = m.get("attach");
                Attach attach = JsonUtil.fromJson(json, Attach.class);

                switch (attach.getType()) {
                    case Attach.RECHARGE:
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
                            vPlatformDealrecord.setMoney(total);
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
                    case Attach.PAY_TASK:
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
                            vPlatformDealrecord.setMoney(total);
                            //增加金额
                            userMapper.updateAccount(attach.getUserId(), total);
                            //生成记录
                            vPlatformDealrecordMapper.insert(vPlatformDealrecord);
                        } else {
                            throw new MyTestException(String.format("%s:时间:%s", "微信支付异常", new Date()));
                        }
                    default:
                        break;

                }

            } else {
                throw new MyTestException(String.format("%s:时间:%s", "微信支付异常", new Date()));
            }
        } catch (Exception e) {
            throw new MyTestException(String.format("%s:时间:%s", "微信支付异常", new Date()));
        }
    }

//    @Override
//    @Transactional
//    public void wx2Pay(Map<String, String> m, HttpServletRequest request, HttpServletResponse resp) {
//        try {
//            if (m.get("result_code").equals("SUCCESS")) {
//                String sign = m.get("sign");
//                String sign1 = PayUtil.getSign(m);
//                String json = m.get("attach");
//                Attach attach = JsonUtil.fromJson(json, Attach.class);
//                if (sign.equals(sign1)) {
//                    //TODO ; 判断金额是否相等
//                    Integer total = Integer.parseInt(m.get("total"));
//                    VPlatformDealrecord vPlatformDealrecord = new VPlatformDealrecord();
//                    vPlatformDealrecord.setId(StrKit.ID());
//                    vPlatformDealrecord.setUserId(attach.getUserId());
//                    vPlatformDealrecord.setCreateTime(new Date());
//                    vPlatformDealrecord.setType(1);//微信充值
//                    vPlatformDealrecord.setPId(m.get("out_trade_no"));
//                    vPlatformDealrecord.setPayType(2);//微信;;
//                    vPlatformDealrecord.setStatus(2);//收入
//                    //增加金额
//                    userMapper.updateAccount(attach.getUserId(), total);
//                    //生成记录
//                    vPlatformDealrecordMapper.insert(vPlatformDealrecord);
//                } else {
//                    throw new MyTestException(String.format("%s:时间:%s", "微信支付异常", new Date()));
//                }
//            } else {
//                throw new MyTestException(String.format("%s:时间:%s", "微信支付异常", new Date()));
//            }
//        } catch (Exception e) {
//            throw new MyTestException(String.format("%s:时间:%s", "微信支付异常", new Date()));
//        }
//    }

    @Override
    @Transactional
    public void aliPay(Map<String, String> m, HttpServletRequest request, HttpServletResponse resp) {
        if (AliPayUtil.verify(m)) {
            if (m.get("trade_status").equals("TRADE_FINISHED") || m.get("trade_status").equals("TRADE_SUCCESS")) {
                String json = m.get("attach");
                Attach attach = JsonUtil.fromJson(json, Attach.class);
                VPlatformDealrecord vPlatformDealrecord;
                Integer total;
                switch (attach.getType()) {
                    case Attach.RECHARGE:
                        //TODO ; 判断金额是否相等
                        total = Integer.parseInt(m.get("total"))*100;
                        vPlatformDealrecord = new VPlatformDealrecord();
                        vPlatformDealrecord.setId(StrKit.ID());
                        vPlatformDealrecord.setUserId(attach.getUserId());
                        vPlatformDealrecord.setCreateTime(new Date());
                        vPlatformDealrecord.setTaskId(attach.getTargetId());
                        vPlatformDealrecord.setType(1);//充值
                        vPlatformDealrecord.setPayType(3);//支付宝
                        vPlatformDealrecord.setPId(m.get("out_trade_no"));
                        vPlatformDealrecord.setStatus(1);//支出
                        vPlatformDealrecord.setSellerId(m.get("seller_id"));
                        vPlatformDealrecord.setMoney(total);
                        //增加金额
                        userMapper.updateAccount(attach.getUserId(), total);
                        //生成记录
                        vPlatformDealrecordMapper.insert(vPlatformDealrecord);
                    case Attach.PAY_TASK:
                        //TODO ; 判断金额是否相等
                        total = Integer.parseInt(m.get("total"));
                        vPlatformDealrecord = new VPlatformDealrecord();
                        vPlatformDealrecord.setId(StrKit.ID());
                        vPlatformDealrecord.setUserId(attach.getUserId());
                        vPlatformDealrecord.setCreateTime(new Date());
                        vPlatformDealrecord.setType(3);//微任务预支付
                        vPlatformDealrecord.setPId(m.get("out_trade_no"));
                        vPlatformDealrecord.setTaskId(attach.getTargetId());
                        vPlatformDealrecord.setPayType(3);//支付宝
                        vPlatformDealrecord.setStatus(1);//支出
                        vPlatformDealrecord.setSellerId(m.get("seller_id"));
                        vPlatformDealrecord.setMoney(total);
                        //扣除金额
                        userMapper.updateAccount(attach.getUserId(), -total);
                        //生成记录
                        vPlatformDealrecordMapper.insert(vPlatformDealrecord);
                        //修改状态
                        vPlatformTaskMapper.updateTaskStatus(attach.getTargetId(), 1);//待审核
                    default:
                        break;
                }


            } else {
                throw new MyTestException(String.format("%s:时间:%s", "支付宝支付异常", new Date()));
            }
        } else {
            throw new MyTestException(String.format("%s:时间:%s", "支付宝支付异常", new Date()));
        }

    }

//    @Override
//    @Transactional
//    public void aliPay2(Map<String, String> m, HttpServletRequest request, HttpServletResponse resp) {
//        if (AliPayUtil.verify(m)) {
//            if (m.get("trade_status").equals("TRADE_FINISHED") || m.get("trade_status").equals("TRADE_SUCCESS")) {
//                String json = m.get("attach");
//                Attach attach = JsonUtil.fromJson(json, Attach.class);
//                //TODO ; 判断金额是否相等
//                Integer total = Integer.parseInt(m.get("total"));
//                VPlatformDealrecord vPlatformDealrecord = new VPlatformDealrecord();
//                vPlatformDealrecord.setId(StrKit.ID());
//                vPlatformDealrecord.setUserId(attach.getUserId());
//                vPlatformDealrecord.setCreateTime(new Date());
//                vPlatformDealrecord.setTaskId(attach.getTargetId());
//                vPlatformDealrecord.setType(1);//充值
//                vPlatformDealrecord.setPayType(3);//支付宝
//                vPlatformDealrecord.setPId(m.get("out_trade_no"));
//                vPlatformDealrecord.setStatus(1);//支出
//                vPlatformDealrecord.setSellerId(m.get("seller_id"));
//                //增加金额
//                userMapper.updateAccount(attach.getUserId(), total);
//                //生成记录
//                vPlatformDealrecordMapper.insert(vPlatformDealrecord);
//
//            } else {
//                throw new MyTestException(String.format("%s:时间:%s", "支付宝支付异常", new Date()));
//            }
//        } else {
//            throw new MyTestException(String.format("%s:时间:%s", "支付宝支付异常", new Date()));
//        }
//
//    }

    @Override
    @Transactional
    public Result payTask(String userId, String taskId, Integer type, String ip, String productId) throws Exception {
        //根据taskId查询总额
        Integer money = vPlatformTaskMapper.findTotalPriceByTd(taskId);
        Map result = new HashMap();
        String id = StrKit.ID();
        String attach = JsonUtil.toJson(new Attach(taskId, userId, Attach.PAY_TASK));//附带信息 targetId,id
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
                String code = PayUtil.smPay(WxPay.smPay("赚客-微任务支付", id, money, attach, ip, productId));
                result.put("payType", 2);
                result.put("payInfo", code);
                return new Result(R.SUCCESS, result);
            case 3:
                //支付宝支付
                String payInfo = AliPayUtil.smPay(new AliPay("赚客-微任务支付宝支付", id, money, attach, ip, productId));
                result.put("payType", 3);
                result.put("payInfo", payInfo);
                return new Result(R.SUCCESS, result);

            default:
                return new Result(R.NET_ERROR);

        }
    }

    @Override
    @Transactional
    public Result rechargeAccount(String userId, Integer total, Integer type, String ip, HttpServletResponse response) throws Exception {
        Map result = new HashMap();
        String id = StrKit.ID();
//        ip="39.106.148.195";
        String attach = JsonUtil.toJson(new Attach(id, userId, Attach.RECHARGE));//附带信息 targetId,userId
        //充值
        switch (type) {
            case 2:
                //微信支付
                String code = PayUtil.smPay(WxPay.smPay("微信支付", id, total, attach, ip,"10001"));
                result.put("payType", 2);
                result.put("payInfo", code);
                return new Result(R.SUCCESS, result);
            case 3:
                String payInfo = AliPayUtil.smPay(new AliPay("赚客-微任务支付宝支付", id, total, attach, ip,""));
                result.put("payType", 3);
                result.put("payInfo", payInfo);
                return new Result(R.SUCCESS, result);
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
    public Result sysUserCheckList(Integer currentPage, int pageSize, VPlatformUser vPlatformUser) {
        Example example = new Example(VPlatformUser.class);
        String name = vPlatformUser.getName();
        vPlatformUser.setName(null);
        example.createCriteria().andEqualTo(vPlatformUser).andLike("name",StrKit.isBlank(name)?null:'%'+name+'%');
        example.setOrderByClause("create_time desc");
        PageHelper.startPage(currentPage,pageSize,true);
        List list = userMapper.selectByExample(example);
        return new Result(R.SUCCESS, list);
    }

    @Override
    public Result sysUserCheck(VPlatformUser vPlatformUser) {
        userMapper.updateByPrimaryKeySelective(vPlatformUser);
        return new Result(R.SUCCESS);
    }

    @Override
    public Result sysUserOff(VPlatformUser vPlatformUser) {
        userMapper.updateByPrimaryKeySelective(vPlatformUser);
        return new Result(R.SUCCESS);
    }

    @Override
    public Result sysUserList(Integer currentPage, int i, VPlatformUser vPlatformUser) {
        Example example = new Example(VPlatformUser.class);
        String name = vPlatformUser.getName();
        vPlatformUser.setName(null);
        example.createCriteria().andEqualTo(vPlatformUser).andLike("name",StrKit.isBlank(name)?null:'%'+name+'%');
        example.setOrderByClause("account desc");
        PageHelper.startPage(currentPage,i,true);
        List list = userMapper.selectByExample(example);
        return new Result(R.SUCCESS, list);
    }
}
