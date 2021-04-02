package com.store.service;

import com.store.api.model.data.StoreVO;
import com.store.dao.StoreMapper;
import com.store.model.StoreModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import com.store.utils.MapUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

@Service("storeService")
public class StoreService {

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private RegularService regularService;

    @Autowired
    private MapUtil mapUtil;

    @Autowired
    private ExcelUtil<StoreModel> excelUtil;

    public XSSFWorkbook export(StoreModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<StoreModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(StoreModel model) {
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

    public StoreModel getModelById(StoreModel model) {
        List<StoreModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<StoreModel> getList(StoreModel model) {
        return storeMapper.getList(model);
    }

    public List<StoreVO> getListToApp(StoreModel model) {
        List<StoreModel> list = storeMapper.getList(model);
        List<StoreVO> voList = new ArrayList<>();
        for (StoreModel store : list) {
            StoreVO vo = new StoreVO(store);
            voList.add(vo);
        }
        return voList;
    }

    public List<StoreVO> getListToAppWidth(StoreModel model, String currentLng, String currentLat) {
        List<StoreModel> list = storeMapper.getList(model);
        List<StoreVO> voList = new ArrayList<>();
        Integer range = Integer.parseInt(regularService.getSendStage()) * 1000;
        for (StoreModel store : list) {
            Double distribution = mapUtil.getDistance(Double.parseDouble(store.getLat()), Double.parseDouble(store.getLng()), Double.parseDouble(currentLat), Double.parseDouble(currentLng));
            Integer storeRange = (int) Math.abs(distribution);
            Integer distributeScope = range - distribution >= 0 ? 1 : 2;
            StoreVO vo = new StoreVO(store, distributeScope, storeRange);
            voList.add(vo);
        }
        Collections.sort(voList, new Comparator<StoreVO>() {
            @Override
            public int compare(StoreVO s1, StoreVO s2) {
                int diff = s1.getStoreRange() - s2.getStoreRange();
                if (diff > 0) {
                    return 1;
                } else if (diff < 0) {
                    return -1;
                }
                return 0; //相等为0
            }
        });
        return voList;
    }

    public int getCount(StoreModel model) {
        return storeMapper.getCount(model);
    }

    public StoreModel insert(StoreModel model) {
        storeMapper.insert(model);
        return model;
    }

    public StoreModel update(StoreModel model) {
        storeMapper.update(model);
        return model;
    }

    public List<StoreModel> batchUpdate(List<StoreModel> modelList) {
        for (StoreModel model : modelList) {
            storeMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(StoreModel model) {
        storeMapper.disableOrEnable(model);
    }

    public void delete(StoreModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(model.getStoreid());
        model.setIdList(idList);
        storeMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        StoreModel model = new StoreModel();
        model.setIdList(idList);
        storeMapper.delete(model);
        return ResultData.toSuccessString();
    }

}