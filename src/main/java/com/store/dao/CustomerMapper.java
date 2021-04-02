package com.store.dao;

import com.store.model.CustomerModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("customerDao")
public interface CustomerMapper {

    public List<CustomerModel> getList(CustomerModel model);

    public int getCount(CustomerModel model);

    public void insert(CustomerModel model);

    public void update(CustomerModel model);

    public void changeBindUser(CustomerModel model);

    public void changeBindThird(CustomerModel model);

    public void updatePhone(CustomerModel model);

    public void disableOrEnable(CustomerModel model);

    public void delete(CustomerModel model);
}