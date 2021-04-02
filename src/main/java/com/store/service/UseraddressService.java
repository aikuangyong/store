package com.store.service;

import com.store.dao.UseraddressMapper;
import com.store.model.UseraddressModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.ArrayList;

@Service("useraddressService")
public class UseraddressService {

    @Autowired
    private UseraddressMapper useraddressMapper;

    @Autowired
    private ExcelUtil<UseraddressModel> excelUtil;

    public XSSFWorkbook export(UseraddressModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<UseraddressModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(UseraddressModel model) {
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

    public UseraddressModel getModelById(UseraddressModel model){
        model.noPage();
        List<UseraddressModel> dataList = getList(model);
        if(dataList.size() > 0){
            return dataList.get(0);
        }
        return null;
    }

    public List<UseraddressModel> getList(UseraddressModel model){
        return useraddressMapper.getList(model);
    }

    public int getCount(UseraddressModel model){
        return useraddressMapper.getCount(model);
    }

    public UseraddressModel add(UseraddressModel model){
        UseraddressModel nowAddress = new UseraddressModel();
        nowAddress.setUserid(model.getUserid());
        List<UseraddressModel> nowData = getList(nowAddress);
        if(CollectionUtils.isEmpty(nowData)){
            model.setIsdefault("1");
        }

        model = insert(model);
        if("1".equals(model.getIsdefault())){
            setIsDefault(model.getUserid(),model.getAddressid());
        }
        return model;
    }

    public UseraddressModel insert(UseraddressModel model){
        useraddressMapper.insert(model);
        return model;
    }

    public UseraddressModel update(UseraddressModel model){
        useraddressMapper.update(model);
        return model;
    }

    @Transactional
    public void setIsDefault(String userid,String addressid){
        UseraddressModel allModel = new UseraddressModel();
        allModel.setIsdefault("2");
        allModel.setUserid(userid);
        useraddressMapper.update(allModel);
        allModel.setIsdefault("1");
        allModel.setAddressid(addressid);
        useraddressMapper.update(allModel);
    }

    public List<UseraddressModel> batchUpdate(List<UseraddressModel> modelList){
        for (UseraddressModel model:modelList){
            useraddressMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(UseraddressModel model){
        useraddressMapper.disableOrEnable(model);
    }

    public void delete(UseraddressModel model){
        List<String> idList = new ArrayList<>();
        idList.add(model.getAddressid());
        model.setIdList(idList);
        useraddressMapper.delete(model);
    }

    public String batchDelete(List<String> idList){
        UseraddressModel model = new UseraddressModel();
        model.setIdList(idList);
        useraddressMapper.delete(model);
        return ResultData.toSuccessString();
    }

}