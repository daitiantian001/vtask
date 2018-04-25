package com.lmnml.group.entity.web;

import lombok.Data;

import javax.persistence.Id;

/**
 * Created by daitian on 2018/4/22.
 */
@Data
public class VSystemRole {
    @Id
    private String id;
    private String name;
    private Integer status;
}
