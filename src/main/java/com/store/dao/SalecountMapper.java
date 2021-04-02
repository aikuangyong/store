package com.store.dao;

import com.store.model.SalecountModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("salecountDao")
public interface SalecountMapper {

    public List<SalecountModel> getList(SalecountModel model);

    public int getCount(SalecountModel model);

    public void insert(SalecountModel model);

    public void update(SalecountModel model);

    public void disableOrEnable(SalecountModel model);

    public void delete(SalecountModel model);
}