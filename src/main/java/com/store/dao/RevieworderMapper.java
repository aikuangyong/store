package com.store.dao;

import com.store.model.RevieworderModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("revieworderDao")
public interface RevieworderMapper {

    public List<RevieworderModel> getList(RevieworderModel model);

    public int getCount(RevieworderModel model);

    public void insert(RevieworderModel model);

    public void update(RevieworderModel model);

    public void disableOrEnable(RevieworderModel model);

    public void delete(RevieworderModel model);
}