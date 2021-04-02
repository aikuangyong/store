package com.store.dao;

import com.store.model.SysadminModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sysadminDao")
public interface SysadminMapper {

    List<SysadminModel> getList(SysadminModel model);

    int getCount(SysadminModel model);

    void insert(SysadminModel model);

    void update(SysadminModel model);

    void disableOrEnable(SysadminModel model);

    void delete(SysadminModel model);
}