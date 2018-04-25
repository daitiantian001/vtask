package com.lmnml.group.common.excel2;

import lombok.Data;

import java.util.List;

/**
 * Created by daitian on 2018/4/25.
 */
@Data
public class ExcelNode {
    private String node;
    private String name;
    private Integer row;
    private Integer col;
    private Integer colNum;
    private Integer rowNum;
    private List<ExcelNode> children;
}
