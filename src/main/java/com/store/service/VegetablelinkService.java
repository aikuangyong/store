package com.store.service;

import com.store.dao.ProductsuborderlinkMapper;
import com.store.dao.VegetablelinkMapper;
import com.store.model.ProductsuborderlinkModel;
import com.store.model.RegularsuborderModel;
import com.store.model.VegetablelinkModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.DateUtil;
import com.store.utils.ExcelUtil;
import com.store.utils.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service("vegetablelinkService")
public class VegetablelinkService {

    @Autowired
    private VegetablelinkMapper vegetablelinkMapper;

    @Autowired
    private ProductsuborderlinkService productsuborderlinkService;

    @Autowired
    private RegularsuborderService regularsuborderService;

    @Autowired
    private ExcelUtil<VegetablelinkModel> excelUtil;

    public XSSFWorkbook export(VegetablelinkModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<VegetablelinkModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public List<VegetablelinkModel> getAllRegularProduct(VegetablelinkModel linkModel) {
        return vegetablelinkMapper.getAllRegularProduct(linkModel);
    }

    public Map<String, VegetablelinkModel> getAllRegularByMapProduct(VegetablelinkModel linkModel) {
        List<VegetablelinkModel> linkList = vegetablelinkMapper.getAllRegularProduct(linkModel);
        Map<String, VegetablelinkModel> linkData = new HashMap<String, VegetablelinkModel>();
        for (VegetablelinkModel dataModel : linkList) {
            linkData.put(dataModel.getConfigdate(), dataModel);
        }
        return linkData;
    }

    public String getLinkProduct(VegetablelinkModel model) {
        model.setPageNow(0);
        model.setPageSize(Integer.MAX_VALUE);
        List<VegetablelinkModel> dataList = vegetablelinkMapper.addMenuList(model);
        List<String> productList = new ArrayList<>();
        for (VegetablelinkModel vmodel : dataList) {
            productList.add(vmodel.getProductname());
        }
        return StringUtils.join(productList, ";");
    }

    public ResultData getAddMenuList(VegetablelinkModel model) {
        ResultData returnData = new ResultData();
        try {
            returnData.setDataList(vegetablelinkMapper.addMenuList(model));
            returnData.setDataObj(getLinkProduct(model));
            int dataCount = this.getAddMenuListCount(model);
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

    public int getAddMenuListCount(VegetablelinkModel model) {
        return vegetablelinkMapper.addMenuListCount(model);
    }


    public ResultData getData(VegetablelinkModel model) {
        ResultData returnData = new ResultData();
        try {
            returnData.setDataList(this.getList(model));
            int dataCount = this.getCount(model);
            returnData.setPageCount(dataCount, model.getPageSize());
            returnData.setDataCount(dataCount);
            returnData.setState(ConstantVariable.SUCCESS);
            returnData.setPageNumber(model.getPageNumber());
        } catch (Exception e) {
            e.printStackTrace();
            returnData.setState(ConstantVariable.ERROR);
            returnData.setMsg(e.getMessage());
        }
        return returnData;
    }

    public int getCount(VegetablelinkModel model) {
        return vegetablelinkMapper.getCount(model);
    }

    public VegetablelinkModel getModelById(VegetablelinkModel model) {
        List<VegetablelinkModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<VegetablelinkModel> getList(VegetablelinkModel model) {
        return vegetablelinkMapper.getList(model);
    }

    @Transactional
    public VegetablelinkModel insert(VegetablelinkModel model) {
        vegetablelinkMapper.insert(model);
        Map<String, VegetablelinkModel> vegetablelinkModelMap = getAllRegularByMapProduct(null);
        RegularsuborderModel regularsuborderModel = new RegularsuborderModel();
        regularsuborderModel.setEqSendTime(model.getConfigdate());
        List<RegularsuborderModel> suborderList = regularsuborderService.getList(regularsuborderModel);
        for (RegularsuborderModel suborder : suborderList) {
            VegetablelinkModel linkModel = vegetablelinkModelMap.get(suborder.getSendtime());
            if (null != linkModel) {
                suborder.setProductname(linkModel.getProductname());
                regularsuborderService.update(suborder);
                ProductsuborderlinkModel subLinkModel = new ProductsuborderlinkModel();
                subLinkModel.setSuborderid(suborder.getSuborderid());
                subLinkModel = productsuborderlinkService.getModelById(subLinkModel);
                if(null != subLinkModel){
                    subLinkModel.setVid(linkModel.getProductid());
                    productsuborderlinkService.update(subLinkModel);
                }else{
                    subLinkModel = new ProductsuborderlinkModel();
                    subLinkModel.setSuborderid(suborder.getSuborderid());
                    subLinkModel.setVid(linkModel.getProductid());
                    subLinkModel.setCreatetime(new Date());
                    productsuborderlinkService.insert(subLinkModel);
                }
            }
        }
        return model;
    }

    public VegetablelinkModel update(VegetablelinkModel model) {
        vegetablelinkMapper.update(model);
        return model;
    }

    public List<VegetablelinkModel> batchUpdate(List<VegetablelinkModel> modelList) {
        for (VegetablelinkModel model : modelList) {
            vegetablelinkMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(VegetablelinkModel model) {
        vegetablelinkMapper.disableOrEnable(model);
    }

    @Transactional
    public void delete(VegetablelinkModel model) {
        VegetablelinkModel vegetablelinkModel = getModelById(model);
        List<String> idList = new ArrayList<>();
        idList.add(model.getVlid());
        model.setIdList(idList);
        vegetablelinkMapper.delete(model);
        if(null != vegetablelinkModel){
            RegularsuborderModel regularsuborderModel = new RegularsuborderModel();
            regularsuborderModel.setEqSendTime(vegetablelinkModel.getConfigdate());
            List<RegularsuborderModel> suborderList = regularsuborderService.getList(regularsuborderModel);
            Map<String, VegetablelinkModel> vegetablelinkModelMap = getAllRegularByMapProduct(null);
            for (RegularsuborderModel suborder : suborderList) {
                VegetablelinkModel linkModel = vegetablelinkModelMap.get(suborder.getSendtime());
                ProductsuborderlinkModel subLinkModel = new ProductsuborderlinkModel();
                subLinkModel.setSuborderid(suborder.getSuborderid());
                subLinkModel = productsuborderlinkService.getModelById(subLinkModel);
                if(null == subLinkModel){
                    continue;
                }
                if (null != linkModel) {
                    subLinkModel.setVid(linkModel.getProductid());
                    suborder.setProductname(linkModel.getProductname());
                    productsuborderlinkService.update(subLinkModel);
                }else{
                    suborder.setProductname("未订购商品");
                    productsuborderlinkService.delete(subLinkModel);
                }
                regularsuborderService.update(suborder);
            }
        }
    }

    public String batchDelete(List<String> idList) {
        VegetablelinkModel model = new VegetablelinkModel();
        model.setIdList(idList);
        vegetablelinkMapper.delete(model);
        return ResultData.toSuccessString();
    }

}