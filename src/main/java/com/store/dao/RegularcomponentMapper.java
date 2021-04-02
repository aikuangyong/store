package com.store.dao;

import com.store.model.RegularHistoryModel;
import com.store.model.RegularcomponentModel;
import com.store.model.SendDay;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("regularcomponentDao")
public interface RegularcomponentMapper {

    public List<RegularcomponentModel> getList(RegularcomponentModel model);

    public RegularcomponentModel getRegularPrice(Map<String, Object> param);

    public int getCount(RegularcomponentModel model);

    public void insert(RegularcomponentModel model);

    public void update(RegularcomponentModel model);

    public void disableOrEnable(RegularcomponentModel model);

    public void delete(RegularcomponentModel model);

    public void deleteByRid(RegularcomponentModel model);
}