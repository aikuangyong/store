package com.store.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.store.api.model.Regular.RegularVO;
import com.store.api.model.data.StoreVO;
import com.store.api.pos.ValidatePosException;
import com.store.dao.RegularMapper;
import com.store.dao.RegularcomponentMapper;
import com.store.model.*;
import com.store.model.config.ImageinfoModel;
import com.store.model.pos.PosCustomer;
import com.store.model.pos.PosData;
import com.store.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service("regularService")
public class RegularService {

    @Autowired
    private RegularMapper regularMapper;

    @Autowired
    private PosUserService posUserService;

    @Autowired
    private RegularHistoryService regularHistoryService;

    @Autowired
    private VegetablelinkService vegetablelinkService;

    @Autowired
    private ProductsuborderlinkService productsuborderlinkService;

    @Autowired
    private UseraddressService addressService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private RegularService regularService;

    @Autowired
    private ConfigtblService configtblService;

    @Autowired
    private ImageinfoService imageService;

    @Autowired
    private SendtimeconfigService sendtimeconfigService;

    @Autowired
    private RegularcomponentMapper regularcomponentMapper;

    @Autowired
    private RegularorderService regularorderService;

    @Autowired
    private RegularorderdetailService regularorderdetailService;

    @Autowired
    private RegularsuborderService regularsuborderService;

    @Autowired
    private ProductsuborderlinkService linkService;

    @Autowired
    private VegetableService vegetableService;

    @Autowired
    private ExcelUtil<RegularModel> excelUtil;

    @Transactional
    public void addSuborder(String suborderid, String vid) {
        ProductsuborderlinkModel model = new ProductsuborderlinkModel();
        model.setSuborderid(suborderid);
        RegularsuborderModel regularsuborderModel = new RegularsuborderModel();
        VegetableModel vegetableModel = new VegetableModel();
        vegetableModel.setVid(vid);
        vegetableModel = vegetableService.getModelById(vegetableModel);
        if (vegetableModel != null) {
            regularsuborderModel.setProductname(vegetableModel.getVegetablename());
        } else {
            regularsuborderModel.setProductname("");
        }
        regularsuborderModel.setSuborderno(suborderid);
        regularsuborderModel.setSuborderid(suborderid);
        ProductsuborderlinkModel existsModel = linkService.getModelById(model);
        regularsuborderService.updateSubOrder(regularsuborderModel);
        if (null == existsModel) {
            model.setCreatetime(new Date());
            model.setVid(vid);
            linkService.insert(model);
        } else {
            model.setId(existsModel.getId());
            model.setCreatetime(new Date());
            model.setVid(vid);
            linkService.update(model);
        }
    }

    public void cancelRegular(String user) {

    }

    public Map<String, List<RegularsuborderModel>> getUserMealPackage(RegularOrderInfo orderInfo) {
        List<RegularOrderInfo> userMealList = getRegularOrderInfo(orderInfo);
        RegularsuborderModel regularsuborderModel = new RegularsuborderModel();
        regularsuborderModel.setUserid(orderInfo.getUserid());
        regularsuborderModel.setCoditionDay(DateUtil.getDay());
        regularsuborderModel.noPage();
        List<RegularsuborderModel> subOrderList = regularsuborderService.getList(regularsuborderModel);
        Map<String, List<RegularsuborderModel>> returnHash = new HashMap<>();
        for (RegularsuborderModel suborder : subOrderList) {
            Iterator<RegularOrderInfo> it = userMealList.iterator();
            while (it.hasNext()) {
                RegularOrderInfo userOrder = it.next();
                if (suborder.getRegularno().equals(userOrder.getOrderno()) && suborder.getSendtime().equals(userOrder.getConfigdate())) {
                    List<RegularOrderInfo> orderList = suborder.getUserOrderList();
                    if (null == orderList) {
                        orderList = new ArrayList<>();
                        suborder.setUserOrderList(orderList);
                    }
                    orderList.add(userOrder);
                    it.remove();
                }
            }
        }
        for (RegularsuborderModel suborder : subOrderList) {
            String key = suborder.getRegularno();
            List<RegularsuborderModel> orderList = returnHash.get(key);
            if (null == orderList) {
                orderList = new ArrayList<>();
                returnHash.put(key, orderList);
            }
            orderList.add(suborder);
        }
        return returnHash;
    }

    public List<UserRegularOrderInfo> getRegularOrderList(UserRegularOrderInfo orderInfo) {
        return regularMapper.getRegularOrderList(orderInfo);
    }

    public List<RegularOrderInfo> getRegularOrderInfo(RegularOrderInfo orderInfo) {
        return regularMapper.getRegularOrderInfo(orderInfo);
    }


    public boolean editRegularOrderInfo(RegularsuborderModel model, String edittype, String editinfo) {
        model.noPage();
        List<RegularsuborderModel> subList = regularsuborderService.getList(model);
        if (null == subList || subList.size() == 0) {
            return false;
        }
        String sendTime = model.getSendtime();
        model = subList.get(0);
        RegularorderModel regularorderModel = new RegularorderModel();
        regularorderModel.setOrderno(model.getRegularno());
        regularorderModel = regularorderService.getModelById(regularorderModel);
        RegularorderdetailModel detailModel = new RegularorderdetailModel();
        detailModel.setOrderid(regularorderModel.getOrderid());
        detailModel = regularorderdetailService.getModelById(detailModel);
        if ("1".equals(edittype)) {
            RegularorderModel updateOrder = new RegularorderModel();
            updateOrder.setOrderid(regularorderModel.getOrderid());
            updateOrder.setAddressid(editinfo);
            regularorderService.update(regularorderModel);
            //地址
            UseraddressModel addressModel = new UseraddressModel();
            addressModel.setAddressid(editinfo);
            addressModel = addressService.getModelById(addressModel);
            String addressDetail = addressModel.getFullAddress();
            detailModel.setReceiveaddress(addressDetail);
            detailModel.setNickname(addressModel.getReceivename());
            detailModel.setPhoneno(addressModel.getReceivephone());
            regularorderdetailService.update(detailModel);
            regularorderModel.setAddressid(addressModel.getAddressid());
            regularorderModel.setNickname(addressModel.getReceivename());
            regularorderModel.setPhoneno(addressModel.getReceivephone());
            regularorderService.update(regularorderModel);
            model.setSendtime(sendTime);
            regularsuborderService.batchUpdate(model);
        } else if ("2".equals(edittype)) {
            //时段
            SendtimeconfigModel timeModel = new SendtimeconfigModel();
//            timeModel.setGroupseq(editinfo);
            timeModel.setId(Integer.parseInt(editinfo + ""));
            timeModel.noPage();
            timeModel = sendtimeconfigService.getModelById(timeModel);
            detailModel.setTimestage(timeModel.getId() + "");
            detailModel.setTimestagetext(timeModel.getShowtext());
            regularorderdetailService.update(detailModel);
            String[] timeStage = timeModel.getShowtext().split("-");
            if (timeStage.length > 0) {
                model.setBeginstagetime(timeStage[0]);
                model.setEndstagetime(timeStage[1]);
            } else {
                model.setBeginstagetime(timeModel.getShowtext());
                model.setEndstagetime(timeModel.getEndtime());
            }
            model.setSendtime(sendTime);
            regularsuborderService.batchUpdate(model);
        }
        return true;
    }

    public void cancelSuborder(RegularsuborderModel suborderModel) {
        suborderModel.noPage();
        List<RegularsuborderModel> subList = regularsuborderService.getList(suborderModel);
        RegularsuborderModel subModel = new RegularsuborderModel();
        subModel.setUserid(suborderModel.getUserid());
        subModel.noPage();
        List<RegularsuborderModel> subOrderList = regularsuborderService.getList(subModel);
        suborderModel = subList.get(0);
        String orderno = suborderModel.getRegularno();
        RegularorderModel orderModel = new RegularorderModel();
        orderModel.setOrderno(orderno);
        orderModel = regularorderService.getModelById(orderModel);
        RegularorderdetailModel detailModel = new RegularorderdetailModel();
        detailModel.setOrderid(orderModel.getOrderid());
        detailModel = regularorderdetailService.getModelById(detailModel);
        cancelSubOrder(suborderModel, detailModel, subOrderList);
    }

    private void cancelSubOrder(RegularsuborderModel suborderModel, RegularorderdetailModel detailModel, List<RegularsuborderModel> subList) {
        //获取子订单的最后一天,然后拿到日期,计算出来延后那一天的日期,然后把取消那一天的日期修改成目标日期
        RegularsuborderModel lastModel = subList.get(subList.size() - 1);
        String sendDay = detailModel.getSendday();
        String weeknum = detailModel.getSendweek();

        RegularDate regularDate = new RegularDate();
        regularDate.setCoditionDay(lastModel.getSendtime());
        List<RegularDate> dateList = regularMapper.getRegularDate(regularDate);
        RegularDate firstDate = dateList.get(0);
        String nextWeek = "", nextDate = "";
        if ("4".equals(sendDay)) {
            String[] sendWeekArr = (weeknum + "," + weeknum).split(",");
            String firstWeek = firstDate.getWeeknum();
            for (int i = 0; i < sendWeekArr.length; i++) {
                if (sendWeekArr[i].equals(firstWeek)) {
                    nextWeek = sendWeekArr[++i];
                    break;
                }
            }
            for (int i = 0; i < dateList.size(); i++) {
                RegularDate date = dateList.get(i);
                if (date.getWeeknum().equals(nextWeek)) {
                    nextDate = date.getDateinfo();
                    break;
                }
            }
        } else {
            Integer dayCnt = Integer.parseInt(sendDay);
//            Set<String> existsDay = new HashSet<>();
//            for (int i = 0; i < dateList.size(); i++) {
//                RegularDate date = dateList.get(i);
//                existsDay.add(date.getDateinfo());
//            }

            for (int i = 0; i < dateList.size(); ) {
                i += dayCnt;
                RegularDate date = dateList.get(i);
                nextDate = date.getDateinfo();
                break;
            }
        }
        lastModel.setSendtime(nextDate);
        lastModel.setSuborderid(EncryptUtils.md5(UUID.randomUUID().toString()));
        lastModel.setRegularno(suborderModel.getRegularno());
        regularsuborderService.insert(lastModel);
        suborderModel.setStatus("4");
        regularsuborderService.update(suborderModel);
    }

    @Transactional
    public String createRegularOrder(RegularorderModel model) {
        //查看用户最后一天消费定期购的日期,如果有就返回下一天的日期,如果没有,就返回空
        try {
            returnUserRegularOrderDate(model);
            if ("3".equals(model.getPaytype())) {
                calcCardPayPrice(model);
            }
            this.createRegularMainOrder(model);
            this.createRegularDetailOrder(model.getRegularorderdetailModel());
            this.createRegularSubOrder(model);
            if ("3".equals(model.getPaytype())) {
                subCardPrice(model);
            }
        } catch (ValidatePosException e) {
            return "卡内余额不足";
        } catch (Exception e) {
            return "系统异常";
        }
        return "";
    }

    public void subCardPrice(RegularorderModel model) throws ValidatePosException {
        String salePrice = model.getRegularorderdetailModel().getSaleprice();
        String cardNo = model.getCardno();
        String remark = DateUtil.getTimeStr();
        PosData<PosCustomer> posData = posUserService.getInfoByCard(cardNo);
        PosCustomer posCustomer = posData.getResult(PosCustomer.class);
        String surplusCash = posCustomer.getPoint();
        Integer salePriceInt = Integer.parseInt(MathUtil.multiply(salePrice, "100"));
        Integer surplusCashInt = Integer.parseInt(surplusCash);
        Integer changeCash = surplusCashInt - salePriceInt;
        if (changeCash < 0) {
            throw new ValidatePosException("卡内余额不足");
        }
        posUserService.updateCard(posCustomer.getPhone(), cardNo, remark, String.valueOf(changeCash));
    }

    public void calcCardPayPrice(RegularorderModel model) {
        String cardNo = model.getCardno();
        PosData<PosCustomer> posData = posUserService.getInfoByCard(cardNo);
        String cashPayRate = String.valueOf(posData.getResult(PosCustomer.class).getCashPayRate());
        Double orderPrice = model.getRegularorderdetailModel().getOrderprice();
        String orderPrice_fee = ConstantVariable.CENTS_FORMAT(MathUtil.multiply(orderPrice, 100));
        String cashRate = MathUtil.divide(cashPayRate, 100);
        String salePrice = ConstantVariable.CENTS_FORMAT(MathUtil.multiply(orderPrice_fee, cashRate));
        salePrice = MathUtil.divide(salePrice, 100);
        model.getRegularorderdetailModel().setSaleprice(salePrice);
    }

    public static void main(String[] args) {
        System.out.println(MathUtil.divide(80, 100));
        System.out.println(MathUtil.multiply(1, MathUtil.divide(80, 100)));
        System.out.println(ConstantVariable.CENTS_FORMAT(Double.parseDouble(MathUtil.multiply(1, 0.4))));
        System.out.println(MathUtil.divide(1, 100));
    }

    public void returnUserRegularOrderDate(RegularorderModel model) {
        RegularsuborderModel suborderModel = new RegularsuborderModel();
        suborderModel.setUserid(model.getUserid());
        List<RegularsuborderModel> subList = regularsuborderService.getMaxSendTimeByUserid(suborderModel);
        if (CollectionUtils.isNotEmpty(subList)) {
            model.setSendstarttime(subList.get(0).getSendtime());
        }
    }

    public void createRegularSubOrder(RegularorderModel model) {
        RegularorderdetailModel detailOrder = model.getRegularorderdetailModel();
        detailOrder.setPhoneno(model.getPhoneno());
        detailOrder.setNickname(model.getNickname());
        String sendday = model.getRegularorderdetailModel().getSendday();
        Integer sendcount = model.getRegularorderdetailModel().getSendcount();
        String daynum = model.getRegularorderdetailModel().getSendweek();
        String tomorrowDay = model.getSendstarttime();//DateUtil.sdfDay.format(tomorrowDate);

        RegularDate dateModel = new RegularDate();
        dateModel.setCoditionDay(tomorrowDay);

        List<RegularDate> dateList = regularMapper.getRegularDate(dateModel);
        Map<String, VegetablelinkModel> dataProductLinkModel = vegetablelinkService.getAllRegularByMapProduct(null);

        List<RegularsuborderModel> subOrderList = new ArrayList<>();
        //如果是自选日期,按照daynum来算出子订单的配送日期
        if ("4".equals(sendday)) {
            String[] weekArr = daynum.split(ConstantVariable.SPLIT_COMMA);
            for (int i = 0, weeknum = 0, index = 0; i < sendcount && index < dateList.size(); index++) {
                RegularDate dateObj = dateList.get(index);
                String weeknumber = dateList.get(index).getWeeknum();
                if (ArrayUtils.contains(weekArr, dateObj.getWeeknum())) {
                    i++;
                    RegularsuborderModel subOrder = new RegularsuborderModel(model);
                    subOrder.setSendtime(dateObj.getDateinfo());
                    subOrder.setStatus(ConstantVariable.SUB_ORDER_READY);
                    subOrder.setStid(detailOrder.getStoreid());
                    subOrderList.add(subOrder);
                }
            }
        } else {
            int sendDayCnt = Integer.parseInt(sendday);
            for (int i = 0, index = 0; index < sendcount; i += sendDayCnt, index++) {
                RegularDate dateObj = dateList.get(i);
                RegularsuborderModel subOrder = new RegularsuborderModel(model);
                subOrder.setSendtime(dateObj.getDateinfo());
                subOrder.setStid(detailOrder.getStoreid());
                subOrder.setStatus(ConstantVariable.SUB_ORDER_READY);
                subOrderList.add(subOrder);
            }
        }

        List<ProductsuborderlinkModel> linkModelList = new ArrayList<>();
        for (RegularsuborderModel suborderModel : subOrderList) {
            VegetablelinkModel vegetablelinkModel = dataProductLinkModel.get(suborderModel.getSendtime());
            if (null != vegetablelinkModel) {
                ProductsuborderlinkModel subLinkModel = new ProductsuborderlinkModel();
                subLinkModel.setVid(vegetablelinkModel.getProductid());
                subLinkModel.setSuborderid(suborderModel.getSuborderid());
                subLinkModel.setCreatetime(new Date());
                suborderModel.setProductname(vegetablelinkModel.getProductname());
                linkModelList.add(subLinkModel);
            }
        }

        regularsuborderService.batchInsert(subOrderList);
        if (CollectionUtils.isNotEmpty(linkModelList)) {
            productsuborderlinkService.batchInsert(linkModelList);
        }
    }


    public void setSubOrderDate(RegularorderdetailModel subOrder, RegularDate regularDate) {
        Calendar cal = Calendar.getInstance();
        String[] dateArr = regularDate.getDateinfo().split(",");
        cal.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]));
        subOrder.setStartsendtime(cal.getTime());
        subOrder.setRegularDate(regularDate);
    }

    public void createRegularDetailOrder(RegularorderdetailModel model) {
        regularorderdetailService.insert(model);
    }

    /**
     * 创建主订单
     *
     * @param model
     * @return
     */
    public void createRegularMainOrder(RegularorderModel model) {
        RegularorderdetailModel detailModel = model.getRegularorderdetailModel();
        RegularModel regularModel = new RegularModel();
        regularModel.setRid(model.getRegularid());
        regularModel = regularService.getModelById(regularModel);
        UseraddressModel addressModel = new UseraddressModel();
        addressModel.setAddressid(model.getAddressid());
        addressModel = addressService.getModelById(addressModel);
        StoreModel storeModel = new StoreModel();
        List<StoreVO> voList = storeService.getListToAppWidth(storeModel, addressModel.getLng(), addressModel.getLat());
        StoreVO storeVO = voList.get(0);
        detailModel.setStoreid(storeVO.getStoreid());
        model.setProductname(regularModel.getRegularname());
        model.setSpecification(regularModel.getContent());
        model.setNickname(addressModel.getReceivename());
        detailModel.setNickname(addressModel.getReceivename());
        detailModel.setContent(regularModel.getContent());
        detailModel.setPhoneno(addressModel.getReceivephone());
        detailModel.setOrderprice(model.getPrice());
        detailModel.setSurplus(detailModel.getSendcount());
        Calendar cal = Calendar.getInstance();
        String[] dateArr = model.getSendstarttime().split("-");
        Integer year = Integer.parseInt(dateArr[0]);
        Integer month = Integer.parseInt(dateArr[1]) - 1;
        Integer date = Integer.parseInt(dateArr[2]);
        cal.set(year, month, date);
        detailModel.setStartsendtime(cal.getTime());
        model.setPhoneno(addressModel.getReceivephone());
        String timeStage = model.getRegularorderdetailModel().getTimestage();
        SendtimeconfigModel configModel = new SendtimeconfigModel();
        configModel.setId(Integer.parseInt(timeStage));
        configModel = sendtimeconfigService.getModelById(configModel);
        model.getRegularorderdetailModel().setTimestagetext(configModel.getShowtext());
        detailModel.setReceiveaddress(addressModel.getFullAddress());
        //getRegularPrice
        setRegularPrice(regularModel, model);
        if (!"3".equals(model.getPaytype())) {
            model.getRegularorderdetailModel().setSaleprice(MathUtil.df.format(detailModel.getOrderprice()));
        }
        regularorderService.insert(model);
        detailModel.setOrderid(model.getOrderid());
    }

    private void setRegularPrice(RegularModel regularModel, RegularorderModel orderModel) {
        Map<String, String> regularParam = getRegularInfo(regularModel);
        RegularorderdetailModel detailModel = orderModel.getRegularorderdetailModel();
        String key = detailModel.getUnit() + ConstantVariable.SPLIT + detailModel.getSendcount();
        String orderPrice = regularParam.get(key);
        if (null == orderPrice) {
            throw new NullPointerException("价格为空");
        }
        detailModel.setOrderprice(Double.parseDouble(orderPrice));
    }

    public Map<String, Object> getRegularDetail(String rid) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        //定期购单位
        returnMap.put("util", getUtil(rid));
        //定期购配送模式
        returnMap.put("sendday", getSendDay());
        //配送次数
        returnMap.put("sendcount", getSendCount(rid));
        SendtimeconfigModel sendtimeconfigModel = new SendtimeconfigModel();
        sendtimeconfigModel.setOrderColumn("groupseq");
        sendtimeconfigModel.setOrderType("asc");
        sendtimeconfigModel.noPage();
        List<SendtimeconfigModel> configList = sendtimeconfigService.getList(sendtimeconfigModel);
        //定期购配送信息
        returnMap.put("sendtimestage", configList);
        RegularModel model = new RegularModel();
        model.setRid(rid);
        model = getModelById(model);
        //定期购价格
        returnMap.put("priceinfo", getRegularInfo(model));
        model.getComponentList().clear();
        //定期购信息
        returnMap.put("regularinfo", new RegularVO(model));
        //运费信息
        returnMap.put("sendinfo", getSendCostConfig());
        //配送范围
        returnMap.put("sendstage", getSendStage());
        return returnMap;
    }

    public Map<String, Object> getConfig() {
        Map<String, Object> costConfig = getSendCostConfig();
        String stageInfo = getSendStage();
        costConfig.put("sendstage", stageInfo);
        costConfig.put("sendtimestage", sendtimeconfigService.getAllList());
        return costConfig;
    }


    public String getSendStage() {
        ConfigtblModel configModel = new ConfigtblModel();
        configModel.setDatatype("sendRange");
        return configtblService.getModelById(configModel).getComments();
    }

    public Map<String, Object> getSendCostConfig() {
        ConfigtblModel configModel = new ConfigtblModel();
        configModel.setDatatype("sendCost");
        String sendInfoStr = configtblService.getModelById(configModel).getComments();
        return JSON.parseObject(sendInfoStr, HashMap.class);
    }

    public Map<String, Object> getConfig(RegularModel model) {
        Map<String, Object> returnMap = new HashMap<>();
        List<String> priceList = new ArrayList<>();
        ConfigtblModel configModel = new ConfigtblModel();
        configModel.setDatatype("sendCost");
        String sendInfoStr = configtblService.getModelById(configModel).getComments();
        Map<String, Object> sendInfoMap = JSON.parseObject(sendInfoStr, HashMap.class);
        //运费信息
        returnMap.put("sendinfo", sendInfoMap);
        configModel.setDatatype("sendRange");
        //配送范围
        returnMap.put("sendstage", configtblService.getModelById(configModel).getComments());
        return returnMap;
    }

    public Map<String, String> getRegularInfo(RegularModel model) {
        List<RegularcomponentModel> componentList = model.getComponentList();
        Map<String, String> positionMap = new HashMap<>();
        Map<String, String> salePriceMap = new HashMap<>();
        for (RegularcomponentModel componentModel : componentList) {
            positionMap.put(componentModel.getRow() + ConstantVariable.SPLIT + componentModel.getCol(), componentModel.getVal());
        }

        for (Map.Entry<String, String> entry : positionMap.entrySet()) {
            String key = entry.getKey();
            String priceStr = entry.getValue();
            String[] positionArr = key.split(ConstantVariable.SPLIT);
            String row = positionArr[0];
            String col = positionArr[1];
            if (!"1".equals(row) && !"1".equals(col)) {
                String unit = positionMap.get(row + ConstantVariable.SPLIT + "1");
                String count = positionMap.get(1 + ConstantVariable.SPLIT + col);
                salePriceMap.put(unit + ConstantVariable.SPLIT + count, priceStr);
            }
        }
        return salePriceMap;
    }

    public List<Map<String, Object>> getSendDay() {
        List<Map<String, Object>> returnList = new ArrayList<>();
        List<SendDay> sendDayList = regularMapper.getSendDay();
        for (SendDay sendDay : sendDayList) {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("key", sendDay.getFieldKey());
            returnMap.put("val", sendDay.getFieldValue());
            returnList.add(returnMap);
        }
        return returnList;
    }

    private List<Map<String, Object>> getSendCount(String rid) {
        RegularcomponentModel regularcomponentModel = new RegularcomponentModel();
        regularcomponentModel.noPage();
        regularcomponentModel.setRow("1");
        regularcomponentModel.setRid(rid);
        regularcomponentModel.setOrderColumn("val");
        regularcomponentModel.setOrderType("asc");
        List<RegularcomponentModel> regularComponentList = regularcomponentMapper.getList(regularcomponentModel);
        List<Map<String, Object>> returnList = new ArrayList<>();
        for (RegularcomponentModel model : regularComponentList) {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("key", model.getVal());
            returnMap.put("val", model.getVal() + "次");
            returnList.add(returnMap);
        }
        return returnList;
    }

    private List<Map<String, Object>> getUtil(String rid) {
        RegularcomponentModel regularcomponentModel = new RegularcomponentModel();
        regularcomponentModel.noPage();
        regularcomponentModel.setRid(rid);
        regularcomponentModel.setCol("1");
        regularcomponentModel.setOrderColumn("val");
        regularcomponentModel.setOrderType("asc");
        List<RegularcomponentModel> regularComponentList = regularcomponentMapper.getList(regularcomponentModel);
        List<Map<String, Object>> returnList = new ArrayList<>();
        for (RegularcomponentModel model : regularComponentList) {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("key", model.getVal());
            returnMap.put("val", model.getVal() + "g");
            returnList.add(returnMap);
        }
        return returnList;
    }

    public XSSFWorkbook export(RegularModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<RegularModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(RegularModel model) {
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

    public RegularModel getModelById(RegularModel model) {
        model.noPage();
        List<RegularModel> dataList = getList(model);
        if (dataList.size() > 0) {
            RegularModel regularModel = dataList.get(0);
            RegularcomponentModel regularcomponentModel = new RegularcomponentModel();
            regularcomponentModel.noPage();
            regularcomponentModel.setRid(regularModel.getRid());
            List<RegularcomponentModel> componentList = regularcomponentMapper.getList(regularcomponentModel);
            regularModel.setComponentList(componentList);
            return regularModel;
        }
        return null;
    }

    public List<RegularModel> getList(RegularModel model) {
        List<RegularModel> regularModelList = regularMapper.getList(model);
        ImageinfoModel imageModel = new ImageinfoModel();
        List<String> idList = new ArrayList<>();
        imageModel.setImgGrpList(idList);
        for (RegularModel regularModel : regularModelList) {
            idList.add(regularModel.getRegularimg());
            regularModel.setImagelist(new ArrayList<>());
        }
        List<ImageinfoModel> imgList = imageService.getList(imageModel);
        for (RegularModel regularModel : regularModelList) {
            Iterator<ImageinfoModel> it = imgList.iterator();
            while (it.hasNext()) {
                ImageinfoModel imageinfoModel = it.next();
                if (regularModel.getRegularimg().equals(imageinfoModel.getImggrp())) {
                    regularModel.getImagelist().add(imageinfoModel);
                    it.remove();
                }
            }
        }
        return regularModelList;
    }

    public int getCount(RegularModel model) {
        return regularMapper.getCount(model);
    }

    @Transactional
    public RegularModel insert(RegularModel model) {
        addRegularComponent(model);
        regularMapper.insert(model);
        return model;
    }

    private void addRegularComponent(RegularModel model) {
        String component = model.getComponent();
        List<RegularcomponentModel> rcList = JSONArray.parseArray(component).toJavaList(RegularcomponentModel.class);
        model.setComponentList(rcList);
        model.setComponent("");
        for (RegularcomponentModel rcModel : model.getComponentList()) {
            rcModel.setRid(model.getRid());
            rcModel.setCreatetime(DateUtil.getDate());
            regularcomponentMapper.insert(rcModel);
        }
    }

    @Transactional
    public RegularModel update(RegularModel model) {
        RegularcomponentModel regularcomponentModel = new RegularcomponentModel();
        regularcomponentModel.setRid(model.getRid());
        regularcomponentMapper.deleteByRid(regularcomponentModel);
        addRegularComponent(model);
        regularMapper.update(model);
        return model;
    }

    public List<RegularModel> batchUpdate(List<RegularModel> modelList) {
        for (RegularModel model : modelList) {
            regularMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(RegularModel model) {
        regularMapper.disableOrEnable(model);
    }

    public void delete(RegularModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(model.getRid());
        model.setIdList(idList);
        regularMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        RegularModel model = new RegularModel();
        model.setIdList(idList);
        regularMapper.delete(model);
        return ResultData.toSuccessString();
    }

    public void saveRegularInfo(RegularorderModel regularorderModel) {
        String orderNo = regularorderModel.getOrderno();
        String orderInfo = JSON.toJSONString(regularorderModel);
        RegularHistoryModel regularHistoryModel = new RegularHistoryModel();
        regularHistoryModel.setOrderno(orderNo);
        regularHistoryModel.setOrderinfo(orderInfo);
        regularHistoryService.insert(regularHistoryModel);
    }
}