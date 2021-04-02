package com.store.service;

import com.store.dao.RevieworderMapper;
import com.store.model.RevieworderModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("revieworderService")
public class RevieworderService {

    @Autowired
    private RevieworderMapper revieworderMapper;

    @Autowired
    private ExcelUtil<RevieworderModel> excelUtil;

    public XSSFWorkbook export(RevieworderModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<RevieworderModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(RevieworderModel model) {
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

    public RevieworderModel getModelById(RevieworderModel model){
        List<RevieworderModel> dataList = getList(model);
        if(dataList.size() > 0){
            return dataList.get(0);
        }
        return null;
    }

    public List<RevieworderModel> getList(RevieworderModel model){
        return revieworderMapper.getList(model);
    }

    public int getCount(RevieworderModel model){
        return revieworderMapper.getCount(model);
    }

    public RevieworderModel insert(RevieworderModel model){
        revieworderMapper.insert(model);
        return model;
    }

    public RevieworderModel update(RevieworderModel model){
        revieworderMapper.update(model);
        return model;
    }

    public List<RevieworderModel> batchUpdate(List<RevieworderModel> modelList){
        for (RevieworderModel model:modelList){
            revieworderMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(RevieworderModel model){
        revieworderMapper.disableOrEnable(model);
    }

    public void delete(RevieworderModel model){
        List<String> idList = new ArrayList<>();
        idList.add(model.getRoid());
        model.setIdList(idList);
        revieworderMapper.delete(model);
    }

    public String batchDelete(List<String> idList){
        RevieworderModel model = new RevieworderModel();
        model.setIdList(idList);
        revieworderMapper.delete(model);
        return ResultData.toSuccessString();
    }

}