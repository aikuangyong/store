package com.store.service;

import com.store.dao.RegularorderMapper;
import com.store.model.RegularorderModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("regularorderService")
public class RegularorderService {

    @Autowired
    private RegularorderMapper regularorderMapper;

    @Autowired
    private ExcelUtil<RegularorderModel> excelUtil;

    public XSSFWorkbook export(RegularorderModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<RegularorderModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(RegularorderModel model) {
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

    public RegularorderModel getModelById(RegularorderModel model){
        List<RegularorderModel> dataList = getList(model);
        if(dataList.size() > 0){
            return dataList.get(0);
        }
        return null;
    }

    public List<RegularorderModel> getList(RegularorderModel model){
        return regularorderMapper.getList(model);
    }

    public int getCount(RegularorderModel model){
        return regularorderMapper.getCount(model);
    }

    public RegularorderModel insert(RegularorderModel model){
        regularorderMapper.insert(model);
        return model;
    }

    public RegularorderModel update(RegularorderModel model){
        regularorderMapper.update(model);
        return model;
    }

    public RegularorderModel updateOrderByPay(RegularorderModel model){
        regularorderMapper.updateOrderByPay(model);
        return model;
    }

    public List<RegularorderModel> batchUpdate(List<RegularorderModel> modelList){
        for (RegularorderModel model:modelList){
            regularorderMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(RegularorderModel model){
        regularorderMapper.disableOrEnable(model);
    }

    public void delete(RegularorderModel model){
        List<String> idList = new ArrayList<>();
        idList.add(model.getOrderid());
        model.setIdList(idList);
        regularorderMapper.delete(model);
    }

    public String batchDelete(List<String> idList){
        RegularorderModel model = new RegularorderModel();
        model.setIdList(idList);
        regularorderMapper.delete(model);
        return ResultData.toSuccessString();
    }

}