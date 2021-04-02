package com.store.model.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * YAML 语法实体类
 * 切记点：
 * 一、冒号后面加空格，即 key:(空格)value
 * 二、每行参数左边空格数量决定了该参数的层级，不可乱输入。
 */
@Component
@ConfigurationProperties
@Data
public class ApplicationEntity {

    private Map<String,String> pos;

    private Map<String,String> wx;

    private Map<String, String> datasource;

    private Map<String, String> sms;

    private Map<String, String> push;

    private Map<String, String> api;

    private Map<String,String> pay;

    private String name;

    private Map<String, Object> mybatis;

    private RedisEntity redis;

    private String uploadpath;

    private String onDomain;

    private String templatePath;

    private String outPath;

    private String excelTemplate;

    private String uploadSrc;

    private String apiFloder;

    private String ueConfig;

    public String getUeConfig() {
        return ueConfig;
    }

    public void setUeConfig(String ueConfig) {
        this.ueConfig = ueConfig;
    }

    public Map<String, String> getApi() {
        return api;
    }

    public void setApi(Map<String, String> api) {
        this.api = api;
    }

    public Map<String, String> getSms() {
        return sms;
    }

    public void setSms(Map<String, String> sms) {
        this.sms = sms;
    }

    public String getApiFloder() {
        return apiFloder;
    }

    public void setApiFloder(String apiFloder) {
        this.apiFloder = apiFloder;
    }

    public String getExcelTemplate() {
        return excelTemplate;
    }

    public void setExcelTemplate(String excelTemplate) {
        this.excelTemplate = excelTemplate;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public String getOnDomain() {
        return onDomain;
    }

    public void setOnDomain(String onDomain) {
        this.onDomain = onDomain;
    }

    public String getUploadpath() {
        return uploadpath;
    }

    public void setUploadpath(String uploadpath) {
        this.uploadpath = uploadpath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getMybatis() {
        return mybatis;
    }

    public void setMybatis(Map<String, Object> mybatis) {
        this.mybatis = mybatis;
    }

    public RedisEntity getRedis() {
        return redis;
    }

    public void setRedis(RedisEntity redis) {
        this.redis = redis;
    }

    public Map<String, String> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, String> datasource) {
        this.datasource = datasource;
    }

    public String getUploadSrc() {
        return uploadSrc;
    }

    public void setUploadSrc(String uploadSrc) {
        this.uploadSrc = uploadSrc;
    }
}