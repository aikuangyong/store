package com.store.service;

import com.store.dao.ProductsuborderlinkMapper;
import com.store.model.ProductsuborderlinkModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("productsuborderlinkService")
public class ProductsuborderlinkService {

    @Autowired
    private ProductsuborderlinkMapper productsuborderlinkMapper;

    @Autowired
    private ExcelUtil<ProductsuborderlinkModel> excelUtil;

    public void editRegularSuborder() {

    }

    public XSSFWorkbook export(ProductsuborderlinkModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<ProductsuborderlinkModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(ProductsuborderlinkModel model) {
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

    public ProductsuborderlinkModel getModelById(ProductsuborderlinkModel model) {
        List<ProductsuborderlinkModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<ProductsuborderlinkModel> getList(ProductsuborderlinkModel model) {
        return productsuborderlinkMapper.getList(model);
    }

    public int getCount(ProductsuborderlinkModel model) {
        return productsuborderlinkMapper.getCount(model);
    }

    public ProductsuborderlinkModel insert(ProductsuborderlinkModel model) {
        productsuborderlinkMapper.insert(model);
        return model;
    }

    public void batchInsert(List<ProductsuborderlinkModel> dataList) {
        productsuborderlinkMapper.batchInsert(dataList);
    }

    public ProductsuborderlinkModel update(ProductsuborderlinkModel model) {
        productsuborderlinkMapper.update(model);
        return model;
    }

    public List<ProductsuborderlinkModel> batchUpdate(List<ProductsuborderlinkModel> modelList) {
        for (ProductsuborderlinkModel model : modelList) {
            productsuborderlinkMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(ProductsuborderlinkModel model) {
        productsuborderlinkMapper.disableOrEnable(model);
    }

    public void delete(ProductsuborderlinkModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(String.valueOf(model.getId()));
        model.setIdList(idList);
        productsuborderlinkMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        ProductsuborderlinkModel model = new ProductsuborderlinkModel();
        model.setIdList(idList);
        productsuborderlinkMapper.delete(model);
        return ResultData.toSuccessString();
    }

}