package com.store.service;

import com.store.dao.RegularcomponentMapper;
import com.store.model.RegularcomponentModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Service("regularcomponentService")
public class RegularcomponentService {

    @Autowired
    private RegularcomponentMapper regularcomponentMapper;

    @Autowired
    private ExcelUtil<RegularcomponentModel> excelUtil;

    public XSSFWorkbook export(RegularcomponentModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<RegularcomponentModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(RegularcomponentModel model) {
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

    public RegularcomponentModel getModelById(RegularcomponentModel model) {
        List<RegularcomponentModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<RegularcomponentModel> getList(RegularcomponentModel model) {
        return regularcomponentMapper.getList(model);
    }

    public RegularcomponentModel getRegularPrice(Map<String, Object> param) {
        return regularcomponentMapper.getRegularPrice(param);
    }

    public int getCount(RegularcomponentModel model) {
        return regularcomponentMapper.getCount(model);
    }

    public RegularcomponentModel insert(RegularcomponentModel model) {
        regularcomponentMapper.insert(model);
        return model;
    }

    public RegularcomponentModel update(RegularcomponentModel model) {
        regularcomponentMapper.update(model);
        return model;
    }

    public List<RegularcomponentModel> batchUpdate(List<RegularcomponentModel> modelList) {
        for (RegularcomponentModel model : modelList) {
            regularcomponentMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(RegularcomponentModel model) {
        regularcomponentMapper.disableOrEnable(model);
    }

    public void delete(RegularcomponentModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(model.getRcid());
        model.setIdList(idList);
        regularcomponentMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        RegularcomponentModel model = new RegularcomponentModel();
        model.setIdList(idList);
        regularcomponentMapper.delete(model);
        return ResultData.toSuccessString();
    }

}