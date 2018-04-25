package com.lmnml.group.dao.app;

import com.lmnml.group.entity.app.VSystemCategory;
import com.lmnml.group.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by daitian on 2018/4/16.
 */
@Mapper
public interface VSystemCategoryMapper extends MyMapper<VSystemCategory> {
    @Select("SELECT name FROM v_system_category")
    List<String> findName(String name);
}
