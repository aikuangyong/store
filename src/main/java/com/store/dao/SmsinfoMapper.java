package com.store.dao;

import com.store.model.SmsinfoModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("smsinfoDao")
public interface SmsinfoMapper {

    public List<SmsinfoModel> getList(SmsinfoModel model);

    public int getCount(SmsinfoModel model);

    public void insert(SmsinfoModel model);

    public void update(SmsinfoModel model);

    public void disableOrEnable(SmsinfoModel model);

    public void delete(SmsinfoModel model);

    public SmsinfoModel getLastSmsInfo(SmsinfoModel model);
}