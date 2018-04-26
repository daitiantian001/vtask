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
    private String targetId;
    private String userId;
}
