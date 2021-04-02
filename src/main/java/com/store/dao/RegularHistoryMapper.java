package com.store.dao;

import com.store.model.RegularHistoryModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("regularHistoryDao")
public interface RegularHistoryMapper {

    public List<RegularHistoryModel> getList(RegularHistoryModel model);

    public int getCount(RegularHistoryModel model);

    public void insert(RegularHistoryModel model);

    public void update(RegularHistoryModel model);

    public void disableOrEnable(RegularHistoryModel model);

    public void delete(RegularHistoryModel model);
}