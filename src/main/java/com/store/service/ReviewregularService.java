package com.store.service;

import com.store.dao.ReviewregularMapper;
import com.store.model.ReviewregularModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("reviewregularService")
public class ReviewregularService {

    @Autowired
    private ReviewregularMapper reviewregularMapper;

    @Autowired
    private ExcelUtil<ReviewregularModel> excelUtil;



    public XSSFWorkbook export(ReviewregularModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<ReviewregularModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(ReviewregularModel model) {
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

    public ReviewregularModel getModelById(ReviewregularModel model){
        List<ReviewregularModel> dataList = getList(model);
        if(dataList.size() > 0){
            return dataList.get(0);
        }
        return null;
    }

    public List<ReviewregularModel> getList(ReviewregularModel model){
        return reviewregularMapper.getList(model);
    }

    public int getCount(ReviewregularModel model){
        return reviewregularMapper.getCount(model);
    }

    public ReviewregularModel insert(ReviewregularModel model){
        reviewregularMapper.insert(model);
        return model;
    }

    public ReviewregularModel update(ReviewregularModel model){
        reviewregularMapper.update(model);
        return model;
    }

    public List<ReviewregularModel> batchUpdate(List<ReviewregularModel> modelList){
        for (ReviewregularModel model:modelList){
            reviewregularMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(ReviewregularModel model){
        reviewregularMapper.disableOrEnable(model);
    }

    public void delete(ReviewregularModel model){
        List<String> idList = new ArrayList<>();
        idList.add(model.getRrid());
        model.setIdList(idList);
        reviewregularMapper.delete(model);
    }

    public void deleteById(ReviewregularModel model){
        reviewregularMapper.deleteById(model);
    }

    public String batchDelete(List<String> idList){
        ReviewregularModel model = new ReviewregularModel();
        model.setIdList(idList);
        reviewregularMapper.delete(model);
        return ResultData.toSuccessString();
    }

}