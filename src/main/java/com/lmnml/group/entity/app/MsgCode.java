package com.lmnml.group.entity.app;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * Created by daitian on 2018/4/16.
 */
@Data
public class MsgCode {
    @Id
    private String mobile;
    private String mCode;
    private Date uTime;

    public MsgCode() {
    }

    public MsgCode(String mobile) {
        this.mobile = mobile;
    }

    public MsgCode(String mobile, String mCode, Date uTime) {
        this.mobile = mobile;
        this.mCode = mCode;
        this.uTime = uTime;
    }
}
