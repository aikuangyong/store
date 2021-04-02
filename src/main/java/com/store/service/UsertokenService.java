package com.store.service;

import com.store.dao.UsertokenMapper;
import com.store.model.UsertokenModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.DateUtil;
import com.store.utils.EncryptUtils;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("usertokenService")
public class UsertokenService {

    @Autowired
    private UsertokenMapper usertokenMapper;

    @Autowired
    private ExcelUtil<UsertokenModel> excelUtil;

    public XSSFWorkbook export(UsertokenModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<UsertokenModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }


    public UsertokenModel getUserToken(String userId) {
        UsertokenModel usertokenModel = new UsertokenModel();
        usertokenModel.setUserid(userId);
        usertokenModel = this.getModelById(usertokenModel);
        boolean userExists = true;
        if (null == usertokenModel) {
            usertokenModel = new UsertokenModel();
            usertokenModel.setUserid(userId);
            userExists = false;
        }
        Long millis = System.currentTimeMillis();
        String token = EncryptUtils.md5(userId + millis);
        usertokenModel.setLogintime(millis);
        usertokenModel.setCreatetime(DateUtil.getDate());
        usertokenModel.setToken(token);
        if (userExists) {
            update(usertokenModel);
        } else {
            insert(usertokenModel);
        }
        return usertokenModel;
    }

    public ResultData getData(UsertokenModel model) {
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

    public UsertokenModel getModelById(UsertokenModel model) {
        List<UsertokenModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<UsertokenModel> getList(UsertokenModel model) {
        return usertokenMapper.getList(model);
    }

    public int getCount(UsertokenModel model) {
        return usertokenMapper.getCount(model);
    }

    public UsertokenModel insert(UsertokenModel model) {
        usertokenMapper.insert(model);
        return model;
    }

    public UsertokenModel update(UsertokenModel model) {
        usertokenMapper.update(model);
        return model;
    }

    public List<UsertokenModel> batchUpdate(List<UsertokenModel> modelList) {
        for (UsertokenModel model : modelList) {
            usertokenMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(UsertokenModel model) {
        usertokenMapper.disableOrEnable(model);
    }

}