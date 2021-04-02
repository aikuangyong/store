package com.store.dao;

import com.store.model.SitemsgModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sitemsgDao")
public interface SitemsgMapper {

    public List<SitemsgModel> getList(SitemsgModel model);

    public int getCount(SitemsgModel model);

    public void insert(SitemsgModel model);

    public void update(SitemsgModel model);

    public void disableOrEnable(SitemsgModel model);

    public void delete(SitemsgModel model);
}