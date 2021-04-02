package com.store.service;

import com.store.dao.ConfigtblMapper;
import com.store.model.ConfigtblModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Service("configtblService")
public class ConfigtblService {

    @Autowired
    private ConfigtblMapper configtblMapper;

    @Autowired
    private ExcelUtil<ConfigtblModel> excelUtil;

    private static final Logger LOGGER = Logger.getLogger(ConfigtblService.class);


    public ConfigtblModel getConfigModel(String dataType) {
        ConfigtblModel model = new ConfigtblModel();
        model.setDatatype(dataType);
        return getModelById(model);
    }

    public boolean saveConfigModel(ConfigtblModel model) {
        try {
            String commonts = model.getComments();
            model.setComments("");
            ConfigtblModel configtblModel = getModelById(model);
            model.setCreatetime(new Date());
            if (null == configtblModel) {
                insert(model);
            } else {
                model.setComments(commonts);
                update(model);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }


    public XSSFWorkbook export(ConfigtblModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<ConfigtblModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(ConfigtblModel model) {
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

    public ConfigtblModel getModelById(ConfigtblModel model) {
        List<ConfigtblModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<ConfigtblModel> getList(ConfigtblModel model) {
        return configtblMapper.getList(model);
    }

    public int getCount(ConfigtblModel model) {
        return configtblMapper.getCount(model);
    }

    public ConfigtblModel insert(ConfigtblModel model) {
        configtblMapper.insert(model);
        return model;
    }

    public ConfigtblModel update(ConfigtblModel model) {
        configtblMapper.update(model);
        return model;
    }

    public List<ConfigtblModel> batchUpdate(List<ConfigtblModel> modelList) {
        for (ConfigtblModel model : modelList) {
            configtblMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(ConfigtblModel model) {
        configtblMapper.disableOrEnable(model);
    }

    public void delete(ConfigtblModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(String.valueOf(model.getId()));
        model.setIdList(idList);
        configtblMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        ConfigtblModel model = new ConfigtblModel();
        model.setIdList(idList);
        configtblMapper.delete(model);
        return ResultData.toSuccessString();
    }

}