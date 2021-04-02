package com.${scheml}.service;

import com.${scheml}.dao.${name?cap_first}Mapper;
import com.${scheml}.model.${name?cap_first}Model;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("${name?uncap_first}Service")
public class ${name?cap_first}Service {

    @Autowired
    private ${name?cap_first}Mapper ${name?uncap_first}Mapper;

    @Autowired
    private ExcelUtil<${name?cap_first}Model> excelUtil;

    public XSSFWorkbook export(${name?cap_first}Model model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<${name?cap_first}Model> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(${name?cap_first}Model model) {
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

    public ${name?cap_first}Model getModelById(${name?cap_first}Model model){
        List<${name?cap_first}Model> dataList = getList(model);
        if(dataList.size() > 0){
            return dataList.get(0);
        }
        return null;
    }

    public List<${name?cap_first}Model> getList(${name?cap_first}Model model){
        return ${name?uncap_first}Mapper.getList(model);
    }

    public int getCount(${name?cap_first}Model model){
        return ${name?uncap_first}Mapper.getCount(model);
    }

    public ${name?cap_first}Model insert(${name?cap_first}Model model){
        ${name?uncap_first}Mapper.insert(model);
        return model;
    }

    public ${name?cap_first}Model update(${name?cap_first}Model model){
        ${name?uncap_first}Mapper.update(model);
        return model;
    }

    public List<${name?cap_first}Model> batchUpdate(List<${name?cap_first}Model> modelList){
        for (${name?cap_first}Model model:modelList){
            ${name?uncap_first}Mapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(${name?cap_first}Model model){
        ${name?uncap_first}Mapper.disableOrEnable(model);
    }

    public void delete(${name?cap_first}Model model){
        List<String> idList = new ArrayList<>();
        idList.add(model.get${primaryKey?cap_first}());
        model.setIdList(idList);
        ${name?uncap_first}Mapper.delete(model);
    }

    public String batchDelete(List<String> idList){
        ${name?cap_first}Model model = new ${name?cap_first}Model();
        model.setIdList(idList);
        ${name}Mapper.delete(model);
        return ResultData.toSuccessString();
    }

}