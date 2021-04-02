package com.store.service;

import com.store.api.model.order.OrderDetailVO;
import com.store.api.model.order.OrderInfoVO;
import com.store.api.pos.ValidatePosException;
import com.store.api.pos.model.Param.PosGoodInfo;
import com.store.api.pos.model.Param.PosOrderDetail;
import com.store.api.pos.model.Param.PosOrderInfo;
import com.store.api.pos.model.Param.PosOrderVO;
import com.store.api.pos.model.PosReturnModel;
import com.store.dao.OrderinfoMapper;
import com.store.dao.PosOrderinfoMapper;
import com.store.model.*;
import com.store.model.pos.*;
import com.store.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service("orderinfoService")
public class OrderinfoService {

    @Autowired
    private PosOrderinfoMapper posOrderinfoMapper;

    @Autowired
    private OrderinfoMapper orderinfoMapper;

    @Autowired
    private PosUserService posService;

    @Autowired
    private PosUtil posUtil;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UseraddressService addressService;

    @Autowired
    private VegetableService vegetableService;

    @Autowired
    private OrderdetailService orderdetailService;

    @Autowired
    private RegularorderService regularorderService;

    @Autowired
    private RegularorderdetailService regularorderdetailService;

    @Autowired
    private ShopcarService shopcarService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ExcelUtil<OrderinfoModel> excelUtil;

    public XSSFWorkbook export(OrderinfoModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<OrderinfoModel> dataList = getList(model);
        for (OrderinfoModel orderInfo : dataList) {
            orderInfo.setOrderstatus(orderStatus(orderInfo.getOrderstatus()));
            orderInfo.setSendtype(sendType(orderInfo.getSendtype()));
        }
        return excelUtil.exportExcel(model, dataList);
    }

    public PosReturnModel getOrderIdList(String id, QueryPosModel posOrder) {
        List<String> idList = posOrderinfoMapper.getOrderIdList(posOrder);
        return PosReturnModel.returnSuccess(id, idList);
    }

    public PosOrderDetail getOrderDetail(QueryPosModel posOrder) {
        PosOrderDetail posOrderDetail = posOrderinfoMapper.getOrderDetail(posOrder);
        return posOrderDetail;
    }

    public void editPosOrderInfo(PosOrderInfo posOrder) {
    }

    private String orderStatus(String orderStatus) {
        if (ConstantVariable.ORDER_READY_PAY.equals(orderStatus)) {
            return "待付款";
        } else if (ConstantVariable.ORDER_2.equals(orderStatus)) {
            return "待发货";
        } else if (ConstantVariable.ORDER_3.equals(orderStatus)) {
            return "配送中";
        } else if (ConstantVariable.ORDER_READY_PULL.equals(orderStatus)) {
            return "待提货";
        } else if (ConstantVariable.ORDER_READY_EVAL.equals(orderStatus)) {
            return "待评价";
        } else if (ConstantVariable.ORDER_CANCEL.equals(orderStatus)) {
            return "已取消";
        } else {
            return "已完成";
        }
    }

    private String sendType(String sendType) {
        if (ConstantVariable.ORDER_READY_PAY.equals(sendType)) {
            return "店内用餐";
        } else if (ConstantVariable.ORDER_2.equals(sendType)) {
            return "打包带走";
        } else {
            return "外送";
        }
    }

    public UserCounsumeInfoVO getCounsumeInfo(String userid, String cardno) {
        PosUser posUser = new PosUser();
        posUser.setVip_no(cardno);
        PosData<PosCustomer> posData = posService.getMemberInfo(posUser);
        PosCustomer posCustomer = posData.getResult(PosCustomer.class);
        OrderinfoModel orderinfoModel = new OrderinfoModel();
        orderinfoModel.noPage();
        orderinfoModel.setUserid(userid);
        List<OrderinfoModel> orderList = getList(orderinfoModel);
        UserCounsumeInfoVO vo = new UserCounsumeInfoVO();
        vo.setCardNo(posCustomer.getCardNo());
        vo.setCashPayRate(posCustomer.getCashPayRate());
        vo.setPoint(ConstantVariable.PRICE_FORMAT.format(new Double(posCustomer.getPoint())));
        for (OrderinfoModel orderModel : orderList) {
            if (orderModel.getOrderstatus().equals(ConstantVariable.ORDER_READY_PAY)) {
                continue;
            }
            if (!orderModel.getOrderstatus().equals(ConstantVariable.CARD_PAY)) {
                continue;
            }
            if (null == orderModel.getPaytime()) {
                continue;
            }
            List<CounsemeDetailVO> detailList = vo.getDetailList();
            if (CollectionUtils.isEmpty(detailList)) {
                detailList = new ArrayList<>();
                vo.setDetailList(detailList);
            }
            CounsemeDetailVO detailVO = new CounsemeDetailVO();
            detailVO.setPrice(orderModel.getSaleprice());
            detailVO.setCounsumeDate(orderModel.getPaytime());
            detailVO.setDateStr(DateUtil.sdfTime.format(orderModel.getPaytime()));
            detailVO.setType(CounsemeDetailVO.SUB);
            detailVO.setTypeStr(CounsemeDetailVO.typeMap.get(CounsemeDetailVO.SUB));
            detailList.add(detailVO);
        }
        return vo;
    }

    public PosData getPaidConsumeLog(String userid) {
        CustomerModel customerModel = customerService.getModelById(userid);
        String phone = customerModel.getPhoneno();
        PosConsume posConsume = new PosConsume();
        posConsume.setUser_id(customerModel.getUid());
        posConsume.setMobile(phone);
        posConsume.setPage_size(200);
        return posUtil.getPaidConsumeLog(posConsume);
    }


    public PosData consumeCard(String userid, String orderno, String ordertype, String cardno) {
        CustomerModel userModel = customerService.getModelById(userid);
        Double price = 0d;
        String orderNo = orderno;
        String remark = "";
        String userId = userModel.getUid();
        String mobile = userModel.getPhoneno();
        String orderId = "-1";
        if ("1".equals(ordertype)) {
            OrderinfoModel orderinfoModel = new OrderinfoModel();
            orderinfoModel.setOrderno(orderno);
            orderinfoModel = getModelById(orderinfoModel);
            price = orderinfoModel.getOrderprice();
            remark = orderinfoModel.getSendinfo();
            orderId = orderinfoModel.getOrderid();
        } else {
            RegularorderModel orderInfo = new RegularorderModel();
            orderInfo.setOrderno(orderno);
            orderInfo = regularorderService.getModelById(orderInfo);
            RegularorderdetailModel regularDetail = new RegularorderdetailModel();
            regularDetail.setOrderid(orderInfo.getOrderid());
            regularDetail = regularorderdetailService.getModelById(regularDetail);
            price = regularDetail.getOrderprice();
            remark = regularDetail.getContent();
            orderId = orderInfo.getOrderid();
        }
        PosUser posUser = new PosUser();
        posUser.setVip_no(cardno);
        PosData<PosCustomer> cardInfo = posUtil.getMemberInfo(posUser);
        PosCustomer posCustomer = cardInfo.getResult(PosCustomer.class);
        String salePrice = String.valueOf(null == price ? 0 : price);
        salePrice = MathUtil.discount(posCustomer.getCashPayRate(), salePrice);

        Double otherPrice = Double.parseDouble(MathUtil.sub(posCustomer.getPoint(), salePrice));
        if (otherPrice < 0) {
            PosData returnData = new PosData();
            returnData.setCode(PosData.ERROR);
            returnData.setMessage("剩余金额不足");
            return returnData;
        }
        PosConsume posConsume = new PosConsume();
        posConsume.setMobile(PosUtil.PHONE);
        posConsume.setOrderNo(orderNo);
        posConsume.setRemark(remark);
        posConsume.setWxConsumeNo(cardno);
        posConsume.setPrice(String.valueOf(price));
        PosData<PosConsume> posData = posUtil.paidConsume(posConsume);
        //{"wxConsumeNo":"MEMB20180810000004","mobile":"13961618173","orderNo":"1","price":0.01}
        if (PosData.SUCCESS.equals(posData.getCode())) {
            if ("1".equals(ordertype)) {
                OrderinfoModel orderinfoModel = new OrderinfoModel();
                orderinfoModel.setOrderno(orderno);
                orderinfoModel.setOrderid(orderId);
                orderinfoModel.setPaytype(ConstantVariable.CARD_PAY);
                orderinfoModel.setOrderstatus(ConstantVariable.ORDER_2);
                orderinfoModel.setCardno(cardno);
                orderinfoModel.setPaytime(new Date());
                orderinfoModel.setSaleprice(Double.parseDouble(salePrice));
                update(orderinfoModel);
            } else {
                RegularorderdetailModel detailModel = new RegularorderdetailModel();
                detailModel.setOrderid(orderId);
                detailModel.setOrderstatus(ConstantVariable.ORDER_2);
                detailModel.setPaytype(ConstantVariable.CARD_PAY);
                detailModel.setSaleprice(salePrice);
                regularorderdetailService.updateOrderStatus(detailModel);
                RegularorderModel orderInfo = new RegularorderModel();
                orderInfo.setOrderno(orderno);
                orderInfo.setCardno(cardno);
                orderInfo.setPaytype(ConstantVariable.CARD_PAY);
                orderInfo.setPaytime(new Date());
                regularorderService.updateOrderByPay(orderInfo);
            }
        }
        return posData;
    }

    @Transactional
    public void deleteOrder(String userid, String orderno) {
        OrderinfoModel orderinfoModel = new OrderinfoModel();
        orderinfoModel.setOrderno(orderno);
        orderinfoModel.setUserid(userid);
        orderinfoModel = getModelById(orderinfoModel);
        if (null != orderinfoModel) {
            if (ConstantVariable.ORDER_READY_PAY.equals(orderinfoModel.getOrderstatus()) ||
                    ConstantVariable.ORDER_READY_EVAL.equals(orderinfoModel.getOrderstatus()) ||
                    ConstantVariable.ORDER_FINSH.equals(orderinfoModel.getOrderstatus())) {
                orderinfoMapper.insertDeleteOrder(new DeleteRecord(orderinfoModel.toString(), "order"));
                orderinfoMapper.deleteOrder(orderinfoModel);
            } else {
                throw new NullPointerException("请勿删除正在进行中的订单");
            }
        } else {
            throw new NullPointerException("查无此订单");
        }
    }


    public List<OrderInfoVO> getAppOrderList(OrderinfoModel orderModel) {
        List<OrderinfoModel> orderList = orderinfoMapper.getAppOrderList(orderModel);
        List<String> orderNoList = new ArrayList<>();
        List<OrderInfoVO> voList = new ArrayList<>();
        OrderdetailModel detailModel = new OrderdetailModel();
        for (OrderinfoModel orderInfo : orderList) {
            orderNoList.add(orderInfo.getOrderno());
        }
        orderModel.setIdList(orderNoList);
        if (orderNoList.size() == 0) {
            return voList;
        }
        List<OrderdetailModel> detailList = orderinfoMapper.getAppOrderPorductList(orderModel);
        for (OrderinfoModel orderInfo : orderList) {
            for (OrderdetailModel orderDetail : detailList) {
                if (orderDetail.getOrderno().equals(orderInfo.getOrderno())) {
                    List<OrderdetailModel> orderDetailList = orderInfo.getDetailList();
                    if (null == orderDetailList) {
                        orderDetailList = new ArrayList<>();
                        orderInfo.setDetailList(orderDetailList);
                    }
                    orderDetailList.add(orderDetail);
                }
            }
        }


        for (OrderinfoModel modelData : orderList) {
            voList.add(new OrderInfoVO(modelData));
        }
        return voList;
    }

    public void balanceByCard(String userid, String cardno, String orderno) {
        //
    }

    public OrderInfoVO getOrderDetail(OrderinfoModel orderInfoModel) {
        orderInfoModel = getModelById(orderInfoModel);
        String orderNo = orderInfoModel.getOrderno();
        OrderdetailModel detailModel = new OrderdetailModel();
        detailModel.setOrderno(orderNo);
        List<OrderdetailModel> detailList = orderdetailService.getList(detailModel);
        orderInfoModel.setDetailList(detailList);
        return new OrderInfoVO(orderInfoModel);
    }

    public List<OrderDetailVO> getOrderDetailList(OrderinfoModel orderInfoModel) {
        orderInfoModel = getModelById(orderInfoModel);
        String orderNo = orderInfoModel.getOrderno();
        OrderdetailModel detailModel = new OrderdetailModel();
        detailModel.setOrderno(orderNo);
        List<OrderdetailModel> detailList = orderdetailService.getList(detailModel);
        orderInfoModel.setDetailList(detailList);
        return new OrderInfoVO(orderInfoModel).getDetailList();
    }

    public String cancelOrder(OrderinfoModel orderinfoModel) {
        orderinfoModel = getModelById(orderinfoModel);
        if (null != orderinfoModel) {
            if (ConstantVariable.ORDER_READY_PAY.equals(orderinfoModel.getOrderstatus())) {
                orderinfoModel.setOrderstatus(ConstantVariable.ORDER_CANCEL);
                update(orderinfoModel);
                return "";
            } else {
                return "只能修改待支付的订单";
            }
        }
        return "查无此订单";
    }

    /**
     * @param orderModel
     * @param carid
     * @param isCar      1:购物车;2:商品
     */
    private void complateOrderModel(OrderinfoModel orderModel, String carid, String isCar) {
        String userid = orderModel.getUserid();
        CustomerModel customerModel = new CustomerModel();
        customerModel.setUid(userid);
        customerModel = customerService.getModelById(userid);
        String addressId = orderModel.getAddressid();
        if (StringUtils.isNotEmpty(addressId)) {
            UseraddressModel addressModel = new UseraddressModel();
            addressModel.setAddressid(orderModel.getAddressid());
            addressModel = addressService.getModelById(addressModel);
            orderModel.setReceiveaddress(addressModel.getFullAddress());
            orderModel.setReceivephone(addressModel.getReceivephone());
            orderModel.setNickname(addressModel.getReceivename());
            orderModel.setReceiveuser(addressModel.getReceivename());
        } else {
            orderModel.setReceivephone(customerModel.getPhoneno());
            orderModel.setNickname(customerModel.getNickname());
            orderModel.setReceiveuser(customerModel.getNickname());
        }
        if ("1".equals(isCar)) {
            setPriceByCar(orderModel, carid);
        } else {
            setPriceByProduct(orderModel, carid);
        }

        if ("3".equals(orderModel.getPaytype())) {
            orderModel.setSaleprice(1d);
        } else {
            orderModel.setSaleprice(orderModel.getOrderprice());
        }
        orderModel.setOrderstatus(ConstantVariable.ORDER_READY_PAY);
        orderModel.setPhoneno(customerModel.getPhoneno());
        orderModel.setCreatetime(new Date());
        String storeId = orderModel.getStoreid();
        StoreModel storeModel = new StoreModel();
        storeModel.setStoreid(storeId);
        storeModel = storeService.getModelById(storeModel);
        orderModel.setStorename(storeModel.getStorename());
    }

    private void setPriceByCar(OrderinfoModel orderModel, String carid) {
        List<String> carIdList = Arrays.asList(carid.split(","));
        Map<String, Integer> productCntMap = new HashMap<>();
        List<String> productIdList = new ArrayList<>();
        ShopcarModel carModel = new ShopcarModel();
        carModel.setIdList(carIdList);
        List<ShopcarModel> carList = shopcarService.getList(carModel);
        for (ShopcarModel cmodel : carList) {
            productIdList.add(cmodel.getProductid());
            productCntMap.put(cmodel.getProductid(), cmodel.getProductcount());
        }
        VegetableModel vmodel = new VegetableModel();
        vmodel.setIdList(productIdList);
        List<VegetableModel> vlist = vegetableService.getList(vmodel);
        BigDecimal orderPrice = new BigDecimal(0);
        for (VegetableModel product : vlist) {
            String vid = product.getVid();
            Integer productCnt = productCntMap.get(vid);
            BigDecimal productPrice = new BigDecimal(product.getPrice());
            String singlePrice = MathUtil.multiply(productCnt, productPrice);
            orderPrice = new BigDecimal(MathUtil.add(orderPrice, singlePrice));
        }
        orderModel.setOrderprice(Double.parseDouble(MathUtil.df.format(orderPrice)));
    }

    private void setPriceByProduct(OrderinfoModel orderModel, String productId) {
        List<String> productIdList = new ArrayList<>();
        productIdList.add(productId);
        VegetableModel vmodel = new VegetableModel();
        vmodel.setIdList(productIdList);
        List<VegetableModel> vlist = vegetableService.getList(vmodel);
        BigDecimal orderPrice = new BigDecimal(0);
        for (VegetableModel product : vlist) {
            String vid = product.getVid();
            orderPrice = new BigDecimal(product.getPrice());
        }
        orderModel.setOrderprice(Double.parseDouble(MathUtil.df.format(orderPrice)));
        orderModel.setSaleprice(0d);
    }

    public void createOrderByShopcar(OrderinfoModel orderModel, String carid) throws ValidatePosException {
        complateOrderModel(orderModel, carid, "1");
        ShopcarModel shopcarModel = new ShopcarModel();
        shopcarModel.setUserid(orderModel.getUserid());
        shopcarModel.noPage();
        List<ShopcarModel> carList = shopcarService.getList(shopcarModel);
        if (null == carList || 0 == carList.size()) {
            return;
        }

        Iterator<ShopcarModel> it = carList.iterator();
        while (it.hasNext()) {
            ShopcarModel carModel = it.next();
            if (carid.indexOf(carModel.getCarid()) == -1) {
                it.remove();
            }
        }
        List<String> idList = new ArrayList<>();
        Map<String, Integer> saleCountMap = new HashMap<>();
        for (ShopcarModel carModel : carList) {
            idList.add(carModel.getProductid());
            saleCountMap.put(carModel.getProductid(), carModel.getProductcount());
        }
        VegetableModel vegetableModel = new VegetableModel();
        vegetableModel.setIdList(idList);
        vegetableModel.noPage();

        List<VegetableModel> vegetableModelList = vegetableService.getList(vegetableModel);
        if (ConstantVariable.CARD_PAY.equals(orderModel.getCardno())) {
            PosCustomer posCustomer = getCardInfo(orderModel.getCardno());
            String orderPaidPrice = "0";
            for (VegetableModel vmodel : vegetableModelList) {
                OrderdetailModel detailModel = new OrderdetailModel();
                String allGoodSalePrice = MathUtil.multiply(saleCountMap.get(vmodel.getVid()), Double.parseDouble(vmodel.getPrice()));
                String allGoodPaidPric = MathUtil.discount(posCustomer.getCashPayRate(), allGoodSalePrice);
                orderPaidPrice = MathUtil.add(orderPaidPrice, allGoodPaidPric);
                detailModel.setVegetable(vmodel.getVid());
                detailModel.setVegetablename(vmodel.getVegetablename());
                detailModel.setProducecontent(vmodel.getContent());
                detailModel.setOrderno(orderModel.getOrderno());
                detailModel.setSalecount(saleCountMap.get(vmodel.getVid()));
                detailModel.setSaleprice(Double.parseDouble(MathUtil.df.format(Double.parseDouble(allGoodSalePrice))));
                detailModel.setPaidprice(MathUtil.df.format(Double.parseDouble(allGoodPaidPric)));
                detailModel.setImagesrc(vmodel.getThumimg());
                orderdetailService.insert(detailModel);
            }
            orderModel.setSaleprice(Double.parseDouble(MathUtil.df.format(Double.parseDouble(orderPaidPrice))));
            insert(orderModel);
            subCardPrice(orderModel);
        } else {
            for (VegetableModel vmodel : vegetableModelList) {
                OrderdetailModel detailModel = new OrderdetailModel();
                detailModel.setVegetable(vmodel.getVid());
                detailModel.setVegetablename(vmodel.getVegetablename());
                detailModel.setProducecontent(vmodel.getContent());
                detailModel.setOrderno(orderModel.getOrderno());
                detailModel.setSalecount(saleCountMap.get(vmodel.getVid()));
                detailModel.setSaleprice(saleCountMap.get(vmodel.getVid()) * Double.parseDouble(vmodel.getPrice()));
                detailModel.setImagesrc(vmodel.getThumimg());
                orderdetailService.insert(detailModel);
            }
            insert(orderModel);
        }
        List<String> carIdList = Arrays.asList(carid.split(","));
        shopcarService.batchDelete(carIdList);
    }

    public OrderinfoModel createOrderByProduct(OrderinfoModel orderModel, String vid) throws ValidatePosException {
        complateOrderModel(orderModel, vid, "2");
        VegetableModel vegetableModel = new VegetableModel();
        vegetableModel.setVid(vid);
        VegetableModel vmodel = vegetableService.getModelById(vegetableModel);
        OrderdetailModel detailModel = new OrderdetailModel();
        detailModel.setVegetable(vmodel.getVid());
        detailModel.setVegetablename(vmodel.getVegetablename());
        detailModel.setProducecontent(vmodel.getContent());
        detailModel.setOrderno(orderModel.getOrderno());
        detailModel.setSalecount(1);
        detailModel.setSaleprice(Double.parseDouble(vmodel.getPrice()));
        if (ConstantVariable.CARD_PAY.equals(orderModel.getPaytype())) {
            String cardNo = orderModel.getCardno();
            PosCustomer posCustomer = getCardInfo(cardNo);
            String paidprice = MathUtil.discount(posCustomer.getCashPayRate(), vmodel.getPrice());
            detailModel.setPaidprice(paidprice);
            orderModel.setSaleprice(Double.parseDouble(paidprice));
            subCardPrice(orderModel);
        } else {
            orderModel.setSaleprice(orderModel.getOrderprice());
            detailModel.setPaidprice(String.valueOf(orderModel.getOrderprice()));
        }
        detailModel.setImagesrc(vmodel.getImageList().get(0).getSrc());
        orderdetailService.insert(detailModel);
        insert(orderModel);
        return orderModel;
    }

    public void subCardPrice(OrderinfoModel orderinfoModel) throws ValidatePosException {
        String salePrice = String.valueOf(orderinfoModel.getSaleprice());
        String cardNo = orderinfoModel.getCardno();
        String remark = DateUtil.getTimeStr();
        PosData<PosCustomer> posData = posService.getInfoByCard(cardNo);
        PosCustomer posCustomer = posData.getResult(PosCustomer.class);
        String surplusCash = posCustomer.getPoint();
        Integer salePriceInt = Integer.parseInt(MathUtil.multiply(salePrice, "100"));
        Integer surplusCashInt = Integer.parseInt(surplusCash);
        Integer changeCash = surplusCashInt - salePriceInt;
        if (changeCash < 0) {
            throw new ValidatePosException("卡内余额不足");
        }
        posService.updateCard(posCustomer.getPhone(), cardNo, remark, String.valueOf(changeCash));
    }

    public ResultData getData(OrderinfoModel model) {
        ResultData returnData = new ResultData();
        try {
            returnData.setDataList(this.getList(model));
            int dataCount = this.getCount(model);
            returnData.setPageCount(dataCount, model.getPageSize());
            returnData.setDataCount(dataCount);
            returnData.setState(ConstantVariable.SUCCESS);
            returnData.setPageNumber(model.getPageNumber());
        } catch (Exception e) {
            returnData.setState(ConstantVariable.ERROR);
            returnData.setMsg(e.getMessage());
        }
        return returnData;
    }

    public OrderinfoModel getModelById(OrderinfoModel model) {
        List<OrderinfoModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<OrderInfoVO> getListToApp(OrderinfoModel model) {
        List<OrderinfoModel> modelList = orderinfoMapper.getList(model);
        List<OrderInfoVO> voList = new ArrayList<>();
        for (OrderinfoModel modelData : modelList) {
            voList.add(new OrderInfoVO(modelData));
        }
        return voList;
    }

    public List<OrderinfoModel> getList(OrderinfoModel model) {
        return orderinfoMapper.getList(model);
    }

    public int getCount(OrderinfoModel model) {
        return orderinfoMapper.getCount(model);
    }

    public OrderinfoModel insert(OrderinfoModel model) {
        orderinfoMapper.insert(model);
        return model;
    }

    public OrderinfoModel update(OrderinfoModel model) {
        orderinfoMapper.update(model);
        return model;
    }

    public List<OrderinfoModel> batchUpdate(List<OrderinfoModel> modelList) {
        for (OrderinfoModel model : modelList) {
            orderinfoMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(OrderinfoModel model) {
        orderinfoMapper.disableOrEnable(model);
    }

    public void delete(OrderinfoModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(model.getOrderid());
        model.setIdList(idList);
        orderinfoMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        OrderinfoModel model = new OrderinfoModel();
        model.setIdList(idList);
        orderinfoMapper.delete(model);
        return ResultData.toSuccessString();
    }

    private PosCustomer getCardInfo(String carNo) {
        PosUser userModel = new PosUser();
        userModel.setVip_no(carNo);
        PosData<PosCustomer> posData = posUtil.getMemberInfo(userModel);
        PosCustomer posCustomer = posData.getResult(PosCustomer.class);
        return posCustomer;
    }

}