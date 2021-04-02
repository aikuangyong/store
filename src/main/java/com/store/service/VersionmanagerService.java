package com.store.service;

import com.store.dao.VersionmanagerMapper;
import com.store.model.VersionmanagerModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("versionmanagerService")
public class VersionmanagerService {

    @Autowired
    private VersionmanagerMapper versionmanagerMapper;

    @Autowired
    private ExcelUtil<VersionmanagerModel> excelUtil;

    public XSSFWorkbook export(VersionmanagerModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<VersionmanagerModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public VersionmanagerModel getNewVewsion(VersionmanagerModel model) {
        return versionmanagerMapper.getNewVewsion(null);
    }

    public ResultData getData(VersionmanagerModel model) {
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

    public VersionmanagerModel getModelById(VersionmanagerModel model) {
        List<VersionmanagerModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<VersionmanagerModel> getList(VersionmanagerModel model) {
        return versionmanagerMapper.getList(model);
    }

    public int getCount(VersionmanagerModel model) {
        return versionmanagerMapper.getCount(model);
    }

    public VersionmanagerModel insert(VersionmanagerModel model) {
        versionmanagerMapper.insert(model);
        return model;
    }

    public VersionmanagerModel update(VersionmanagerModel model) {
        versionmanagerMapper.update(model);
        return model;
    }

    public List<VersionmanagerModel> batchUpdate(List<VersionmanagerModel> modelList) {
        for (VersionmanagerModel model : modelList) {
            versionmanagerMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(VersionmanagerModel model) {
        versionmanagerMapper.disableOrEnable(model);
    }

    public void delete(VersionmanagerModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(model.getVid());
        model.setIdList(idList);
        versionmanagerMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        VersionmanagerModel model = new VersionmanagerModel();
        model.setIdList(idList);
        versionmanagerMapper.delete(model);
        return ResultData.toSuccessString();
    }

}