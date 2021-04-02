package com.store.dao;

import com.store.model.SysroleModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sysroleDao")
public interface SysroleMapper {

    public List<SysroleModel> getList(SysroleModel model);

    public int getCount(SysroleModel model);

    public void insert(SysroleModel model);

    public void update(SysroleModel model);

    public void disableOrEnable(SysroleModel model);

    public void delete(SysroleModel model);

}