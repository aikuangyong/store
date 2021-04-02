package com.store.dao;

import com.store.model.DeleteRecord;
import com.store.model.OrderdetailModel;
import com.store.model.OrderinfoModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderinfoDao")
public interface OrderinfoMapper {

    public List<OrderinfoModel> getList(OrderinfoModel model);

    public List<OrderinfoModel> getAppOrderList(OrderinfoModel model);

    public List<OrderdetailModel> getAppOrderPorductList(OrderinfoModel model);

    public int getCount(OrderinfoModel model);

    public void insert(OrderinfoModel model);

    public void update(OrderinfoModel model);

    public void disableOrEnable(OrderinfoModel model);

    public void delete(OrderinfoModel model);

    public void deleteOrder(OrderinfoModel model);

    public void insertDeleteOrder(DeleteRecord deleteRecord);
}