package com.store.dao;

import com.store.model.ShopcarModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("shopcarDao")
public interface ShopcarMapper {

    public List<ShopcarModel> getList(ShopcarModel model);

    public int getCount(ShopcarModel model);

    public void insert(ShopcarModel model);

    public void update(ShopcarModel model);

    public void disableOrEnable(ShopcarModel model);

    public void delete(ShopcarModel model);

    public void deleteByCondition(ShopcarModel model);
}