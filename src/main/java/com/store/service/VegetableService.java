package com.store.service;

import com.store.dao.ImageinfoMapper;
import com.store.dao.VegetableMapper;
import com.store.model.VegetableModel;
import com.store.model.ResultData;
import com.store.model.config.ImageinfoModel;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

@Service("vegetableService")
public class VegetableService {

    @Autowired
    private VegetableMapper vegetableMapper;

    @Autowired
    private ImageinfoMapper imageinfoMapper;

    @Autowired
    private ExcelUtil<VegetableModel> excelUtil;

    public XSSFWorkbook export(VegetableModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<VegetableModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(VegetableModel model) {
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

    public VegetableModel getModelById(VegetableModel model){
        List<VegetableModel> dataList = vegetableMapper.getDataById(model);
        setImageToVegetable(dataList);
        if(dataList.size() > 0){
            return dataList.get(0);
        }
        return null;
    }

    private void setImageToVegetable(List<VegetableModel> dataList){
        ImageinfoModel imageinfoModel = new ImageinfoModel();
        imageinfoModel.setImgGrpList(new ArrayList<>());
        for(VegetableModel vegettable:dataList){
            imageinfoModel.getImgGrpList().add(vegettable.getVegetableimg());
        }
        imageinfoModel.setPageNow(0);
        imageinfoModel.setPageSize(Integer.MAX_VALUE);
        List<ImageinfoModel> imgList = imageinfoMapper.getList(imageinfoModel);
        for(VegetableModel vegettable:dataList){
            String imageId = vegettable.getVegetableimg();
            List<ImageinfoModel> imageList = new ArrayList<>();
            if(null == vegettable.getImageList()){
                vegettable.setImageList(imageList);
            }
            Iterator<ImageinfoModel> it =  imgList.iterator();
            while(it.hasNext()){
                ImageinfoModel image = it.next();
                if(imageId.equals(image.getImggrp())){
                    imageList.add(image);
                    it.remove();
                }
            }
        }
    }

    public List<VegetableModel> getList(VegetableModel model){
        List<VegetableModel> dataList =  vegetableMapper.getList(model);
        setImageToVegetable(dataList);
        return dataList;
    }

    public List<VegetableModel> getListByTypeTop(VegetableModel model){
        List<VegetableModel> dataList =  vegetableMapper.getListByTypeTop(model);
        setImageToVegetable(dataList);
        return dataList;
    }

    public int getCount(VegetableModel model){
        return vegetableMapper.getCount(model);
    }

    public VegetableModel insert(VegetableModel model){
        vegetableMapper.insert(model);
        return model;
    }

    public VegetableModel update(VegetableModel model){
        vegetableMapper.update(model);
        return model;
    }

    public List<VegetableModel> batchUpdate(List<VegetableModel> modelList){
        for (VegetableModel model:modelList){
            vegetableMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(VegetableModel model){
        vegetableMapper.disableOrEnable(model);
    }

    public void delete(VegetableModel model){
        List<String> idList = new ArrayList<>();
        idList.add(model.getVid());
        model.setIdList(idList);
        vegetableMapper.delete(model);
    }

    public String batchDelete(List<String> idList){
        VegetableModel model = new VegetableModel();
        model.setIdList(idList);
        vegetableMapper.delete(model);
        return ResultData.toSuccessString();
    }

}