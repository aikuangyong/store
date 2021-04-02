package com.store.dao;

import com.store.model.AfterserviceModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("afterserviceDao")
public interface AfterserviceMapper {

    public List<AfterserviceModel> getList(AfterserviceModel model);

    public int getCount(AfterserviceModel model);

    public void insert(AfterserviceModel model);

    public void update(AfterserviceModel model);

    public void disableOrEnable(AfterserviceModel model);

    public void delete(AfterserviceModel model);

    public List<AfterserviceModel> getAppList(AfterserviceModel model);
}