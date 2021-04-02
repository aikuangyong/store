package com.store.service;

import com.store.api.model.Product.ShopCarVO;
import com.store.dao.ShopcarMapper;
import com.store.model.ShopcarModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Service("shopcarService")
public class ShopcarService {

    @Autowired
    private ShopcarMapper shopcarMapper;

    @Autowired
    private ExcelUtil<ShopcarModel> excelUtil;

    public XSSFWorkbook export(ShopcarModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<ShopcarModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(ShopcarModel model) {
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

    public ShopcarModel getModelById(ShopcarModel model) {
        List<ShopcarModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<ShopcarModel> getList(ShopcarModel model) {
        return shopcarMapper.getList(model);
    }

    public List<ShopCarVO> getListToApp(ShopcarModel model) {
        List<ShopcarModel> carList = shopcarMapper.getList(model);
        List<ShopCarVO> voList = new ArrayList<>();
        for (ShopcarModel carModel : carList) {
            voList.add(new ShopCarVO(carModel));
        }
        return voList;
    }

    public int getCount(ShopcarModel model) {
        return shopcarMapper.getCount(model);
    }

    public ShopcarModel insert(ShopcarModel model) {
        shopcarMapper.insert(model);
        return model;
    }

    public void deleteByCondition(ShopcarModel model) {
        shopcarMapper.deleteByCondition(model);
    }

    public void add(ShopcarModel model) {
        ShopcarModel existsModel = getModelById(model);
        if (null != existsModel) {
            existsModel.setCreatetime(new Date());
            int count = existsModel.getProductcount() + 1;
            existsModel.setProductcount(count);
            update(existsModel);
        } else {
            model.setProductcount(1);
            insert(model);
        }
    }

    public boolean sub(ShopcarModel model) {
        Integer count = model.getProductcount();
        model.setProductcount(null);
        ShopcarModel existsModel = getModelById(model);
        if (null != existsModel) {
            count = existsModel.getProductcount() - count;
            if (count > 0) {
                existsModel.setProductcount(count);
                update(existsModel);
                return true;
            }
            List<String> idList = new ArrayList<>();
            idList.add(model.getProductid());
            model.setIdList(idList);
            shopcarMapper.deleteByCondition(model);
            return true;
        }
        return false;
    }

    public ShopcarModel update(ShopcarModel model) {
        shopcarMapper.update(model);
        return model;
    }

    public List<ShopcarModel> batchUpdate(List<ShopcarModel> modelList) {
        for (ShopcarModel model : modelList) {
            shopcarMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(ShopcarModel model) {
        shopcarMapper.disableOrEnable(model);
    }

    public void delete(ShopcarModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(model.getCarid());
        model.setIdList(idList);
        shopcarMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        ShopcarModel model = new ShopcarModel();
        model.setIdList(idList);
        shopcarMapper.delete(model);
        return ResultData.toSuccessString();
    }

}