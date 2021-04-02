package com.store.dao;

import com.store.model.UseraddressModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("useraddressDao")
public interface UseraddressMapper {

    public List<UseraddressModel> getList(UseraddressModel model);

    public int getCount(UseraddressModel model);

    public void insert(UseraddressModel model);

    public void update(UseraddressModel model);

    public void disableOrEnable(UseraddressModel model);

    public void delete(UseraddressModel model);
}