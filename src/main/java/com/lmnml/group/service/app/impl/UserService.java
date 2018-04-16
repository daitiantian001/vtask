package com.lmnml.group.service.app.impl;

import com.lmnml.group.dao.app.MsgCodeMapper;
import com.lmnml.group.dao.app.VPlatFormUserMapper;
import com.lmnml.group.entity.app.MsgCode;
import com.lmnml.group.entity.app.VPlatformUser;
import com.lmnml.group.service.app.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public VPlatformUser findUserByMobile(String mobile) {
        return userMapper.findUserByMobile(mobile);
    }

    @Override
    public void insertMsgCode(MsgCode msgCode) {
        Object flag = msgCodeMapper.selectOne(new MsgCode(msgCode.getMobile()));
        if (flag==null) {
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
    public void insertUser(VPlatformUser vPlatformUser) {
        userMapper.insertSelective(vPlatformUser);
    }

    @Override
    public Boolean canSend(String mobile) {
        return msgCodeMapper.canSend(mobile);
    }
}
