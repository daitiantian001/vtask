package com.lmnml.group.common.excel;

import com.google.gson.Gson;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by daitian on 2018/4/21.
 */
public class ExcelUtil {
    /**
     * 设置sheet名称
     *
     * @param workbook
     * @param sheet
     * @return
     */
    private static HSSFWorkbook setSheet(HSSFWorkbook workbook, String sheet) {
        workbook.createSheet(sheet);
        return workbook;
    }

    /**
     * 设置excel标题
     *
     * @param sheet    工作表
     * @param startRow 标题行数
     * @param colNumb  数据列数
     * @param title    标题内容
     */
    private static void setTitle(HSSFSheet sheet, int startRow, int colNumb, String title) {
        sheet.addMergedRegion(new CellRangeAddress(0, startRow - 1, 1, colNumb - 1));
        sheet.createRow(0).createCell(1).setCellValue(title);
    }

    /**
     * 设置单元格
     *
     * @param row   行对象
     * @param index 单元格
     * @param s
     */
    private static void setCell(HSSFRow row, int index, String s) {
        HSSFCell cell = row.createCell(index);
        cell.setCellStyle(getTitlSyle(row.getSheet().getWorkbook()));
        cell.setCellValue(s);
    }

    /**
     * 设置表头
     *
     * @param hrow    行对象
     * @param row     第几行(0开始)
     * @param rowNumb 共几行
     * @param col     第几列(0开始)
     * @param colNumb 共几列
     * @param s
     */
    private static void setCell(HSSFRow hrow, int row, int rowNumb, int col, int colNumb, String s) {
        HSSFCell cell = hrow.createCell(col);
//        cell.setCellStyle(hrow.geth);
        hrow.getSheet().addMergedRegion(new CellRangeAddress(row, row + rowNumb - 1, col, col + colNumb - 1));
        cell.setCellValue(s);
    }

    private static HSSFCellStyle getTitlSyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor((short) 13);// 设置背景色
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //设置边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setTopBorderColor(HSSFColor.BLACK.index);
        return style;
    }

    private static HSSFCellStyle getCellSyle(HSSFWorkbook wb) {
        HSSFCellStyle itemStyle = wb.createCellStyle();
        itemStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        itemStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //设置边框
        itemStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        itemStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        itemStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        itemStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        itemStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        itemStyle.setRightBorderColor(HSSFColor.BLACK.index);
        itemStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        itemStyle.setTopBorderColor(HSSFColor.BLACK.index);
        return itemStyle;
    }

    /**
     * 生成固定格式表头
     *
     * @param sheet
     * @param json
     * @return
     */
    private static List dataList(HSSFSheet sheet, String json) {

        //lambda表达式不支持定义变量.
        List list = new ArrayList();
        Map p = new Gson().fromJson(json, Map.class);
        HSSFRow row = sheet.createRow(0);
        p.forEach((k, v) -> {
            setCell(row, list.size(), v.toString());
            list.add(k);
        });
        return list;
    }

    /**
     * 加载数据
     *
     * @param title    表名
     * @param model    数据格式
     * @param workbook 工作簿对象
     * @param data     数据
     * @return
     */
    public static HSSFWorkbook loadData(String title, String model, List<Map> data, HSSFWorkbook workbook) {
        List<String> list = dataList(workbook.createSheet(title), model);

        HSSFSheet sheet = workbook.getSheet(title);
        HSSFRow row;
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                row = sheet.createRow(sheet.getLastRowNum() + 1);
                for (int j = 0; j < list.size(); j++) {
                    HSSFCell cell = row.createCell(j);
                    cell.setCellValue(data.get(i).get(list.get(j).toString()).toString());
                    cell.setCellStyle(getCellSyle(workbook));
                }
            }
        }
        return workbook;
    }


    /**
     * 导出excel
     *
     * @param response
     * @throws Exception
     */
    public static void export(String title, String model, List<Map> data, HttpServletResponse response) throws Exception {
        HSSFWorkbook workbook = loadData(title, model, data, new HSSFWorkbook());
        if (workbook != null) {
            try {
                String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
                String headStr = "attachment; filename=\"" + fileName + "\"";
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition", headStr);
                OutputStream out = response.getOutputStream();
                workbook.write(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
