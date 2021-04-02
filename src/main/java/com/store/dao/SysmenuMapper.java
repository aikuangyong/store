package com.store.dao;

import com.store.model.SysmenuModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sysmenuDao")
public interface SysmenuMapper {

    public List<SysmenuModel> getList(SysmenuModel model);

    public List<SysmenuModel> getMenuByRole(SysmenuModel model);

    public int getCount(SysmenuModel model);

    public String insert(SysmenuModel model);

    public void update(SysmenuModel model);

    public void disableOrEnable(SysmenuModel model);

    public void delete(SysmenuModel model);
}