package com.store.dao;

import com.store.model.SiteinfomationModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("siteinfomationDao")
public interface SiteinfomationMapper {

    public List<SiteinfomationModel> getList(SiteinfomationModel model);

    public int getCount(SiteinfomationModel model);

    public void insert(SiteinfomationModel model);

    public void update(SiteinfomationModel model);

    public void disableOrEnable(SiteinfomationModel model);

    public void delete(SiteinfomationModel model);
}