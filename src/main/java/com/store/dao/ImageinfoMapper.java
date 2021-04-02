package com.store.dao;

import com.store.model.config.ImageinfoModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("imageinfoDao")
public interface ImageinfoMapper {

    public List<ImageinfoModel> getDefaultImg(ImageinfoModel model);

    public List<ImageinfoModel> getList(ImageinfoModel model);

    public int getCount(ImageinfoModel model);

    public void insert(ImageinfoModel model);

    public void update(ImageinfoModel model);

    public void disableOrEnable(ImageinfoModel model);

    public void delete(ImageinfoModel model);
}