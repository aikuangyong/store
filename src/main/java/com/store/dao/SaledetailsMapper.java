package com.store.dao;

import com.store.model.SaledetailsModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("saledetailsDao")
public interface SaledetailsMapper {

    public List<SaledetailsModel> getList(SaledetailsModel model);

    public int getCount(SaledetailsModel model);

    public void insert(SaledetailsModel model);

    public void update(SaledetailsModel model);

    public void disableOrEnable(SaledetailsModel model);

    public void delete(SaledetailsModel model);
}