package com.store.dao;

import com.store.model.VegetabletypeModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("vegetabletypeDao")
public interface VegetabletypeMapper {

    public List<VegetabletypeModel> getList(VegetabletypeModel model);

    public int getCount(VegetabletypeModel model);

    public void insert(VegetabletypeModel model);

    public void update(VegetabletypeModel model);

    public void disableOrEnable(VegetabletypeModel model);

    public void delete(VegetabletypeModel model);
}