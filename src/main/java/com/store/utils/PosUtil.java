package com.store.utils;

import com.alibaba.fastjson.JSON;
import com.store.model.ConfigtblModel;
import com.store.model.pos.*;
import com.store.service.ConfigtblService;
import javafx.geometry.Pos;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.Null;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PosUtil {

    private static final Logger LOGGER = Logger.getLogger(PosUtil.class);

    public static final String PHONE = "13961618173";
    public static final String CARD_NO = "MEMB20180810000004";

    @Autowired
    private ConfigtblService configService;


    public PosData<PosConsume> getPaidConsumeLog(PosConsume posConsume) {
        try {
            String ts = DateUtil.getTimeStr();
            PosModel posModel = getSign(ts);
            Map<String, String> param = new HashMap<>();
            param.put("appid", posModel.getAppid());
            param.put("ts", ts);
            param.put("v", posModel.getVersion());
            param.put("sign", posModel.getSign());
            param.put("model", "customer");
            param.put("method", "getPaidConsumeLog");
            param.put("msg", JSON.toJSONString(posConsume));
            String[] urlParamArr = new String[param.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : param.entrySet()) {
                urlParamArr[i++] = entry.getKey() + "=" + entry.getValue();
            }
            String urlParam = StringUtils.join(urlParamArr, "&");
            LOGGER.info("updateMemberInfo-urlParam" + urlParam);
            String responseStr = HttpRequest.sendPost(posModel.getRequestUrl(), urlParam, null);
            LOGGER.info("updateMemberInfo-responseStr" + responseStr);
            PosData<PosConsume> posData = JSON.parseObject(responseStr, PosData.class);
            return PosData.toReturn(posData);
        } catch (Exception e) {
            e.printStackTrace();
            return PosData.error(e.getMessage());
        }
    }


    /**
     * 门店编号:storeCode;手机号:mobile;订单编号:orderno;金额:price(负数为退款);备注信息:remark;oldOrderNo:原订单编号(退款输入原订单编号);
     *
     * @param posConsume
     * @return
     */
    public PosData<PosConsume> paidConsume(PosConsume posConsume) {
        try {
            String ts = DateUtil.getTimeStr();
            PosModel posModel = getSign(ts);
            Map<String, String> param = new HashMap<>();
            param.put("appid", posModel.getAppid());
            param.put("ts", ts);
            param.put("v", posModel.getVersion());
            param.put("sign", posModel.getSign());
            param.put("model", "customer");
            param.put("method", "paidConsume");
            param.put("msg", JSON.toJSONString(posConsume));
            String[] urlParamArr = new String[param.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : param.entrySet()) {
                urlParamArr[i++] = entry.getKey() + "=" + entry.getValue();
            }
            String urlParam = StringUtils.join(urlParamArr, "&");
            LOGGER.info("updateMemberInfo-urlParam" + urlParam);
            String responseStr = HttpRequest.sendPost(posModel.getRequestUrl(), urlParam, null);
            LOGGER.info("updateMemberInfo-responseStr" + responseStr);
            PosData<PosConsume> posData = JSON.parseObject(responseStr, PosData.class);
            return PosData.toReturn(posData);
        } catch (Exception e) {
            e.printStackTrace();
            return PosData.error(e.getMessage());
        }
    }

    /**
     * user_id 对应 uid，phoneno对应phone，vip_no和vip_offline_no 都是卡号
     *
     * @param userModel
     * @return
     */
    public PosData<PosUser> updateMemberInfo(PosUser userModel) {
        try {
            String ts = DateUtil.getTimeStr();
            PosModel posModel = getSign(ts);
            Map<String, String> param = new HashMap<>();
            param.put("appid", posModel.getAppid());
            param.put("ts", ts);
            param.put("v", posModel.getVersion());
            param.put("sign", posModel.getSign());
            param.put("model", "customer");
            param.put("method", "update");
            param.put("msg", JSON.toJSONString(userModel));
            String[] urlParamArr = new String[param.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : param.entrySet()) {
                urlParamArr[i++] = entry.getKey() + "=" + entry.getValue();
            }
            String urlParam = StringUtils.join(urlParamArr, "&");
            LOGGER.info("updateMemberInfo-urlParam" + urlParam);
            String responseStr = HttpRequest.sendPost(posModel.getRequestUrl(), urlParam, null);
            LOGGER.info("updateMemberInfo-responseStr" + responseStr);
            return JSON.parseObject(responseStr, PosData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return PosData.error(e.getMessage());
        }
    }

    public PosData payInfo(PosPay posPay) {
        try {
            String ts = DateUtil.getTimeStr();
            PosModel posModel = getSign(ts);
            Map<String, String> param = new HashMap<>();
            param.put("appid", posModel.getAppid());
            param.put("ts", ts);
            param.put("v", posModel.getVersion());
            param.put("sign", posModel.getSign());
            param.put("model", "paidConsume");
            param.put("method", "get");
            param.put("msg", JSON.toJSONString(posPay));
            String[] urlParamArr = new String[param.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : param.entrySet()) {
                urlParamArr[i++] = entry.getKey() + "=" + entry.getValue();
            }
            String urlParam = StringUtils.join(urlParamArr, "&");
            LOGGER.info("getMemberInfo-urlParam" + urlParam);
            String responseStr = HttpRequest.sendPost(posModel.getRequestUrl(), urlParam, null);
            LOGGER.info("getMemberInfo-responseStr" + responseStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PosStockRealtime> getStockRealtime(PosStockRealtime stockRealtime) {
        try {
            String ts = DateUtil.getTimeStr();
            PosModel posModel = getSign(ts);
            Map<String, String> param = new HashMap<>();
            param.put("appid", posModel.getAppid());
            param.put("ts", ts);
            param.put("v", posModel.getVersion());
            param.put("sign", posModel.getSign());
            param.put("model", "goods");
            param.put("method", "stockRealtime");
            param.put("msg", JSON.toJSONString(stockRealtime));
            String[] urlParamArr = new String[param.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : param.entrySet()) {
                urlParamArr[i++] = entry.getKey() + "=" + entry.getValue();
            }
            String urlParam = StringUtils.join(urlParamArr, "&");
            LOGGER.info("getMemberInfo-urlParam" + urlParam);
            String responseStr = HttpRequest.sendPost(posModel.getRequestUrl(), urlParam, null);
            LOGGER.info("getMemberInfo-responseStr" + responseStr);
            PosData<PosStockRealtime> posData = JSON.parseObject(responseStr, PosData.class);
            if (PosData.ERROR.equals(posData.getCode())) {
                LOGGER.error(posData.getMessage());
                return null;
            }
            return posData.getResultList(PosStockRealtime.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PosData<PosCustomer> getMemberInfo(PosUser userModel) {
        try {
            String ts = DateUtil.getTimeStr();
            PosModel posModel = getSign(ts);
            Map<String, String> param = new HashMap<>();
            param.put("appid", posModel.getAppid());
            param.put("ts", ts);
            param.put("v", posModel.getVersion());
            param.put("sign", posModel.getSign());
            param.put("model", "customer");
            param.put("method", "get");
            param.put("msg", JSON.toJSONString(userModel));
            String[] urlParamArr = new String[param.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : param.entrySet()) {
                urlParamArr[i++] = entry.getKey() + "=" + entry.getValue();
            }
            String urlParam = StringUtils.join(urlParamArr, "&");
            LOGGER.info("getMemberInfo-urlParam" + urlParam);
            String responseStr = HttpRequest.sendPost(posModel.getRequestUrl(), urlParam, null);
            LOGGER.info("getMemberInfo-responseStr" + responseStr);
            PosData<PosCustomer> posData = JSON.parseObject(responseStr, PosData.class);
            if (PosData.ERROR.equals(posData.getCode())) {
//                throw new NullPointerException(posData.getMessage());
                LOGGER.error(posData.getMessage());
                return new PosData<PosCustomer>();
            }
            return posData;
        } catch (Exception e) {
            e.printStackTrace();
            return PosData.error(e.getMessage());
        }
    }

    public PosData<PosCustomer> offlineUpload(PosOfflineUploadRep posMsgModel) {
        try {
            String ts = DateUtil.getTimeStr();
            PosModel posModel = getSign(ts);
            Map<String, String> param = new HashMap<>();
            param.put("appid", posModel.getAppid());
            param.put("ts", ts);
            param.put("v", posModel.getVersion());
            param.put("sign", posModel.getSign());
            param.put("model", "order");
            param.put("method", "offlineUpload");
            param.put("msg", JSON.toJSONString(posMsgModel));
            String[] urlParamArr = new String[param.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : param.entrySet()) {
                urlParamArr[i++] = entry.getKey() + "=" + entry.getValue();
            }
            String urlParam = StringUtils.join(urlParamArr, "&");
            LOGGER.info("getMemberInfo-urlParam" + urlParam);
            String responseStr = HttpRequest.sendPost(posModel.getRequestUrl(), urlParam, null);
            LOGGER.info("getMemberInfo-responseStr" + responseStr);
            PosData<PosCustomer> posData = JSON.parseObject(responseStr, PosData.class);
            if (PosData.ERROR.equals(posData.getCode())) {
                LOGGER.error(posData.getMessage());
                return new PosData<PosCustomer>();
            }
            return posData;
        } catch (Exception e) {
            e.printStackTrace();
            return PosData.error(e.getMessage());
        }
    }


    private PosModel getSign(String ts) {
        PosModel posModel = new PosModel();
        ConfigtblModel model = new ConfigtblModel();
        model.setDatatype("pos");
        model = configService.getModelById(model);
        Map<String, String> param = (Map<String, String>) JSON.parse(model.getComments());
        posModel.setSign(EncryptUtils.md5(param.get("appid") + param.get("secret") + ts + param.get("v")));
        posModel.setAppid(param.get("appid"));
        posModel.setVersion(param.get("v"));
        posModel.setRequestUrl(param.get("url"));
        return posModel;
    }
}