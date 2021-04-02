package com.store.dao;

import com.store.model.ConfigtblModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("configtblDao")
public interface ConfigtblMapper {

    public List<ConfigtblModel> getList(ConfigtblModel model);

    public int getCount(ConfigtblModel model);

    public void insert(ConfigtblModel model);

    public void update(ConfigtblModel model);

    public void disableOrEnable(ConfigtblModel model);

    public void delete(ConfigtblModel model);
}