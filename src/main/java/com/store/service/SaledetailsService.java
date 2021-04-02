package com.store.service;

import com.store.dao.SaledetailsMapper;
import com.store.model.SaledetailsModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("saledetailsService")
public class SaledetailsService {

    @Autowired
    private SaledetailsMapper saledetailsMapper;

    @Autowired
    private ExcelUtil<SaledetailsModel> excelUtil;

    public XSSFWorkbook export(SaledetailsModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<SaledetailsModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(SaledetailsModel model) {
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

    public SaledetailsModel getModelById(SaledetailsModel model){
        List<SaledetailsModel> dataList = getList(model);
        if(dataList.size() > 0){
            return dataList.get(0);
        }
        return null;
    }

    public List<SaledetailsModel> getList(SaledetailsModel model){
        return saledetailsMapper.getList(model);
    }

    public int getCount(SaledetailsModel model){
        return saledetailsMapper.getCount(model);
    }

    public SaledetailsModel insert(SaledetailsModel model){
        saledetailsMapper.insert(model);
        return model;
    }

    public SaledetailsModel update(SaledetailsModel model){
        saledetailsMapper.update(model);
        return model;
    }

    public List<SaledetailsModel> batchUpdate(List<SaledetailsModel> modelList){
        for (SaledetailsModel model:modelList){
            saledetailsMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(SaledetailsModel model){
        saledetailsMapper.disableOrEnable(model);
    }

    public void delete(SaledetailsModel model){
        List<String> idList = new ArrayList<>();
        idList.add(model.getDetailid());
        model.setIdList(idList);
        saledetailsMapper.delete(model);
    }

    public String batchDelete(List<String> idList){
        SaledetailsModel model = new SaledetailsModel();
        model.setIdList(idList);
        saledetailsMapper.delete(model);
        return ResultData.toSuccessString();
    }

}