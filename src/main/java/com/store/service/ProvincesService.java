package com.store.service;

import com.store.dao.ProvincesMapper;
import com.store.model.ProvincesModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("provincesService")
public class ProvincesService {

    @Autowired
    private ProvincesMapper provincesMapper;

    @Autowired
    private ExcelUtil<ProvincesModel> excelUtil;

    public XSSFWorkbook export(ProvincesModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<ProvincesModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(ProvincesModel model) {
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

    public ProvincesModel getModelById(ProvincesModel model){
        List<ProvincesModel> dataList = getList(model);
        if(dataList.size() > 0){
            return dataList.get(0);
        }
        return null;
    }

    public List<ProvincesModel> getList(ProvincesModel model){
        return provincesMapper.getList(model);
    }

    public int getCount(ProvincesModel model){
        return provincesMapper.getCount(model);
    }

    public ProvincesModel insert(ProvincesModel model){
        provincesMapper.insert(model);
        return model;
    }

    public ProvincesModel update(ProvincesModel model){
        provincesMapper.update(model);
        return model;
    }

    public List<ProvincesModel> batchUpdate(List<ProvincesModel> modelList){
        for (ProvincesModel model:modelList){
            provincesMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(ProvincesModel model){
        provincesMapper.disableOrEnable(model);
    }

}