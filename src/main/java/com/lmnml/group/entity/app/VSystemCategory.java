package com.lmnml.group.entity.app;

import lombok.Data;

import javax.persistence.Id;

/**
 * Created by daitian on 2018/4/16.
 */
@Data
public class VSystemCategory {
    @Id
    private String id;
    private String name;
    private String icon;
    private Integer status;
}
