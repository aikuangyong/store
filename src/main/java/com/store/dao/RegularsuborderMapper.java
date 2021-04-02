package com.store.dao;

import com.store.model.RegularsuborderModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("regularsuborderDao")
public interface RegularsuborderMapper {

    public List<RegularsuborderModel> getList(RegularsuborderModel model);

    public List<RegularsuborderModel> getMaxSendTimeByUserid(RegularsuborderModel model);

    public int getCount(RegularsuborderModel model);

    public void insert(RegularsuborderModel model);

    public void update(RegularsuborderModel model);

    public void updateSubOrder(RegularsuborderModel model);

    public void batchUpdate(RegularsuborderModel model);

    public void clearRegularOrder(String userid);

    public void disableOrEnable(RegularsuborderModel model);

    public void delete(RegularsuborderModel model);

    public void batchInsert(List<RegularsuborderModel> list);
}