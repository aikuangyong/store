package com.store.dao;

import com.store.model.BindinfoModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bindinfoDao")
public interface BindinfoMapper {

    public List<BindinfoModel> getList(BindinfoModel model);

    public int getCount(BindinfoModel model);

    public void insert(BindinfoModel model);

    public void update(BindinfoModel model);

    public void disableOrEnable(BindinfoModel model);

    public void delete(BindinfoModel model);
}