package com.store.service;

import com.store.dao.VegetabletypeMapper;
import com.store.model.VegetabletypeModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("vegetabletypeService")
public class VegetabletypeService {

    @Autowired
    private VegetabletypeMapper vegetabletypeMapper;

    @Autowired
    private ExcelUtil<VegetabletypeModel> excelUtil;

    public XSSFWorkbook export(VegetabletypeModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<VegetabletypeModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(VegetabletypeModel model) {
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

    public VegetabletypeModel getModelById(VegetabletypeModel model){
        List<VegetabletypeModel> dataList = getList(model);
        if(dataList.size() > 0){
            return dataList.get(0);
        }
        return null;
    }

    public List<VegetabletypeModel> getList(VegetabletypeModel model){
        return vegetabletypeMapper.getList(model);
    }

    public int getCount(VegetabletypeModel model){
        return vegetabletypeMapper.getCount(model);
    }

    public VegetabletypeModel insert(VegetabletypeModel model){
        vegetabletypeMapper.insert(model);
        return model;
    }

    public VegetabletypeModel update(VegetabletypeModel model){
        vegetabletypeMapper.update(model);
        return model;
    }

    public List<VegetabletypeModel> batchUpdate(List<VegetabletypeModel> modelList){
        for (VegetabletypeModel model:modelList){
            vegetabletypeMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(VegetabletypeModel model){
        vegetabletypeMapper.disableOrEnable(model);
    }

    public void delete(VegetabletypeModel model){
        List<String> idList = new ArrayList<>();
        idList.add(model.getVtid());
        model.setIdList(idList);
        vegetabletypeMapper.delete(model);
    }

    public String batchDelete(List<String> idList){
        VegetabletypeModel model = new VegetabletypeModel();
        model.setIdList(idList);
        vegetabletypeMapper.delete(model);
        return ResultData.toSuccessString();
    }

}