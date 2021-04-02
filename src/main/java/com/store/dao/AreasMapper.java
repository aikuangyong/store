package com.store.dao;

import com.store.model.AreasModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("areasDao")
public interface AreasMapper {

    public List<AreasModel> getList(AreasModel model);

    public int getCount(AreasModel model);

    public void insert(AreasModel model);

    public void update(AreasModel model);

    public void disableOrEnable(AreasModel model);

    public void delete(AreasModel model);
}