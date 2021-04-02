package com.store.dao;

import com.store.model.RegularorderModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("regularorderDao")
public interface RegularorderMapper {

    public List<RegularorderModel> getList(RegularorderModel model);

    public int getCount(RegularorderModel model);

    public void insert(RegularorderModel model);

    public void update(RegularorderModel model);

    public void updateOrderByPay(RegularorderModel model);

    public void disableOrEnable(RegularorderModel model);

    public void delete(RegularorderModel model);
}