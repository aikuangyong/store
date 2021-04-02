package com.store.service;

import com.store.dao.SendtimeconfigMapper;
import com.store.model.SendtimeconfigModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("sendtimeconfigService")
public class SendtimeconfigService {

    @Autowired
    private SendtimeconfigMapper sendtimeconfigMapper;

    @Autowired
    private ExcelUtil<SendtimeconfigModel> excelUtil;

    @Autowired
    private ConfigtblService configtblService;

    public XSSFWorkbook export(SendtimeconfigModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<SendtimeconfigModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(SendtimeconfigModel model) {
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

    public SendtimeconfigModel getModelById(SendtimeconfigModel model) {
        model.noPage();
        List<SendtimeconfigModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<SendtimeconfigModel> getList(SendtimeconfigModel model) {
        return sendtimeconfigMapper.getList(model);
    }

    public List<SendtimeconfigModel> getAllList() {
        SendtimeconfigModel sendtimeconfigModel = new SendtimeconfigModel();
        sendtimeconfigModel.noPage();
        sendtimeconfigModel.setOrderColumn("groupseq");
        sendtimeconfigModel.setOrderType("asc");
        return getList(sendtimeconfigModel);
    }

    public int getCount(SendtimeconfigModel model) {
        return sendtimeconfigMapper.getCount(model);
    }



    public SendtimeconfigModel insert(SendtimeconfigModel model) {
        sendtimeconfigMapper.insert(model);
        return model;
    }

    public SendtimeconfigModel update(SendtimeconfigModel model) {
        sendtimeconfigMapper.update(model);
        return model;
    }

    public List<SendtimeconfigModel> batchUpdate(List<SendtimeconfigModel> modelList) {
        for (SendtimeconfigModel model : modelList) {
            sendtimeconfigMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(SendtimeconfigModel model) {
        sendtimeconfigMapper.disableOrEnable(model);
    }

    public void delete(SendtimeconfigModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(String.valueOf(model.getId()));
        model.setIdList(idList);
        sendtimeconfigMapper.delete(model);
    }

    public void deleteAll(){
        sendtimeconfigMapper.deleteAll();
    }

    public String batchDelete(List<String> idList) {
        SendtimeconfigModel model = new SendtimeconfigModel();
        model.setIdList(idList);
        sendtimeconfigMapper.delete(model);
        return ResultData.toSuccessString();
    }

}