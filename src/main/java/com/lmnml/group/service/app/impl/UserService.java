package com.lmnml.group.service.app.impl;

import com.lmnml.group.common.model.R;
import com.lmnml.group.common.model.Result;
import com.lmnml.group.dao.app.MsgCodeMapper;
import com.lmnml.group.dao.app.VPlatFormUserMapper;
import com.lmnml.group.entity.app.MsgCode;
import com.lmnml.group.entity.app.VPlatformUser;
import com.lmnml.group.service.app.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if(type==1){
            vPlatformUser.setOpenId(openId);
        }else if(type==2){
            vPlatformUser.setZfbId(openId);
        }
        userMapper.insertSelective(vPlatformUser);
        return new Result(R.SUCCESS);
    }
}
