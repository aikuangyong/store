package com.store.service;

import com.store.dao.BindinfoMapper;
import com.store.model.BindinfoModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("bindinfoService")
public class BindinfoService {

    @Autowired
    private BindinfoMapper bindinfoMapper;

    @Autowired
    private ExcelUtil<BindinfoModel> excelUtil;

    public XSSFWorkbook export(BindinfoModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<BindinfoModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(BindinfoModel model) {
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

    public BindinfoModel getModelById(BindinfoModel model){
        List<BindinfoModel> dataList = getList(model);
        if(dataList.size() > 0){
            return dataList.get(0);
        }
        return null;
    }

    public List<BindinfoModel> getList(BindinfoModel model){
        return bindinfoMapper.getList(model);
    }

    public int getCount(BindinfoModel model){
        return bindinfoMapper.getCount(model);
    }

    public BindinfoModel insert(BindinfoModel model){
        bindinfoMapper.insert(model);
        return model;
    }

    public BindinfoModel update(BindinfoModel model){
        bindinfoMapper.update(model);
        return model;
    }

    public List<BindinfoModel> batchUpdate(List<BindinfoModel> modelList){
        for (BindinfoModel model:modelList){
            bindinfoMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(BindinfoModel model){
        bindinfoMapper.disableOrEnable(model);
    }

    public void delete(BindinfoModel model){
        List<String> idList = new ArrayList<>();
        idList.add(model.getBid());
        model.setIdList(idList);
        bindinfoMapper.delete(model);
    }

    public String batchDelete(List<String> idList){
        BindinfoModel model = new BindinfoModel();
        model.setIdList(idList);
        bindinfoMapper.delete(model);
        return ResultData.toSuccessString();
    }

}