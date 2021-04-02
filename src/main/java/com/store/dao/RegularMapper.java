package com.store.dao;

import com.store.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("regularDao")
public interface RegularMapper {


    public List<UserRegularOrderInfo> getRegularOrderList(UserRegularOrderInfo orderInfo);

    public List<RegularOrderInfo> getRegularOrderInfo(RegularOrderInfo orderInfo);

    public List<SendDay> getSendDay();

    public List<RegularDate> getRegularDate(RegularDate dateModel);

    public List<RegularModel> getList(RegularModel model);

    public int getCount(RegularModel model);

    public void insert(RegularModel model);

    public void update(RegularModel model);

    public void disableOrEnable(RegularModel model);

    public void delete(RegularModel model);
}