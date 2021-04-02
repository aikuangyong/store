package com.store.dao;

import com.store.model.SysmenuroleModel;
import com.store.model.SysroleModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sysmenuroleDao")
public interface SysmenuroleMapper {

    public List<SysmenuroleModel> getList(SysmenuroleModel model);

    public List<SysmenuroleModel> getMenuByRole(SysmenuroleModel model);

    public int getCount(SysmenuroleModel model);

    public void insert(SysmenuroleModel model);

    public void update(SysmenuroleModel model);

    public void disableOrEnable(SysmenuroleModel model);

    public void delete(SysmenuroleModel model);

    public void deleteByRoleid(SysmenuroleModel model);

    public void batchAddRoleMenu(SysmenuroleModel model);

}