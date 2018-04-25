package com.lmnml.group;

import com.lmnml.group.common.excel.ExcelJSON;
import com.lmnml.group.common.excel2.ExcelNode;
import com.lmnml.group.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VtaskApplicationTests {

	@Test
	public void contextLoads() {
		Map map = new HashMap();
		List<ExcelNode> excelNodes = JsonUtil.toExcel(ExcelJSON.TEST020);
		run(excelNodes,map);
		excelNodes.forEach(k->{
			System.out.println(k.toString());
		});
	}
	public static Map run(List<ExcelNode> excelNodes,Map<String,Integer> map){
		if(map.get("num")==null){
			map.put("num",0);
		}
		if(map.get("total")==null){
			map.put("total",0);
		}
		for (int i = 0; i <excelNodes.size(); i++) {
			ExcelNode excelNode = excelNodes.get(i);
			excelNode.setRow(map.get("num"));
			excelNode.setCol(i);
			int num =excelNode.getChildren().size();
//			excelNode.setRowNum(num);
//			excelNode.setColNum(clo);
			map.put("inNum",map.get("num")+1);//遍历次数
			map.put("total",map.get("num")+num);
			if(num!=0){
				run(excelNode.getChildren(),map);
			}
		}
		return map;
	}

}
