package com.store.dao;

import com.store.model.VegetableModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("vegetableDao")
public interface VegetableMapper {

    public List<VegetableModel> getList(VegetableModel model);

    public List<VegetableModel> getListByTypeTop(VegetableModel model);

    public List<VegetableModel> getDataById(VegetableModel model);

    public int getCount(VegetableModel model);

    public void insert(VegetableModel model);

    public void update(VegetableModel model);

    public void disableOrEnable(VegetableModel model);

    public void delete(VegetableModel model);
}