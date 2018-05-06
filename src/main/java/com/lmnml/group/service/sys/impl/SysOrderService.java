package com.lmnml.group.service.sys.impl;

import com.lmnml.group.dao.app.VPlatformDealrecordMapper;
import com.lmnml.group.service.sys.ISysOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by daitian on 2018/5/6.
 */
@Service
public class SysOrderService implements ISysOrderService{

    @Autowired
    VPlatformDealrecordMapper vPlatformDealrecordMapper;
}
