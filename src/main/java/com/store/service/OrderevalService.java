package com.store.service;

import com.store.api.model.Product.EvalVO;
import com.store.dao.OrderevalMapper;
import com.store.model.*;
import com.store.model.config.ApplicationEntity;
import com.store.model.config.ImageinfoModel;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import com.store.utils.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

@Service("orderevalService")
public class OrderevalService {

    @Autowired
    private OrderevalMapper orderevalMapper;

    @Autowired
    private OrderinfoService orderinfoService;

    @Autowired
    private VegetableService vegetableService;

    @Autowired
    private ImageinfoService imageinfoService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ExcelUtil<OrderevalModel> excelUtil;

    @Autowired
    private ApplicationEntity applicationEntity;

    public XSSFWorkbook export(OrderevalModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<OrderevalModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    @Transactional
    public void evalOrder(List<OrderevalModel> evalList, List<String> productidList, String userid, String orderno) {
        OrderinfoModel orderinfoModel = new OrderinfoModel();
        orderinfoModel.setOrderno(orderno);
        orderinfoModel = orderinfoService.getModelById(orderinfoModel);
        if (null == orderinfoModel) {
            throw new NullPointerException("查无此订单");
        }
//        if (!ConstantVariable.ORDER_READY_EVAL.equals(orderinfoModel.getOrderstatus())) {
//            throw new NullPointerException("只对状态为待评价的订单发起操作");
//        }
        orderinfoModel.setOrderstatus(ConstantVariable.ORDER_FINSH);
        orderinfoService.update(orderinfoModel);
        VegetableModel vegetableModel = new VegetableModel();
        ImageinfoModel imageinfoModel = new ImageinfoModel();
        CustomerModel customerModel = customerService.getModelById(userid);
        vegetableModel.setIdList(productidList);
        vegetableModel.noPage();
        List<VegetableModel> productList = vegetableService.getList(vegetableModel);
        List<String> imgIdList = new ArrayList<>();
        for (VegetableModel vmodel : productList) {
            imgIdList.add(vmodel.getVegetableimg());
        }
        imageinfoModel.setImgGrpList(imgIdList);
        List<ImageinfoModel> imgList = imageinfoService.getDefaultImg(imageinfoModel);

        for (OrderevalModel evalModel : evalList) {
            Iterator<VegetableModel> vmodelIt = productList.iterator();
            Iterator<ImageinfoModel> imageIt = imgList.iterator();
            evalModel.setNickname(customerModel.getNickname());
            evalModel.setUserid(customerModel.getUid());
            evalModel.setEvaltime(new Date());
            evalModel.setCreatetime(new Date());
            evalModel.setUsericon(customerModel.getIcon());
            evalModel.setValid(ConstantVariable.VALID_ENABLE);
            while (vmodelIt.hasNext()) {
                VegetableModel vmodel = vmodelIt.next();
                if (vmodel.getVid().equals(evalModel.getVegetable())) {
                    evalModel.setVegetablename(vmodel.getVegetablename());
                    evalModel.setVegetablecontent(vmodel.getContent());
                    evalModel.setImggrp(vmodel.getVegetableimg());
                    vmodelIt.remove();
                }
            }
            while (imageIt.hasNext()) {
                ImageinfoModel imageModel = imageIt.next();
                if (imageModel.getImggrp().equals(evalModel.getImggrp())) {
                    evalModel.setVegetableimg(imageModel.getImghref());
                    imageIt.remove();
                }
            }
            insert(evalModel);
        }
    }

    public ResultData getData(OrderevalModel model) {
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

    public OrderevalModel getModelById(OrderevalModel model) {
        List<OrderevalModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<OrderevalModel> getList(OrderevalModel model) {
        return orderevalMapper.getList(model);
    }

    public List<EvalVO> getListToApp(OrderevalModel qmodel) {
        List<OrderevalModel> modelList = getList(qmodel);
        List<EvalVO> voList = new ArrayList<>();
        for (OrderevalModel model : modelList) {
            EvalVO vo = new EvalVO(model);
            voList.add(vo);
        }
        return voList;
    }

    public int getCount(OrderevalModel model) {
        return orderevalMapper.getCount(model);
    }

    public OrderevalModel insert(OrderevalModel model) {
        orderevalMapper.insert(model);
        return model;
    }

    public OrderevalModel update(OrderevalModel model) {
        if (StringUtils.isNotBlank(model.getVegetable()) && 0 != model.getIstop()) {
            orderevalMapper.clearTop(model);
        }
        orderevalMapper.update(model);
        return model;
    }

    public List<OrderevalModel> batchUpdate(List<OrderevalModel> modelList) {
        for (OrderevalModel model : modelList) {
            orderevalMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(OrderevalModel model) {
        orderevalMapper.disableOrEnable(model);
    }

    public void delete(OrderevalModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(model.getEvalid());
        model.setIdList(idList);
        orderevalMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        OrderevalModel model = new OrderevalModel();
        model.setIdList(idList);
        orderevalMapper.delete(model);
        return ResultData.toSuccessString();
    }

}