package com.store.service;

import com.store.api.model.order.OrderInfoVO;
import com.store.dao.AfterserviceMapper;
import com.store.dao.ImageinfoMapper;
import com.store.model.*;
import com.store.model.config.ImageinfoModel;
import com.store.utils.CodeUtil;
import com.store.utils.ConstantVariable;
import com.store.utils.EncryptUtils;
import com.store.utils.ExcelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service("afterserviceService")
public class AfterserviceService {

    @Autowired
    private AfterserviceMapper afterserviceMapper;

    @Autowired
    private OrderinfoService orderinfoService;

    @Autowired
    private ImageinfoService imageinfoService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ImageinfoMapper imageinfoMapper;

    @Autowired
    private ExcelUtil<AfterserviceModel> excelUtil;

    public XSSFWorkbook export(AfterserviceModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<AfterserviceModel> dataList = getList(model);
        for (AfterserviceModel afterModel : dataList) {
            if ("1".equals(afterModel.getStatus())) {
                afterModel.setStatus("待审核");
            } else if ("2".equals(afterModel.getStatus())) {
                afterModel.setStatus("审核通过");
            } else {
                afterModel.setStatus("审核未通过");
            }
            if ("1".equals(afterModel.getBackpaystatus())) {
                afterModel.setBackpaystatus("未退款");
            } else {
                afterModel.setBackpaystatus("已退款");
            }
        }
        return excelUtil.exportExcel(model, dataList);
    }

    public AfterserviceModel getDetailData(String orderno) {
        AfterserviceModel afterserviceModel = new AfterserviceModel();
        afterserviceModel.setOrderno(orderno);
        OrderinfoModel orderModel = new OrderinfoModel();
        orderModel.setOrderno(orderno);
        OrderInfoVO orderVo = orderinfoService.getOrderDetail(orderModel);
        afterserviceModel = getModelById(afterserviceModel);
        afterserviceModel.setOrderinfoVo(orderVo);
        return afterserviceModel;
    }

    public void add(AfterserviceModel model, List<MultipartFile> files) throws Exception {
        String fileType = "saleservice";
        String imggrp = "";
        if (null != files && files.size() != 0) {
            imggrp = EncryptUtils.md5(System.currentTimeMillis() + CodeUtil.getRandomNumber(6));
            imageinfoService.uploadImage(files, fileType, imggrp);
        }

        model.setImggrp(imggrp);
        CustomerModel userModel = customerService.getModelById(model.getUserid());
        model.setNickname(userModel.getNickname());
        model.setPhoneno(userModel.getPhoneno());
        insert(model);
    }

    public ResultData getData(AfterserviceModel model) {
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

    public AfterserviceModel getModelById(AfterserviceModel model) {
        List<AfterserviceModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<AfterserviceModel> getList(AfterserviceModel model) {
        List<AfterserviceModel> modelList = afterserviceMapper.getList(model);
        setImageToVegetable(modelList);
        return modelList;
    }

    private void setImageToVegetable(List<AfterserviceModel> dataList) {
        ImageinfoModel imageinfoModel = new ImageinfoModel();
        imageinfoModel.setImgGrpList(new ArrayList<>());
        for (AfterserviceModel afterModel : dataList) {
            imageinfoModel.getImgGrpList().add(afterModel.getImggrp());
        }
        imageinfoModel.setPageNow(0);
        imageinfoModel.setPageSize(Integer.MAX_VALUE);
        List<ImageinfoModel> imgList = imageinfoMapper.getList(imageinfoModel);
        for (AfterserviceModel afterModel : dataList) {
            String imageId = afterModel.getImggrp();
            List<ImageinfoModel> imageList = new ArrayList<>();
            if (null == afterModel.getImageList()) {
                afterModel.setImageList(imageList);
            }
            Iterator<ImageinfoModel> it = imgList.iterator();
            while (it.hasNext()) {
                ImageinfoModel image = it.next();
                if (imageId.equals(image.getImggrp())) {
                    imageList.add(image);
                    it.remove();
                }
            }
        }
    }

    public List<AfterserviceModel> getAppList(AfterserviceModel model) {
        return afterserviceMapper.getAppList(model);
    }

    public int getCount(AfterserviceModel model) {
        return afterserviceMapper.getCount(model);
    }

    public AfterserviceModel insert(AfterserviceModel model) {
        model.setApplytime(new Date());
        afterserviceMapper.insert(model);
        return model;
    }

    public AfterserviceModel update(AfterserviceModel model) {
        afterserviceMapper.update(model);
        return model;
    }

    public List<AfterserviceModel> batchUpdate(List<AfterserviceModel> modelList) {
        for (AfterserviceModel model : modelList) {
            afterserviceMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(AfterserviceModel model) {
        afterserviceMapper.disableOrEnable(model);
    }

    public void delete(AfterserviceModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(model.getServiceid());
        model.setIdList(idList);
        afterserviceMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        AfterserviceModel model = new AfterserviceModel();
        model.setIdList(idList);
        afterserviceMapper.delete(model);
        return ResultData.toSuccessString();
    }

}