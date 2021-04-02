package com.store.service;

import com.store.dao.OrderdetailMapper;
import com.store.model.OrderdetailModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("orderdetailService")
public class OrderdetailService {

    @Autowired
    private OrderdetailMapper orderdetailMapper;

    @Autowired
    private ExcelUtil<OrderdetailModel> excelUtil;

    public XSSFWorkbook export(OrderdetailModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<OrderdetailModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(OrderdetailModel model) {
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

    public OrderdetailModel getModelById(OrderdetailModel model){
        List<OrderdetailModel> dataList = getList(model);
        if(dataList.size() > 0){
            return dataList.get(0);
        }
        return null;
    }

    public List<OrderdetailModel> getList(OrderdetailModel model){
        return orderdetailMapper.getList(model);
    }

    public int getCount(OrderdetailModel model){
        return orderdetailMapper.getCount(model);
    }

    public OrderdetailModel insert(OrderdetailModel model){
        orderdetailMapper.insert(model);
        return model;
    }

    public OrderdetailModel update(OrderdetailModel model){
        orderdetailMapper.update(model);
        return model;
    }

    public List<OrderdetailModel> batchUpdate(List<OrderdetailModel> modelList){
        for (OrderdetailModel model:modelList){
            orderdetailMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(OrderdetailModel model){
        orderdetailMapper.disableOrEnable(model);
    }

    public void delete(OrderdetailModel model){
        List<String> idList = new ArrayList<>();
        idList.add(model.getDetailid());
        model.setIdList(idList);
        orderdetailMapper.delete(model);
    }

    public String batchDelete(List<String> idList){
        OrderdetailModel model = new OrderdetailModel();
        model.setIdList(idList);
        orderdetailMapper.delete(model);
        return ResultData.toSuccessString();
    }

}