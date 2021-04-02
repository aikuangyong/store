package com.store.dao;

import com.store.model.ProvincesModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("provincesDao")
public interface ProvincesMapper {

    public List<ProvincesModel> getList(ProvincesModel model);

    public int getCount(ProvincesModel model);

    public void insert(ProvincesModel model);

    public void update(ProvincesModel model);

    public void disableOrEnable(ProvincesModel model);

    public void delete(ProvincesModel model);
}