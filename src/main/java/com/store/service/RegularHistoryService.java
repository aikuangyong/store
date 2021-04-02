package com.store.service;

import com.store.dao.RegularHistoryMapper;
import com.store.model.RegularHistoryModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("regularHistoryService")
public class RegularHistoryService {

    @Autowired
    private RegularHistoryMapper regularHistoryMapper;

    @Autowired
    private ExcelUtil<RegularHistoryModel> excelUtil;

    public XSSFWorkbook export(RegularHistoryModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<RegularHistoryModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(RegularHistoryModel model) {
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

    public RegularHistoryModel getModelById(RegularHistoryModel model) {
        List<RegularHistoryModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<RegularHistoryModel> getList(RegularHistoryModel model) {
        return regularHistoryMapper.getList(model);
    }

    public int getCount(RegularHistoryModel model) {
        return regularHistoryMapper.getCount(model);
    }

    public RegularHistoryModel insert(RegularHistoryModel model) {
        regularHistoryMapper.insert(model);
        return model;
    }

    public RegularHistoryModel update(RegularHistoryModel model) {
        regularHistoryMapper.update(model);
        return model;
    }

    public List<RegularHistoryModel> batchUpdate(List<RegularHistoryModel> modelList) {
        for (RegularHistoryModel model : modelList) {
            regularHistoryMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(RegularHistoryModel model) {
        regularHistoryMapper.disableOrEnable(model);
    }

    public void delete(RegularHistoryModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(model.getOrderno());
        model.setIdList(idList);
        regularHistoryMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        RegularHistoryModel model = new RegularHistoryModel();
        model.setIdList(idList);
        regularHistoryMapper.delete(model);
        return ResultData.toSuccessString();
    }

}