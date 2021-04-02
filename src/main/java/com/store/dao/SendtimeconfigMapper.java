package com.store.dao;

import com.store.model.SendtimeconfigModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sendtimeconfigDao")
public interface SendtimeconfigMapper {

    public List<SendtimeconfigModel> getList(SendtimeconfigModel model);

    public int getCount(SendtimeconfigModel model);

    public void insert(SendtimeconfigModel model);

    public void update(SendtimeconfigModel model);

    public void disableOrEnable(SendtimeconfigModel model);

    public void delete(SendtimeconfigModel model);

    public void deleteAll();
}