package com.store.service;

import com.store.dao.AreasMapper;
import com.store.model.AreasModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("areasService")
public class AreasService {

    @Autowired
    private AreasMapper areasMapper;

    @Autowired
    private ExcelUtil<AreasModel> excelUtil;

    public XSSFWorkbook export(AreasModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<AreasModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(AreasModel model) {
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

    public AreasModel getModelById(AreasModel model){
        List<AreasModel> dataList = getList(model);
        if(dataList.size() > 0){
            return dataList.get(0);
        }
        return null;
    }

    public List<AreasModel> getList(AreasModel model){
        return areasMapper.getList(model);
    }

    public int getCount(AreasModel model){
        return areasMapper.getCount(model);
    }

    public AreasModel insert(AreasModel model){
        areasMapper.insert(model);
        return model;
    }

    public AreasModel update(AreasModel model){
        areasMapper.update(model);
        return model;
    }

    public List<AreasModel> batchUpdate(List<AreasModel> modelList){
        for (AreasModel model:modelList){
            areasMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(AreasModel model){
        areasMapper.disableOrEnable(model);
    }

}