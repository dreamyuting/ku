package com.yaorange.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class POITest {
    public static void main(String[] args) throws IOException {
        Workbook wb = new HSSFWorkbook();//创建工作簿
        Sheet sheet = wb.createSheet("工作表");//新增工作表
        for(int i=0;i<4;i++){
            Row row = sheet.createRow(i);
            for (int j=0;j<4;j++){
                Cell cell = row.createCell(j);
              cell.setCellValue("第"+(i+1)+"行,第"+(j+1)+"列");//给单元格设置内容
                CellStyle cellStyle = wb.createCellStyle();//通过工作薄创建单元格样式对象
                Font font = wb.createFont();    //通过工作薄创建字体对象
                font.setBold(true);//加粗
                font.setFontName("华文行楷");//字体
                font.setFontHeightInPoints((short) 20);//字体大小
                font.setColor(Font.COLOR_RED);//字体颜色
                cellStyle.setFont(font);//将设置好的字体对象设置给单元格样式对象
                cell.setCellStyle(cellStyle); //将准备好的单元格样式对象设置给单元格对象

            }
        }
        ((HSSFWorkbook) wb).write(new FileOutputStream("D:/test.xls"));
        wb.close();
    }
}
