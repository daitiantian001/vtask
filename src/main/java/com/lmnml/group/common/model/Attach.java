package com.lmnml.group.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by daitian on 2018/4/21.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attach {
    public static final String RECHARGE="1";
    public static final String PAY_TASK="2";
    private String targetId;
    private String userId;
    private String type;
}
