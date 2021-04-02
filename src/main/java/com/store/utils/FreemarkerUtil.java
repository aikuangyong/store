package com.store.utils;

import com.store.model.config.ApplicationEntity;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class FreemarkerUtil {

    @Autowired
    private ApplicationEntity config;

    /**
     * 使用模板生成HTML代码
     */
    public void generateViewHtml(Object param,String ftlPath,String ftlName,String outPath,String fileName) throws IOException {
        FileWriter out = null;
        TemplateLoader templateLoader = null;
        try {
            // 通过FreeMarker的Confuguration读取相应的模板文件
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
            // 设置模板路径
//            configuration.setClassForTemplateLoading(FreemarkerUtil.class, ftlPath);
            // 设置默认字体
            configuration.setDefaultEncoding("utf-8");
            templateLoader = new FileTemplateLoader(new File(ftlPath));
            configuration.setTemplateLoader(templateLoader);
            // 获取模板
            Template template = configuration.getTemplate(ftlName);
            //设置输出文件
            File file = new File(outPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(outPath+fileName);
            //设置输出流
            out = new FileWriter(file);
            //模板输出静态文件
            template.process(param, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}