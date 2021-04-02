package com.store.dao;

import com.store.model.ProductsuborderlinkModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("productsuborderlinkDao")
public interface ProductsuborderlinkMapper {

    public List<ProductsuborderlinkModel> getList(ProductsuborderlinkModel model);

    public int getCount(ProductsuborderlinkModel model);

    public void insert(ProductsuborderlinkModel model);

    public void batchInsert(List<ProductsuborderlinkModel> dataList);

    public void update(ProductsuborderlinkModel model);

    public void disableOrEnable(ProductsuborderlinkModel model);

    public void delete(ProductsuborderlinkModel model);
}