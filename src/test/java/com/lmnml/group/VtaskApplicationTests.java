package com.lmnml.group;

import com.google.gson.Gson;
import com.lmnml.group.common.excel.ExcelJSON;
import com.lmnml.group.common.excel2.ExcelNode;
import com.lmnml.group.util.JsonUtil;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VtaskApplicationTests {

    @Test
    public void name() throws Exception {
    }

    @Test
    public void contextLoads() {
        String s="";
        System.out.println(getCotent(s));
    }

    public static String getCotent(String source){
        StringBuffer target=new StringBuffer();
        Pattern pattern = Pattern.compile(">([^<]*)<");
        Matcher macher = pattern.matcher(source);
        while (macher.find()){
            target.append(macher.group());
        }
        return target.toString().replaceAll("<>","").replaceAll("\n","");
    }

    private void a() {
        Map map = new HashMap();
        List<ExcelNode> excelNodes = JsonUtil.toExcel(ExcelJSON.TEST020);
        run(excelNodes, 0);
        excelNodes.forEach(k -> {
            System.out.println(k.toString());
        });
    }

    public static void run(List<ExcelNode> excelNodes, int n) {
        for (int i = 0; i < excelNodes.size(); i++) {
            ExcelNode excelNode = excelNodes.get(i);
            excelNode.setRow(n);
            excelNode.setCol(i);
            int num = excelNode.getChildren().size();
            if (num > 0) {
                run(excelNode.getChildren(), n + 1);
            }
        }
    }

}
