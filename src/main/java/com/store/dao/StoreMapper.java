package com.store.dao;

import com.store.model.StoreModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("storeDao")
public interface StoreMapper {

    public List<StoreModel> getList(StoreModel model);

    public int getCount(StoreModel model);

    public void insert(StoreModel model);

    public void update(StoreModel model);

    public void disableOrEnable(StoreModel model);

    public void delete(StoreModel model);
}