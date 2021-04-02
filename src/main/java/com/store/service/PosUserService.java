package com.store.service;

import com.alibaba.fastjson.JSON;
import com.store.model.*;
import com.store.model.pos.*;
import com.store.utils.*;
import javafx.geometry.Pos;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PosUserService {

    @Autowired
    private PosUtil posUtil;

    @Autowired
    private RegularHistoryService regularHistoryService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VegetableService vegetableService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private OrderinfoService orderinfoService;

    @Autowired
    private OrderdetailService orderdetailService;

    public PosStockRealtime getStockRealtime(String goods_code) {
        StoreModel storeModel = new StoreModel();
        storeModel.noPage();
        List<StoreModel> storeList = storeService.getList(storeModel);
        if (CollectionUtils.isEmpty(storeList)) {
            return null;
        }
        List<PosStockRealtime> stockList = null;
        PosStockRealtime posStockRealtime = new PosStockRealtime();
        posStockRealtime.setGoods_code(goods_code);
        for (StoreModel model : storeList) {
            posStockRealtime.setStore_code(model.getPoseno());
            stockList = posUtil.getStockRealtime(posStockRealtime);
            if (null != stockList) {
                if (CollectionUtils.isNotEmpty(stockList)) {
                    return stockList.get(0);
                }
            }
        }
        return null;
    }

    public PosStockRealtime getStockRealtime(String goods_code, String store_code) {
        StoreModel storeModel = new StoreModel();
        storeModel.noPage();
        List<StoreModel> storeList = storeService.getList(storeModel);
        if (CollectionUtils.isEmpty(storeList)) {
            return null;
        }
        List<PosStockRealtime> stockList = null;
        PosStockRealtime posStockRealtime = new PosStockRealtime();
        posStockRealtime.setGoods_code(goods_code);
        posStockRealtime.setStore_code(store_code);
        stockList = posUtil.getStockRealtime(posStockRealtime);
        if (null != stockList) {
            if (CollectionUtils.isNotEmpty(stockList)) {
                return stockList.get(0);
            }
        }
        return null;
    }

    public PosData<PosCustomer> getMemberInfo(PosUser user) {
        PosData posData = posUtil.getMemberInfo(user);
        if (PosData.ERROR.equals(posData.getCode())) {
            throw new NullPointerException(posData.getMessage());
        }
        return posData;
    }

    public PosData<PosConsume> updateCard(String mobile, String cardno, String remark, String price) {
        PosUser posUser = new PosUser();
        posUser.setVip_no(cardno);
        PosData<PosCustomer> posData = posUtil.getMemberInfo(posUser);
        PosCustomer posCustomer = posData.getResult(PosCustomer.class);
        String point = posCustomer.getPoint();
        Double resultPrice = 0 - Double.parseDouble(MathUtil.sub(price, point));
        String orderNo = "";
        if (resultPrice > 0) {
            orderNo = CodeUtil.getOrderNo(4);
        } else {
            orderNo = CodeUtil.getOrderNo(5);
        }
        PosConsume posConsume = new PosConsume();
        posConsume.setMobile(mobile);
        posConsume.setOrderNo(orderNo);
        posConsume.setRemark(remark);
        posConsume.setWxConsumeNo(cardno);
        posConsume.setPrice(String.valueOf(resultPrice));
        //{"wxConsumeNo":"MEMB20180810000004","mobile":"13961618173","orderNo":"1","price":0.01}
        return posUtil.paidConsume(posConsume);
    }

    public PosData updateMemberInfo(String userid) {
        CustomerModel customerModel = customerService.getModelById(userid);
        PosUser posUser = new PosUser();
        posUser.setMobile(customerModel.getPhoneno());
        PosData<PosCustomer> posData = posUtil.getMemberInfo(posUser);
        List<PosCustomer> posUserList = posData.getResultList(PosCustomer.class);
        for (PosCustomer customer : posUserList) {
            String cardNo = customer.getCardNo();
            bindCardByModel(customerModel, cardNo);
        }
        return posData;
    }

    public PosData bindCardByModel(CustomerModel customerModel, String cardno) {
        PosUser posUser = new PosUser();
        posUser.setUser_id(customerModel.getUid());
        posUser.setMobile(customerModel.getPhoneno());
        posUser.setPassword(customerModel.getPassword());
        posUser.setLogo(customerModel.getIcon());
        posUser.setNick_name(customerModel.getNickname());
        posUser.setOther_id(customerModel.getUid());
        if (StringUtils.isNotBlank(cardno)) {
            PosData<PosCustomer> posCustomer = getInfoByCard(cardno);
            if (PosData.ERROR.equals(posCustomer.getCode())) {
                throw new IllegalArgumentException("查无此卡");
            }
            PosCustomer customer = posCustomer.getResult(PosCustomer.class);
            if (StringUtils.isNotBlank(customer.getPhone())) {
                throw new IllegalArgumentException("此卡已被使用");
            }
            posUser.setUser_id(cardno);
        } else {
            throw new IllegalArgumentException("卡号不能为空");
        }
        return update(posUser);
    }

    public PosData bindCard(String userid, String cardno) {
        PosUser posUser = new PosUser();
        CustomerModel customerModel = customerService.getModelById(userid);
        posUser.setOther_id(customerModel.getUid());
        posUser.setMobile(customerModel.getPhoneno());
        posUser.setPassword(customerModel.getPassword());
        posUser.setLogo(customerModel.getIcon());
        posUser.setNick_name(customerModel.getNickname());
        posUser.setOther_id(customerModel.getUid());
        if (StringUtils.isNotBlank(cardno)) {
            PosData<PosCustomer> posCustomer = getInfoByCard(cardno);
            if (PosData.ERROR.equals(posCustomer.getCode())) {
                throw new NullPointerException("查无此卡");
            }
            PosCustomer customer = posCustomer.getResult(PosCustomer.class);
            if (StringUtils.isNotBlank(customer.getPhone())) {
                throw new IllegalArgumentException("此卡已被使用");
            }
            posUser.setUser_id(cardno);
        } else {
            throw new NullPointerException("卡号不能为空");
        }
        posUser.setUser_id(cardno);
        update(posUser);
        return getInfoByCard(cardno);
    }

    public PosData update(PosUser user) {
        PosData<PosUser> posData = posUtil.updateMemberInfo(user);
        if (PosData.ERROR.equals(posData.getCode())) {
            throw new NullPointerException(posData.getMessage());
        }
        return posData;
    }

    public PosData<PosCustomer> getInfoByCard(String cardno) {
        PosUser posUser = new PosUser();
        posUser.setVip_no(cardno);
        PosData<PosCustomer> posData = posUtil.getMemberInfo(posUser);
        if (PosData.ERROR.equals(posData.getCode())) {
            throw new NullPointerException(posData.getMessage());
        }
        return posData;
    }

    public void submitOrder(String orderNo) {
        PosOfflineUploadRep rep = new PosOfflineUploadRep();
        OrderinfoModel orderinfoModel = new OrderinfoModel();
        orderinfoModel.setOrderno(orderNo);
        orderinfoModel = orderinfoService.getModelById(orderinfoModel);
        String userId = orderinfoModel.getUserid();
        CustomerModel userModel = customerService.getModelById(userId);
        String userPhone = userModel.getPhoneno();
        OrderdetailModel detailModel = new OrderdetailModel();
        detailModel.setOrderno(orderNo);
        detailModel.noPage();
        List<OrderdetailModel> detailList = orderdetailService.getList(detailModel);
        List<PosOfflineUploadGoodsRep> goods = new ArrayList<>();
        List<VegetableModel> vmodelList = vegetableService.getList(null);
        Map<String, VegetableModel> vmodelMap = new HashMap<>();
        for (VegetableModel vegetableModel : vmodelList) {
            vmodelMap.put(vegetableModel.getVid(), vegetableModel);
        }
        for (OrderdetailModel orderdetailModel : detailList) {
            PosOfflineUploadGoodsRep posOfflineUploadGoodsRep = new PosOfflineUploadGoodsRep();
            posOfflineUploadGoodsRep.setCount(orderdetailModel.getSalecount());
            posOfflineUploadGoodsRep.setPrice(Integer.parseInt(MathUtil.yuanToCents(orderdetailModel.getSaleprice().toString())));
            posOfflineUploadGoodsRep.setDismount_price(Integer.parseInt(MathUtil.yuanToCents(orderdetailModel.getPaidprice().toString())));
            String vid = orderdetailModel.getVegetable();
            VegetableModel vmodel = vmodelMap.get(vid);
            posOfflineUploadGoodsRep.setGoods_code(vmodel.getPosno());
            posOfflineUploadGoodsRep.setSku_code(vmodel.getSkuno());
        }

        rep.setMember_phone(userPhone);
        rep.setCreate_time(DateUtil.sdfTime.format(orderinfoModel.getCreatetime()));
        rep.setOrder_no(orderNo);
        rep.setPay_time(DateUtil.getTime());
        rep.setPaid_fee(MathUtil.yuanToCents(String.valueOf(orderinfoModel.getOrderprice())));
        rep.setPay_type(orderinfoModel.getPaytype());
        rep.setTotal_fee(MathUtil.yuanToCents(String.valueOf(orderinfoModel.getOrderprice())));
        rep.setGoods(goods);

        RegularHistoryModel regularHistoryModel = new RegularHistoryModel();
        regularHistoryModel.setOrderno("submitOrder-" + orderNo + DateUtil.getTimeStr());
        regularHistoryModel.setOrderinfo(JSON.toJSONString(rep));
        regularHistoryService.insert(regularHistoryModel);

        posUtil.offlineUpload(rep);
    }
}