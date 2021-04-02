package com.store.service;

import com.store.dao.SalecountMapper;
import com.store.model.SalecountModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("salecountService")
public class SalecountService {

    @Autowired
    private SalecountMapper salecountMapper;

    @Autowired
    private ExcelUtil<SalecountModel> excelUtil;

    public XSSFWorkbook export(SalecountModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<SalecountModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(SalecountModel model) {
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

    public SalecountModel getModelById(SalecountModel model){
        List<SalecountModel> dataList = getList(model);
        if(dataList.size() > 0){
            return dataList.get(0);
        }
        return null;
    }

    public List<SalecountModel> getList(SalecountModel model){
        return salecountMapper.getList(model);
    }

    public int getCount(SalecountModel model){
        return salecountMapper.getCount(model);
    }

    public SalecountModel insert(SalecountModel model){
        salecountMapper.insert(model);
        return model;
    }

    public SalecountModel update(SalecountModel model){
        salecountMapper.update(model);
        return model;
    }

    public List<SalecountModel> batchUpdate(List<SalecountModel> modelList){
        for (SalecountModel model:modelList){
            salecountMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(SalecountModel model){
        salecountMapper.disableOrEnable(model);
    }

    public String batchDelete(List<String> idList){
        SalecountModel model = new SalecountModel();
        model.setIdList(idList);
        salecountMapper.delete(model);
        return ResultData.toSuccessString();
    }

}