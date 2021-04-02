package com.store.utils;

import com.store.model.BaseModel;
import com.store.model.config.ApplicationEntity;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelUtil<T extends BaseModel> {

    @Autowired
    private ApplicationEntity entity;

    public XSSFWorkbook exportExcel(T t, List<T> dataList) {
        String excelTemp = entity.getExcelTemplate();
        File excelFile = new File(excelTemp);
        Method method = null;
        Map<String, String> title = new HashMap<>();
        try {
            Object obj = t.getClass().newInstance();
            method = t.getClass().getMethod("excelHead");
            title = (Map<String, String>) method.invoke(obj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return getXSSFWorkbook(0, title, dataList, null);
    }

    /**
     * 导出Excel
     *
     * @param sheetIndex sheet名称
     * @param title      标题
     * @param dataList   内容
     * @param wb         HSSFWorkbook对象
     * @return
     */
    public XSSFWorkbook getXSSFWorkbook(int sheetIndex, Map<String, String> title, List<T> dataList, XSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new XSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet("数据");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        XSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        XSSFCellStyle style = wb.createCellStyle();
        //声明列对象
        XSSFCell cell = null;
        int columnIndex = 0;
        for (Map.Entry<String, String> entry : title.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            cell = row.createCell(columnIndex++);
            cell.setCellValue(value);
        }

        for (int i = 0; i < dataList.size(); i++) {
            T t = dataList.get(i);
            Map<String, Object> rowData = t.toMap(t);
            row = sheet.createRow(i + 1);
            columnIndex = 0;
            for (Map.Entry<String, String> entry : title.entrySet()) {
                String key = entry.getKey();
                Object objVal = rowData.get(key);
                if(null == objVal){
                    row.createCell(columnIndex++).setCellValue("");
                    continue;
                }

                if(objVal instanceof Date){
                    Date date = (Date) objVal;
                    String dateStr = StringUtils.valueOf(DateUtil.sdfTime.format(date));
                    dateStr = dateStr.replace("00:00:00","");
                    dateStr = dateStr.replace("08:00:00","");
                    row.createCell(columnIndex++).setCellValue(dateStr);
                }else{
                    row.createCell(columnIndex++).setCellValue(StringUtils.valueOf(objVal));
                }
            }
        }
        return wb;
    }
}