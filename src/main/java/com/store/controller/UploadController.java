package com.store.controller;


import com.store.model.ResultData;
import com.store.model.config.ApplicationEntity;
import com.store.model.config.ImageinfoModel;
import com.store.service.ImageinfoService;
import com.store.utils.EncryptUtils;
import com.store.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/m/upload")
@Controller
public class UploadController extends BaseController {

    @Autowired
    private ImageinfoService imageinfoService;

    @RequestMapping("/img")
    @ResponseBody
    public String img(MultipartHttpServletRequest request) throws Exception {
        List<MultipartFile> files = request.getFiles("file");
        String fileType = request.getParameter("fileType");
        List<ImageinfoModel> dataList = new ArrayList<>();
        try {
            dataList = imageinfoService.uploadImage(files, fileType);
        } catch (Exception e) {
            // TODO: handle exception
            return ResultData.toErrorString(e.getMessage());
        }
        return ResultData.toSuccessString(dataList);
    }

    @RequestMapping("/imgs")
    @ResponseBody
    public String imgs(MultipartHttpServletRequest request, @RequestParam(value = "imggrp", required = true) String imggrp) throws Exception {
        List<MultipartFile> files = request.getFiles("file");
        String fileType = request.getParameter("fileType");
        List<ImageinfoModel> dataList = new ArrayList<>();
        try {
            dataList = imageinfoService.uploadImage(files, fileType, imggrp);
        } catch (Exception e) {
            // TODO: handle exception
            return ResultData.toErrorString(e.getMessage());
        }
        return ResultData.toSuccessString(dataList);
    }

}