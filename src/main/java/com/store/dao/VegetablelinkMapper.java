package com.store.dao;

import com.store.model.VegetablelinkModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("vegetablelinkDao")
public interface VegetablelinkMapper {

    public List<VegetablelinkModel> getAllRegularProduct(VegetablelinkModel model);

    public List<VegetablelinkModel> addMenuList(VegetablelinkModel model);

    public int addMenuListCount(VegetablelinkModel model);

    public List<VegetablelinkModel> getList(VegetablelinkModel model);

    public int getCount(VegetablelinkModel model);

    public void insert(VegetablelinkModel model);

    public void update(VegetablelinkModel model);

    public void disableOrEnable(VegetablelinkModel model);

    public void delete(VegetablelinkModel model);
}