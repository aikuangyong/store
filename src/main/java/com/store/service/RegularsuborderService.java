package com.store.service;

import com.store.dao.RegularorderdetailMapper;
import com.store.dao.RegularsuborderMapper;
import com.store.model.OrderdetailModel;
import com.store.model.RegularorderdetailModel;
import com.store.model.RegularsuborderModel;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.ArrayList;

@Service("regularsuborderService")
public class RegularsuborderService {

    @Autowired
    private RegularsuborderMapper regularsuborderMapper;

    @Autowired
    private RegularorderdetailMapper regularDetailMapper;

    @Autowired
    private ExcelUtil<RegularsuborderModel> excelUtil;

    public void clearRegular(String userid) {
        regularsuborderMapper.clearRegularOrder(userid);
    }

    public void batchInsert(List<RegularsuborderModel> list) {
        regularsuborderMapper.batchInsert(list);
    }

    public XSSFWorkbook export(RegularsuborderModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<RegularsuborderModel> dataList = getList(model);
        for (RegularsuborderModel dataModel : dataList) {
            String statusStr = "";
            if ("1".equals(dataModel.getStatus())) {
                statusStr = "待配送";
            } else if ("2".equals(dataModel.getStatus())) {
                statusStr = "配送中";
            } else if ("3".equals(dataModel.getStatus())) {
                statusStr = "配送完成";
            } else {
                statusStr = "已取消";
            }
            dataModel.setStatus(statusStr);
            dataModel.setUnit(dataModel.getUnit() + "g");
            dataModel.setStagetime(dataModel.getBeginstagetime() + "-" + dataModel.getEndstagetime());
        }
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(RegularsuborderModel model) {
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

    public RegularsuborderModel getModelById(RegularsuborderModel model) {
        List<RegularsuborderModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<RegularsuborderModel> getList(RegularsuborderModel model) {
        return regularsuborderMapper.getList(model);
    }

    public List<RegularsuborderModel> getMaxSendTimeByUserid(RegularsuborderModel model) {
        return regularsuborderMapper.getMaxSendTimeByUserid(model);
    }

    public int getCount(RegularsuborderModel model) {
        return regularsuborderMapper.getCount(model);
    }

    public RegularsuborderModel insert(RegularsuborderModel model) {
        regularsuborderMapper.insert(model);
        return model;
    }

    public RegularsuborderModel update(RegularsuborderModel model) {
        regularsuborderMapper.update(model);
        return model;
    }

    public RegularsuborderModel updateSubOrder(RegularsuborderModel model) {
        regularsuborderMapper.updateSubOrder(model);
        return model;
    }

    @Transactional
    public RegularsuborderModel editSubOrderStatus(RegularsuborderModel model) {
        String status = model.getStatus();
        String oldStatus = model.getOldStatus();
        RegularorderdetailModel detailModel = new RegularorderdetailModel();
        detailModel.setConsumeCount(0);
        detailModel.setOrderno(model.getRegularno());
        if (("1".equals(oldStatus) || "2".equals(oldStatus))
                && ("3".equals(status) || "4".equals(status))) {
            detailModel.setConsumeCount(-1);
        } else if (("3".equals(oldStatus) || "4".equals(oldStatus))
                && ("1".equals(status) || "2".equals(status))) {
            detailModel.setConsumeCount(1);
        }
        RegularorderdetailModel orderIdModel = regularDetailMapper.getOrderIdByNo(detailModel);
        detailModel.setOrderid(orderIdModel.getOrderid());
        regularDetailMapper.editRegularOrderDetailSurplus(detailModel);
        regularsuborderMapper.update(model);
        return model;
    }

    public void batchUpdate(RegularsuborderModel model) {
        regularsuborderMapper.batchUpdate(model);
    }

    public void disableOrEnable(RegularsuborderModel model) {
        regularsuborderMapper.disableOrEnable(model);
    }

    public void delete(RegularsuborderModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(model.getSuborderid());
        model.setIdList(idList);
        regularsuborderMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        RegularsuborderModel model = new RegularsuborderModel();
        model.setIdList(idList);
        regularsuborderMapper.delete(model);
        return ResultData.toSuccessString();
    }

}