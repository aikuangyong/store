package com.store.service;

import com.store.dao.SysmenuroleMapper;
import com.store.dao.SysroleMapper;
import com.store.model.SysmenuroleModel;
import com.store.model.SysroleModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import com.store.utils.exception.RequiredException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("sysroleService")
public class SysroleService {

    @Autowired
    private SysroleMapper sysroleMapper;

    @Autowired
    private SysmenuroleMapper sysmenuroleMapper;

    @Autowired
    private ExcelUtil<SysroleModel> excelUtil;

    public XSSFWorkbook export(SysroleModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<SysroleModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }



    public ResultData getData(SysroleModel model) {
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

    public SysroleModel getModelById(SysroleModel model) {
        List<SysroleModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<SysroleModel> getList(SysroleModel model) {
        return sysroleMapper.getList(model);
    }

    public int getCount(SysroleModel model) {
        return sysroleMapper.getCount(model);
    }

    public SysroleModel save(SysroleModel model) throws RequiredException {
        this.insert(model);
        SysmenuroleModel rmModel = new SysmenuroleModel();
        rmModel.setRoleid(model.getRoleid());
        sysmenuroleMapper.batchAddRoleMenu(rmModel);
        return model;
    }

    public SysroleModel insert(SysroleModel model) throws RequiredException {
        SysroleModel checkModel = new SysroleModel();
        checkModel.setRolename(model.getRolename());
        if (this.getList(checkModel).size() == 0) {
            sysroleMapper.insert(model);
        } else {
            throw new RequiredException("角色名称已存在，请勿重复添加");
        }
        return model;
    }

    public SysroleModel update(SysroleModel model) {
        sysroleMapper.update(model);
        return model;
    }

    public List<SysroleModel> batchUpdate(List<SysroleModel> modelList) {
        for (SysroleModel model : modelList) {
            sysroleMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(SysroleModel model) {
        sysroleMapper.disableOrEnable(model);
    }

    public void delete(SysroleModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(model.getRoleid());
        model.setIdList(idList);
        sysroleMapper.delete(model);
        SysmenuroleModel rmModel = new SysmenuroleModel();
        rmModel.setIdList(model.getIdList());
        sysmenuroleMapper.deleteByRoleid(rmModel);
    }

    public String batchDelete(List<String> idList) {
        SysroleModel model = new SysroleModel();
        model.setIdList(idList);
        sysroleMapper.delete(model);
        SysmenuroleModel rmModel = new SysmenuroleModel();
        rmModel.setIdList(model.getIdList());
        sysmenuroleMapper.deleteByRoleid(rmModel);
        return ResultData.toSuccessString();
    }

}