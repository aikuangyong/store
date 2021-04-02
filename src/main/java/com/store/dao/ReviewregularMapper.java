package com.store.dao;

import com.store.model.ReviewregularModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("reviewregularDao")
public interface ReviewregularMapper {

    public List<ReviewregularModel> getList(ReviewregularModel model);

    public int getCount(ReviewregularModel model);

    public void insert(ReviewregularModel model);

    public void update(ReviewregularModel model);

    public void disableOrEnable(ReviewregularModel model);

    public void delete(ReviewregularModel model);

    public void deleteById(ReviewregularModel model);
}