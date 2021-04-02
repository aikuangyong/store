package com.store.dao;

import com.store.model.OrderdetailModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderdetailDao")
public interface OrderdetailMapper {

    public List<OrderdetailModel> getList(OrderdetailModel model);

    public int getCount(OrderdetailModel model);

    public void insert(OrderdetailModel model);

    public void update(OrderdetailModel model);

    public void disableOrEnable(OrderdetailModel model);

    public void delete(OrderdetailModel model);
}