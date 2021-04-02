package com.store.dao;

import com.store.model.RegularorderdetailModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("regularorderdetailDao")
public interface RegularorderdetailMapper {

    public RegularorderdetailModel getDetailData(RegularorderdetailModel model);

    public List<RegularorderdetailModel> getList(RegularorderdetailModel model);

    public int getCount(RegularorderdetailModel model);

    public void insert(RegularorderdetailModel model);

    public void update(RegularorderdetailModel model);

    public void updateOrderStatus(RegularorderdetailModel model);

    public RegularorderdetailModel getOrderIdByNo(RegularorderdetailModel model);

    public void editRegularOrderDetailSurplus(RegularorderdetailModel model);

    public void clearRegularSubOrder(RegularorderdetailModel model);

    public void clearRegularOrderDetail(RegularorderdetailModel model);

    public void disableOrEnable(RegularorderdetailModel model);

    public void delete(RegularorderdetailModel model);
}