package com.store.service;

import com.store.dao.SiteinfomationMapper;
import com.store.model.SiteinfomationModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("siteinfomationService")
public class SiteinfomationService {

    @Autowired
    private SiteinfomationMapper siteinfomationMapper;

    @Autowired
    private ExcelUtil<SiteinfomationModel> excelUtil;

    public XSSFWorkbook export(SiteinfomationModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<SiteinfomationModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(SiteinfomationModel model) {
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

    public SiteinfomationModel getModelById(SiteinfomationModel model){
        List<SiteinfomationModel> dataList = getList(model);
        if(dataList.size() > 0){
            return dataList.get(0);
        }
        return null;
    }

    public List<SiteinfomationModel> getList(SiteinfomationModel model){
        return siteinfomationMapper.getList(model);
    }

    public int getCount(SiteinfomationModel model){
        return siteinfomationMapper.getCount(model);
    }

    public SiteinfomationModel insert(SiteinfomationModel model){
        siteinfomationMapper.insert(model);
        return model;
    }

    public SiteinfomationModel update(SiteinfomationModel model){
        siteinfomationMapper.update(model);
        return model;
    }

    public List<SiteinfomationModel> batchUpdate(List<SiteinfomationModel> modelList){
        for (SiteinfomationModel model:modelList){
            siteinfomationMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(SiteinfomationModel model){
        siteinfomationMapper.disableOrEnable(model);
    }

    public void delete(SiteinfomationModel model){
        List<String> idList = new ArrayList<>();
        idList.add(model.getId());
        model.setIdList(idList);
        siteinfomationMapper.delete(model);
    }

    public String batchDelete(List<String> idList){
        SiteinfomationModel model = new SiteinfomationModel();
        model.setIdList(idList);
        siteinfomationMapper.delete(model);
        return ResultData.toSuccessString();
    }

}