package com.store.service;

import org.apache.commons.lang.StringUtils;
import com.store.dao.ImageinfoMapper;
import com.store.model.config.ApplicationEntity;
import com.store.model.config.ImageinfoModel;
import com.store.model.ResultData;
import com.store.utils.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Service("imageinfoService")
public class ImageinfoService {

    @Autowired
    private ImageinfoMapper imageinfoMapper;

    @Autowired
    private ExcelUtil<ImageinfoModel> excelUtil;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private ApplicationEntity applicationEntity;


    public List<ImageinfoModel> getDefaultImg(ImageinfoModel model) {
        List<ImageinfoModel> imgList = imageinfoMapper.getDefaultImg(model);
        return imgList;
    }

    public List<ImageinfoModel> uploadImage(List<MultipartFile> files, String type, String imggrp) throws Exception {
        List<ImageinfoModel> imageList = new ArrayList<>();
        String days = DateUtil.getDays();
        for (MultipartFile file : files) {
            String imgExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
            String imgName = "";
            if ("file".equals(type)) {
                imgName = DateUtil.getDays() + "_" + CodeUtil.getRandomNumber(6) + "_" + file.getOriginalFilename();
            } else {
                imgName = DateUtil.getDays() + EncryptUtils.md5(UUID.randomUUID().toString()) + "." + imgExt;
            }

            ImageinfoModel imgModel = new ImageinfoModel();
            imgModel.setImgid(EncryptUtils.md5(UUID.randomUUID().toString()));
            imgModel.setFilename(imgName);
            imgModel.setFilepath(applicationEntity.getUploadpath() + days + File.separator);
            imgModel.setSrc(days + "/" + imgName);
            imgModel.setFiletype(type);
            if (StringUtils.isEmpty(imggrp)) {
                imgModel.setImggrp(imgModel.getImgid());
            } else {
                imgModel.setImggrp(imggrp);
            }
            fileUtil.uploadFile(file.getBytes(), imgModel);
            imageList.add(imgModel);
            insert(imgModel);
        }
        return imageList;
    }

    public List<ImageinfoModel> uploadImage(List<MultipartFile> files, String type) throws Exception {
        List<ImageinfoModel> imageList = new ArrayList<>();
        for (MultipartFile file : files) {
            imageList.addAll(uploadImage(files, type, null));
        }
        return imageList;
    }

    public ImageinfoModel uploadImage(MultipartFile partFile, String type) throws Exception {
        List<ImageinfoModel> imageList = new ArrayList<>();
        List<MultipartFile> files = new ArrayList<>();
        files.add(partFile);
        for (MultipartFile file : files) {
            imageList.addAll(uploadImage(files, type, null));
        }
        return imageList.get(0);
    }

    public XSSFWorkbook export(ImageinfoModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<ImageinfoModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(ImageinfoModel model) {
        ResultData returnData = new ResultData();
        try {
            returnData.setDataList(this.getList(model));
            int dataCount = this.getCount(model);
            returnData.setPageCount(dataCount, model.getPageSize());
            returnData.setDataCount(dataCount);
            returnData.setState(ConstantVariable.SUCCESS);
            returnData.setPageNumber(model.getPageNumber());
        } catch (Exception e) {
            returnData.setState(ConstantVariable.ERROR);
            returnData.setMsg(e.getMessage());
        }
        return returnData;
    }

    public ImageinfoModel getModelById(ImageinfoModel model) {
        List<ImageinfoModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<ImageinfoModel> getList(ImageinfoModel model) {
        return imageinfoMapper.getList(model);
    }

    public int getCount(ImageinfoModel model) {
        return imageinfoMapper.getCount(model);
    }

    public ImageinfoModel insert(ImageinfoModel model) {
        imageinfoMapper.insert(model);
        return model;
    }

    public ImageinfoModel update(ImageinfoModel model) {
        imageinfoMapper.update(model);
        return model;
    }

    public List<ImageinfoModel> batchUpdate(List<ImageinfoModel> modelList) {
        for (ImageinfoModel model : modelList) {
            imageinfoMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(ImageinfoModel model) {
        imageinfoMapper.disableOrEnable(model);
    }

    public void delete(ImageinfoModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(model.getImgid());
        model.setIdList(idList);
        imageinfoMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        ImageinfoModel model = new ImageinfoModel();
        model.setIdList(idList);
        imageinfoMapper.delete(model);
        return ResultData.toSuccessString();
    }

}