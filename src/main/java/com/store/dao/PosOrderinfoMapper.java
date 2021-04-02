package com.store.dao;

import com.store.api.pos.model.Param.PosOrderDetail;
import com.store.api.pos.model.Param.PosOrderInfo;
import com.store.model.pos.QueryPosModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("posOrderInfoDao")
public interface PosOrderinfoMapper {

    public List<String> getOrderIdList(QueryPosModel model);

    public List<PosOrderInfo> getList(QueryPosModel model);

    public Integer getCount(QueryPosModel model);

    public PosOrderDetail getOrderDetail(QueryPosModel model);

}