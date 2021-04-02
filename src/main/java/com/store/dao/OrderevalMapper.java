package com.store.dao;

import com.store.model.OrderevalModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderevalDao")
public interface OrderevalMapper {

    public List<OrderevalModel> getList(OrderevalModel model);

    public int getCount(OrderevalModel model);

    public void insert(OrderevalModel model);

    public void update(OrderevalModel model);

    public void clearTop(OrderevalModel model);

    public void disableOrEnable(OrderevalModel model);

    public void delete(OrderevalModel model);
}