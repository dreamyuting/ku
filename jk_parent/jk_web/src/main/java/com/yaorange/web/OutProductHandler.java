package com.yaorange.web;

import com.yaorange.entity.Contract;
import com.yaorange.entity.ContractProduct;
import com.yaorange.service.OutProductService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/outProduct")
public class OutProductHandler {
    @Resource(name = "outProductService")
    private OutProductService outProductService;
    @GetMapping("/print")
    public void print(@RequestParam String outDate, HttpServletResponse response) throws IOException {
        //自定义打印出货表
        //创建工作薄对象
        Workbook workbook = new HSSFWorkbook();
        //创建工作表
        Sheet sheet = workbook.createSheet("出货表");
        //通过表设置列宽:参数1列索引，参数2宽度大小：值为256的倍数
        sheet.setColumnWidth(0,18*256);
        sheet.setColumnWidth(1,12*256);
        sheet.setColumnWidth(2,18*256);
        sheet.setColumnWidth(3,12*256);
        sheet.setColumnWidth(4,12*256);
        sheet.setColumnWidth(5,12*256);
        sheet.setColumnWidth(6,12*256);
        sheet.setColumnWidth(7,8*256);
        //首先创建标题行
        Row titleRow = sheet.createRow(0);
        //创建单元格，设置标题文本
        Cell titleCell = titleRow.createCell(0);
        String title = outDate+"出货表";
        titleCell.setCellValue(title);
        //给单元格设置样式
        titleCell.setCellStyle(titleCellStyle(workbook));
        //标题需要合并单元格,通过表对象合并单元格
//        CellRangeAddress(int firstRow, int lastRow, int firstCol, int lastCol)
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,7));//将第一行所有列合并

        //创建表头
        //创建行
        Row headerRow = sheet.createRow(1);
        String[] headerTxt = {"客户","订单号","货号","数量","工厂","工厂交期","船期","贸易条款"};
        for (int i = 0; i < headerTxt.length; i++) {
            //创建单元格
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headerTxt[i]);
            cell.setCellStyle(headerCellStyle(workbook));
        }
        int start = 2;//行索引
        List<ContractProduct> products = outProductService.getOutProducts(outDate);
        for (ContractProduct product : products) {
            Row row = sheet.createRow(start++);
            Contract contract = product.getContract();
            String customName = contract.getCustomName();//客户
            String contractNo = contract.getContractNo();//订单号
            String productNo = product.getProductNo();//货号
            String number = product.getCnumber().toString();//数量
            String factoryName = product.getFactoryName();//工厂名称
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date deliveryPeriod = contract.getDeliveryPeriod();
            String deliveryPeriodString = simpleDateFormat.format(deliveryPeriod);
            Date shipTime = contract.getShipTime();
            String shipTimeString = simpleDateFormat.format(shipTime);//船期字符串
            String tradeTerms = contract.getTradeTerms();//贸易条款
            String[] datas={ customName,contractNo,productNo,number,factoryName,deliveryPeriodString,shipTimeString,tradeTerms};
            for (int i=0; i<datas.length;i++){
                //创建单元格
                Cell cell = row.createCell(i);
                cell.setCellValue(datas[i]);
                cell.setCellStyle(dataCellStyle(workbook));
            }


        }

        //设置响应头
        //解决中文乱码：new String(("").getBytes("gbk"), "iso8859-1")
        response.setHeader("Content-Disposition", "attachment;filename="+new String((title).getBytes("gbk"), "iso8859-1")+".xls");
        //通过WorkBook的write方法响应输出
        workbook.write(response.getOutputStream());
        //关闭
        workbook.close();
    }

    //标题行样式
    public CellStyle titleCellStyle(Workbook workbook){
        CellStyle cellStyle = workbook.createCellStyle();
        //设置字体
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 20);
        font.setBold(true);
        //将字体对象添加到样式中
        cellStyle.setFont(font);
        //设置文本对齐方式
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        //设置边框
        cellStyle.setBorderTop(BorderStyle.THIN);//细边框
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        return cellStyle;
    }
    //表头行样式
    public CellStyle headerCellStyle(Workbook workbook){
        CellStyle cellStyle = workbook.createCellStyle();
        //设置字体
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 10);
        font.setBold(true);
        //将字体对象添加到样式中
        cellStyle.setFont(font);
        //设置文本对齐方式
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        //设置边框
        cellStyle.setBorderTop(BorderStyle.THIN);//细边框
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        return cellStyle;
    }
    //数据行样式
    public CellStyle dataCellStyle(Workbook workbook){
        CellStyle cellStyle = workbook.createCellStyle();
        //设置字体
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 10);
        //将字体对象添加到样式中
        cellStyle.setFont(font);
        //设置文本对齐方式
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        //设置边框
        cellStyle.setBorderTop(BorderStyle.THIN);//细边框
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        return cellStyle;
    }


}
