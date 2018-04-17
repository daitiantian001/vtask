package com.lmnml.group.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by daitian on 2018/4/17.
 */
@Data
@AllArgsConstructor
public class PageInfo {
    private Integer currentPage;
    private Integer total;
}
