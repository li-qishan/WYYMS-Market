package com.buf.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * Created by Administrator on 2018/5/28.
 */
public class ExportUtil {

    public static Workbook generateWorkBook(String title, String[] head, JSONArray array) throws Exception {
        // 创建工作簿
        Workbook workBook = new SXSSFWorkbook();
        // 创建工作表
        Sheet sheet = workBook.createSheet();

        // 通用变量
        int rowNum = 0, cellNum = 0;
        Row nRow = null;
        Cell nCell = null;

        // 设置标题
        nRow = sheet.createRow(rowNum++);
        // 设置行高
        nRow.setHeightInPoints(36f);
        nCell = nRow.createCell(cellNum);
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head.length-1));
        nCell.setCellValue(title);
        // 设置标题样式
        nCell.setCellStyle(bigTitle(workBook));

        // 设置表头
        nRow = sheet.createRow(rowNum++);
        nRow.setHeightInPoints(26.25f);
        for (int i = 0; i < head.length; i++) {
            // 设置列宽
            sheet.setColumnWidth(i, 20 * 256);
            nCell = nRow.createCell(cellNum++);//创建单元格对象
            nCell.setCellValue(head[i].replaceAll("\\s*", ""));
            // 设置单元格的样式
            nCell.setCellStyle(title(workBook));//设置单元格的样式
        }

        // 设置数据
        if (array.size() > 0) {
            for (int i = 0; i < array.size(); i++) {

                JSONObject obj = array.getJSONObject(i);
                String[] dataArray = new String[obj.size()];

                int index = 0;
                for (String s : obj.keySet()) {
                    String str = "";
                    Object temp = obj.get(s);
                    if(temp != null){
                        str = temp.toString();
                    }
                    dataArray[index] = str;
                    index++;
                }

                nRow = sheet.createRow(rowNum++);
                nRow.setHeightInPoints(26.25f);

                cellNum = 0;
                for (int j = 0; j < dataArray.length; j++) {
                    // 设置列宽
                    sheet.setColumnWidth(j, 20 * 256);
                    nCell = nRow.createCell(cellNum++);//创建单元格对象
                    nCell.setCellValue(dataArray[j].replaceAll("\\s*", ""));
                    // 设置单元格的样式
                    nCell.setCellStyle(title(workBook));//设置单元格的样式
                }
            }
        }

        return workBook;
    }

    //大标题样式
    private static CellStyle bigTitle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        style.setFillForegroundColor(HSSFColor.AUTOMATIC.index);
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);                    //字体加粗

        style.setFont(font);

        style.setAlignment(CellStyle.ALIGN_CENTER);                    //横向居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);        //纵向居中

        return style;
    }

    //小标题样式
    private static CellStyle title(Workbook wb) {
        CellStyle style = wb.createCellStyle();
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        style.setFillForegroundColor(HSSFColor.AUTOMATIC.index);
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);

        style.setFont(font);

        style.setAlignment(CellStyle.ALIGN_CENTER);                    //横向居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);        //纵向居中

        style.setBorderTop(CellStyle.BORDER_THIN);                    //上细线
        style.setBorderBottom(CellStyle.BORDER_THIN);                //下细线
        style.setBorderLeft(CellStyle.BORDER_THIN);                    //左细线
        style.setBorderRight(CellStyle.BORDER_THIN);                //右细线

        return style;
    }

    //文字样式
    private static CellStyle text(Workbook wb) {
        CellStyle style = wb.createCellStyle();
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        style.setFillForegroundColor(HSSFColor.AUTOMATIC.index);
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 10);

        style.setFont(font);

        style.setAlignment(CellStyle.ALIGN_LEFT);                    //横向居左
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);        //纵向居中

        style.setBorderTop(CellStyle.BORDER_THIN);                    //上细线
        style.setBorderBottom(CellStyle.BORDER_THIN);                //下细线
        style.setBorderLeft(CellStyle.BORDER_THIN);                    //左细线
        style.setBorderRight(CellStyle.BORDER_THIN);                //右细线

        return style;
    }
}
