package com.store.dao;

import com.store.model.CitiesModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("citiesDao")
public interface CitiesMapper {

    public List<CitiesModel> getList(CitiesModel model);

    public int getCount(CitiesModel model);

    public void insert(CitiesModel model);

    public void update(CitiesModel model);

    public void disableOrEnable(CitiesModel model);

    public void delete(CitiesModel model);
}