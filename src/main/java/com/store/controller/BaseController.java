package com.store.controller;

import com.store.api.pos.ValidatePosException;
import com.store.api.pos.model.Param.PosBaseModel;
import com.store.model.BaseModel;
import com.store.model.config.ApplicationEntity;
import com.store.utils.EncryptUtils;
import com.store.utils.StringUtils;
import javafx.application.Application;
import org.apache.poi.poifs.filesystem.EntryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseController {

    @Autowired
    private ApplicationEntity entity;

    public void validateSign(PosBaseModel baseModel) throws ValidatePosException {
        if (StringUtils.isBlank(baseModel.getAppid()) ||
                StringUtils.isBlank(baseModel.getTs()) ||
                StringUtils.isBlank(baseModel.getV()) ||
                StringUtils.isBlank(baseModel.getSign())) {
            throw new ValidatePosException("验签参数不能为空");
        }
        baseModel.setSecret(entity.getPos().get("secret"));
        //EncryptUtils.md5(param.get("appid") + param.get("secret") + ts + param.get("v"))
        String sign = EncryptUtils.md5(baseModel.getAppid() + baseModel.getSecret() + baseModel.getTs() + baseModel.getV());
        if (!sign.equals(baseModel.getSign())) {
            throw new ValidatePosException("验签失败");
        }
    }

    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}