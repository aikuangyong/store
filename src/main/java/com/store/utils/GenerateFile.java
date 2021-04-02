package com.store.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import com.store.model.config.ColumnModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "spring.generate")
public class GenerateFile {

    @Autowired
    private Configuration configuration;

    @Autowired
    private DatabaseUtil dbUtil;

    private String outpath;

    public void generateApi(){

    }

    public void setOutpath(String outpath) {
        this.outpath = outpath;
    }

    public void generate(String tableName) throws NoSuchFieldException {
        List<ColumnModel> dataList = dbUtil.getColumnInfo(tableName);
        String primaryKey = "";
        for (ColumnModel columnModel : dataList) {
            if (!StringUtils.isEmpty(columnModel.getIsKey())) {
                primaryKey = columnModel.getColumnName();
                break;
            }
        }
        if (StringUtils.isEmpty(primaryKey)) {
            throw new NoSuchFieldException("no primaryKey exception");
        }
        System.out.println(JSON.toJSONString(dataList));
        generateDao(tableName, dataList, primaryKey);
    }

    private void generateDao(String tableName, List<ColumnModel> dataList, String primaryKey) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", tableName);
        map.put("scheml", "store");
        map.put("modelList", dataList);
        map.put("primaryKey", primaryKey);

        String[] ftlNameArr = new String[]{"mapper", "model", "dao", "service", "controller", "js", "api", "html","control"};
        String[] suffixNameArr = new String[]{"xml", "java", "java", "java", "java", "js", "java", "html","js"};
        for (int i = 0; i < ftlNameArr.length; i++) {
            String fileName = tableName.toLowerCase().substring(0, 1).toUpperCase() + tableName.toLowerCase().substring(1, tableName.length()).toLowerCase();
            String outFloderPath = outpath + File.separator + tableName + File.separator;
            switch (ftlNameArr[i]) {
                case "mapper":
                    break;
                case "model":
                    outFloderPath = outFloderPath + "model" + File.separator;
                    break;
                case "dao":
                    outFloderPath = outFloderPath + "dao" + File.separator;
                    break;
                case "service":
                    outFloderPath = outFloderPath + "service" + File.separator;
                    break;
                case "controller":
                    outFloderPath = outFloderPath + "controller" + File.separator;
                    break;
                case "js":
                    break;
                case "api":
                    outFloderPath = outFloderPath + "api" + File.separator;
                    break;
                case "html":
                    break;
                default:
                    break;
            }
            File floder = new File(outFloderPath);
            if (!floder.exists()) {
                floder.mkdirs();
            }
            outFile(outFloderPath, map, fileName, ftlNameArr[i], suffixNameArr[i]);
        }
    }

    public void outFile(String outFloderPath, Map<String, Object> map, String fileName, String ftlName, String suffixName) {
        Writer out = null;
        String fileNamePrefix = ftlName.toLowerCase().substring(0, 1).toUpperCase() + ftlName.toLowerCase().substring(1, ftlName.length()).toLowerCase();
        if ("Dao".equalsIgnoreCase(fileNamePrefix)) {
            fileNamePrefix = "Mapper";
        }
        if ("html".equalsIgnoreCase(fileNamePrefix)) {
            fileNamePrefix = "";
            fileName = fileName.toLowerCase();
        }
        fileName = fileName + fileNamePrefix;
        String path = outFloderPath + fileName + "." + suffixName;
        File htmlFile = new File(path);
        try {
            Template template = configuration.getTemplate("code/" + ftlName + ".ftl");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
            template.process(map, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}