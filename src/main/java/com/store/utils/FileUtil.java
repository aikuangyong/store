package com.store.utils;

import com.store.model.config.ApplicationEntity;
import com.store.model.config.DataModel;
import com.store.model.config.ImageinfoModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {
    @Autowired
    private ApplicationEntity config;

    private Logger LOGGER = Logger.getLogger(FileUtil.class);

    public static void uploadFile(byte[] file, ImageinfoModel model) throws Exception {
        File targetFile = new File(model.getFilepath());
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(model.getFilepath() + File.separator + model.getFilename());
        out.write(file);
        out.flush();
        out.close();
    }

    public String getFloder() {
        String dateSuffix = DateUtil.getDays();
        return config.getUploadpath() + File.separator + dateSuffix + File.separator;
    }

    public void generateJsonFile(Object dataContent, String fileName) {
        String fileStr = DataModel.returnSuccess(dataContent);
        String filePath = null;//config.getJsonFile();
        BufferedOutputStream bos = null;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(filePath + File.separator + fileName);
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(fileStr.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                bos.flush();
                bos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}