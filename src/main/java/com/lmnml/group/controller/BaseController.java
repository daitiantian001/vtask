package com.lmnml.group.controller;

import com.lmnml.group.entity.response.R;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by daitian on 2018/4/15.
 */
public class BaseController {
    protected static final Integer SUCCESS = 1;
    protected static final Integer FAILTURE = 0;
    protected static final Integer UNLOGIN = -1;

    @Autowired
    protected R R;
}
