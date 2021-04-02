package com.store.service;

import com.store.dao.LoopinfoMapper;
import com.store.model.LoopinfoModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("loopinfoService")
public class LoopinfoService {

    @Autowired
    private LoopinfoMapper loopinfoMapper;

    @Autowired
    private ExcelUtil<LoopinfoModel> excelUtil;

    public XSSFWorkbook export(LoopinfoModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<LoopinfoModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(LoopinfoModel model) {
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

    public LoopinfoModel getModelById(LoopinfoModel model){
        List<LoopinfoModel> dataList = getList(model);
        if(dataList.size() > 0){
            return dataList.get(0);
        }
        return null;
    }

    public List<LoopinfoModel> getList(LoopinfoModel model){
        return loopinfoMapper.getList(model);
    }

    public int getCount(LoopinfoModel model){
        return loopinfoMapper.getCount(model);
    }

    public LoopinfoModel insert(LoopinfoModel model){
        loopinfoMapper.insert(model);
        return model;
    }

    public LoopinfoModel update(LoopinfoModel model){
        loopinfoMapper.update(model);
        return model;
    }

    public List<LoopinfoModel> batchUpdate(List<LoopinfoModel> modelList){
        for (LoopinfoModel model:modelList){
            loopinfoMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(LoopinfoModel model){
        loopinfoMapper.disableOrEnable(model);
    }

    public void delete(LoopinfoModel model){
        List<String> idList = new ArrayList<>();
        idList.add(model.getLoopid());
        model.setIdList(idList);
        loopinfoMapper.delete(model);
    }

    public String batchDelete(List<String> idList){
        LoopinfoModel model = new LoopinfoModel();
        model.setIdList(idList);
        loopinfoMapper.delete(model);
        return ResultData.toSuccessString();
    }

}