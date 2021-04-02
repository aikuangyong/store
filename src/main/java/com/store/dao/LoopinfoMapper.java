package com.store.dao;

import com.store.model.LoopinfoModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("loopinfoDao")
public interface LoopinfoMapper {

    public List<LoopinfoModel> getList(LoopinfoModel model);

    public int getCount(LoopinfoModel model);

    public void insert(LoopinfoModel model);

    public void update(LoopinfoModel model);

    public void disableOrEnable(LoopinfoModel model);

    public void delete(LoopinfoModel model);
}