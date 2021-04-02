package com.store.api.model.order;

import com.store.model.OrderdetailModel;
import com.store.model.OrderinfoModel;
import com.store.utils.DateUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderInfoVO {

    private String orderid;
    private String sendinfo;
    private String phoneno;
    private String storeid;
    private String storename;
    private String receiveaddress;
    private String vegetable;
    private String vegetableimage;
    private String orderstatus;
    private String receivephone;
    private String receiveuser;
    private String nickname;
    private String orderno;
    private String tradetime;
    private String sendtime;
    private String sendtype;
    private Double saleprice;
    private Double orderprice;
    private String userid;
    private String addressid;
    private String paytype;
    private Date paytime;
    private String cardno;
    private String storephone;
    private String storeaddress;
    private List<OrderDetailVO> detailList;

    public OrderInfoVO(OrderinfoModel model) {
        this.sendinfo = model.getSendinfo();
        this.phoneno = model.getPhoneno();
        this.storeid = model.getStoreid();
        this.storename = model.getStorename();
        this.storephone = model.getStorephone();
        this.storeaddress = model.getStoreaddress();
        this.receiveaddress = model.getReceiveaddress();
        this.vegetable = model.getVegetable();
        this.vegetableimage = model.getVegetableimage();
        this.orderstatus = model.getOrderstatus();
        this.receivephone = model.getReceivephone();
        this.nickname = model.getNickname();
        this.orderno = model.getOrderno();
        if (null != model.getCreatetime()) {
            this.tradetime = DateUtil.sdfTime.format(model.getCreatetime());
        }
        this.paytime = model.getPaytime();
        this.paytype = model.getPaytype();
        this.cardno = model.getCardno();
        this.sendtime = model.getSendtime();
        this.sendtype = model.getSendtype();
        this.saleprice = model.getSaleprice();
        this.orderprice = model.getOrderprice();
        this.userid = model.getUserid();
        this.addressid = model.getAddressid();
        this.receiveuser = model.getReceiveuser();
        List<OrderdetailModel> modelDetailList = model.getDetailList();
        this.detailList = new ArrayList<>();
        if (null != modelDetailList) {
            for (OrderdetailModel detailModel : modelDetailList) {
                this.detailList.add(new OrderDetailVO(detailModel));
            }
        }
    }

    public OrderInfoVO() {
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getSendinfo() {
        return sendinfo;
    }

    public void setSendinfo(String sendinfo) {
        this.sendinfo = sendinfo;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getStorename() {
        return storename;
    }

    public void setStoren2ame(String storename) {
        this.storename = storename;
    }

    public String getReceiveaddress() {
        return receiveaddress;
    }

    public void setReceiveaddress(String receiveaddress) {
        this.receiveaddress = receiveaddress;
    }

    public String getVegetable() {
        return vegetable;
    }

    public void setVegetable(String vegetable) {
        this.vegetable = vegetable;
    }

    public String getVegetableimage() {
        return vegetableimage;
    }

    public void setVegetableimage(String vegetableimage) {
        this.vegetableimage = vegetableimage;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getReceivephone() {
        return receivephone;
    }

    public void setReceivephone(String receivephone) {
        this.receivephone = receivephone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getSendtype() {
        return sendtype;
    }

    public void setSendtype(String sendtype) {
        this.sendtype = sendtype;
    }

    public Double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(Double saleprice) {
        this.saleprice = saleprice;
    }

    public Double getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(Double orderprice) {
        this.orderprice = orderprice;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    public List<OrderDetailVO> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<OrderDetailVO> detailList) {
        this.detailList = detailList;
    }
}