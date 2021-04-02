package com.store.service;

import com.store.dao.OrderinfoMapper;
import com.store.dao.RegularorderdetailMapper;
import com.store.model.*;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Service("regularorderdetailService")
public class RegularorderdetailService {

    @Autowired
    private RegularorderdetailMapper regularorderdetailMapper;

    @Autowired
    private RegularorderService regularorderService;

    @Autowired
    private RegularsuborderService regularsuborderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ExcelUtil<RegularorderdetailModel> excelUtil;

    @Autowired
    private OrderinfoMapper orderinfoMapper;


    @Autowired
    private RevieworderService revieworderService;

    @Autowired
    private ReviewregularService reviewregularService;

    public XSSFWorkbook export(RegularorderdetailModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<RegularorderdetailModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public RegularorderdetailModel getDetailData(RegularorderdetailModel model) {
        return regularorderdetailMapper.getDetailData(model);
    }

    public ResultData getData(RegularorderdetailModel model) {
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

    public RegularorderdetailModel getModelById(RegularorderdetailModel model) {
        List<RegularorderdetailModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<RegularorderdetailModel> getList(RegularorderdetailModel model) {
        return regularorderdetailMapper.getList(model);
    }

    public int getCount(RegularorderdetailModel model) {
        return regularorderdetailMapper.getCount(model);
    }

    public RegularorderdetailModel insert(RegularorderdetailModel model) {
        regularorderdetailMapper.insert(model);
        return model;
    }

    public RegularorderdetailModel update(RegularorderdetailModel model) {
        regularorderdetailMapper.update(model);
        return model;
    }

    public RegularorderdetailModel updateOrderStatus(RegularorderdetailModel model) {
        regularorderdetailMapper.updateOrderStatus(model);
        return model;
    }

    @Transactional
    public ReviewregularModel addRevierRegular(RegularorderdetailModel model, String reviewer, String store) {
        String orderid = model.getOrderid();
        String orderno = model.getOrderno();
        ReviewregularModel reviewModel = new ReviewregularModel();
        reviewModel.setOrderno(orderno);
        reviewModel = reviewregularService.getModelById(reviewModel);
        if (reviewModel == null) {
            reviewModel = new ReviewregularModel();
            reviewModel.setOrderno(orderno);
            RegularorderModel regularorderModel = new RegularorderModel();
            regularorderModel.setOrderid(orderid);
            regularorderModel = regularorderService.getModelById(regularorderModel);
            RegularorderdetailModel detailModel = new RegularorderdetailModel();
            detailModel = getModelById(model);
//        RegularsuborderModel regularsuborderModel = new RegularsuborderModel();
//        regularsuborderModel.setRegularno(orderno);
//        List<RegularsuborderModel> subList = regularsuborderService.getList(regularsuborderModel);
//        regularsuborderModel = subList.get(0);
            String userid = regularorderModel.getUserid();
            CustomerModel userModel = customerService.getModelById(userid);
            reviewModel.setCreatetime(new Date());
            reviewModel.setNickname(userModel.getNickname());
            reviewModel.setPhoneno(userModel.getPhoneno());
            reviewModel.setReviewer(reviewer);
            if(detailModel.getSaleprice() == null){
                reviewModel.setPrice(0d);
            }else{
                reviewModel.setPrice(Double.parseDouble(detailModel.getSaleprice()));
            }
            reviewModel.setSpecification(detailModel.getUnit() + "g/" + detailModel.getSendcount() + "次");
            reviewModel.setSurplus(detailModel.getSurplus());
            reviewModel.setStore(store);
            reviewModel.setStatus(ConstantVariable.CLEAR_REGULAR_ORDER_NO_VERIFY);
            return reviewregularService.insert(reviewModel);
        }
        return reviewModel;
    }

    @Transactional
    public void clearRegularOrder(String reviewid,String orderno,String status) {
        if(ConstantVariable.CLEAR_REGULAR_ORDER_NO_VERIFY_PASS.equals(status)){
            RegularorderModel regularorderModel = new RegularorderModel();
            regularorderModel.setOrderno(orderno);
            regularorderModel = regularorderService.getModelById(regularorderModel);
            RegularorderdetailModel detailModel = new RegularorderdetailModel();
            detailModel.setOrderid(regularorderModel.getOrderid());
            detailModel.setOrderno(regularorderModel.getOrderno());
            regularorderdetailMapper.clearRegularSubOrder(detailModel);
            regularorderdetailMapper.clearRegularOrderDetail(detailModel);
        }
        ReviewregularModel reviewModel = new ReviewregularModel();
        reviewModel.setRrid(reviewid);
        reviewModel.setStatus(status);
        reviewregularService.update(reviewModel);
        orderinfoMapper.insertDeleteOrder(new DeleteRecord(reviewModel.toString(), "reviewregular_"+status));
        reviewregularService.deleteById(reviewModel);
    }

    public List<RegularorderdetailModel> batchUpdate(List<RegularorderdetailModel> modelList) {
        for (RegularorderdetailModel model : modelList) {
            regularorderdetailMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(RegularorderdetailModel model) {
        regularorderdetailMapper.disableOrEnable(model);
    }

    public void delete(RegularorderdetailModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(model.getDetailid());
        model.setIdList(idList);
        regularorderdetailMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        RegularorderdetailModel model = new RegularorderdetailModel();
        model.setIdList(idList);
        regularorderdetailMapper.delete(model);
        return ResultData.toSuccessString();
    }

}