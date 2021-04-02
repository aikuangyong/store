package com.store.dao;

import com.store.model.VersionmanagerModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("versionmanagerDao")
public interface VersionmanagerMapper {

    public List<VersionmanagerModel> getList(VersionmanagerModel model);

    public VersionmanagerModel getNewVewsion(VersionmanagerModel model);

    public int getCount(VersionmanagerModel model);

    public void insert(VersionmanagerModel model);

    public void update(VersionmanagerModel model);

    public void disableOrEnable(VersionmanagerModel model);

    public void delete(VersionmanagerModel model);
}