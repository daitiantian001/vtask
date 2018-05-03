package com.lmnml.group.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by daitian on 2018/4/26.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliPay {
    private String body;
    private String id;
    private Integer total;
    private String attach;
    private String ip;
    private String storeId;
}
