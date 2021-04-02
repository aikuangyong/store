package com.store.service;

import com.store.dao.SitemsgMapper;
import com.store.model.SitemsgModel;
import com.store.model.ResultData;
import com.store.utils.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("sitemsgService")
public class SitemsgService {

    @Autowired
    private SitemsgMapper sitemsgMapper;

    @Autowired
    private PushUtil pushUtil;

    @Autowired
    private ExcelUtil<SitemsgModel> excelUtil;

    public XSSFWorkbook export(SitemsgModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<SitemsgModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(SitemsgModel model) {
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

    public SitemsgModel getModelById(SitemsgModel model) {
        List<SitemsgModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<SitemsgModel> getList(SitemsgModel model) {
        return sitemsgMapper.getList(model);
    }

    public int getCount(SitemsgModel model) {
        return sitemsgMapper.getCount(model);
    }

    public SitemsgModel insert(SitemsgModel model) {
        model.setCreatetime(new Date());
        model.setMsgid(EncryptUtils.md5(System.currentTimeMillis() + CodeUtil.getRandomNumber(11)));
        Map<String, String> contentParam = new HashMap<>();
        contentParam.put("title", model.getMsgtitle());
        contentParam.put("id", model.getMsgid());
        pushUtil.push(contentParam);
        sitemsgMapper.insert(model);
        return model;
    }

    public SitemsgModel update(SitemsgModel model) {
        sitemsgMapper.update(model);
        return model;
    }

    public List<SitemsgModel> batchUpdate(List<SitemsgModel> modelList) {
        for (SitemsgModel model : modelList) {
            sitemsgMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(SitemsgModel model) {
        sitemsgMapper.disableOrEnable(model);
    }

    public void delete(SitemsgModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(model.getMsgid());
        model.setIdList(idList);
        sitemsgMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        SitemsgModel model = new SitemsgModel();
        model.setIdList(idList);
        sitemsgMapper.delete(model);
        return ResultData.toSuccessString();
    }

}