package com.store.dao;

import com.store.model.UsertokenModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("usertokenDao")
public interface UsertokenMapper {

    public List<UsertokenModel> getList(UsertokenModel model);

    public int getCount(UsertokenModel model);

    public void insert(UsertokenModel model);

    public void update(UsertokenModel model);

    public void disableOrEnable(UsertokenModel model);

    public void delete(UsertokenModel model);
}