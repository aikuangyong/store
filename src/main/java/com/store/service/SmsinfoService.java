package com.store.service;

import com.alibaba.fastjson.JSON;
import com.store.dao.SmsinfoMapper;
import com.store.model.CustomerModel;
import com.store.model.SmsinfoModel;
import com.store.model.ResultData;
import com.store.model.config.ApplicationEntity;
import com.store.utils.*;
import io.netty.handler.codec.base64.Base64Encoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.util.*;

@Service("smsinfoService")
public class SmsinfoService {

    @Autowired
    private SmsinfoMapper smsinfoMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ApplicationEntity applicationEntity;

    @Autowired
    private ExcelUtil<SmsinfoModel> excelUtil;

    public XSSFWorkbook export(SmsinfoModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<SmsinfoModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public SmsinfoModel getLastSmsInfo(SmsinfoModel model) {
        return smsinfoMapper.getLastSmsInfo(model);
    }

    public String sendSms(SmsinfoModel model) {
        String code = CodeUtil.getRandomNumber(4);
        //短信类型1注册2忘记密码3绑定账号
        if ("1".equals(model.getSmstype())) {
            CustomerModel customerModel = new CustomerModel();
            customerModel.setPhoneno(model.getPhoneno());
            if (customerService.getList(customerModel).size() > 0) {
                return ResultData.toErrorString("此号码已注册，请直接登录。");
            }
        }
        SmsinfoModel smsModel = smsinfoMapper.getLastSmsInfo(model);
        List<SmsinfoModel> smsList = smsinfoMapper.getList(model);
        if (null != smsModel) {
            if (System.currentTimeMillis() / 1000 - smsModel.getSendtime().getTime() / 1000 < 120) {
                return ResultData.toErrorString("请勿频繁发送短信");
            }
            String nowDay = DateUtil.getDay();
            int sendCnt = 0;
            for (SmsinfoModel smsInfo : smsList) {
                Date sendTime = smsInfo.getSendtime();
                String sendDay = DateUtil.sdfDay.format(sendTime);
                if (nowDay == sendDay) {
                    if (++sendCnt == 3) {
                        return ResultData.toErrorString("一天只能发送三次短信");
                    }
                }
            }
        }
        model.setVerifycode(code);
        model.setSendtime(DateUtil.getDate());

        Map<String, String> requestProperty = new HashMap<String, String>();
        send(model, 1);
        return ResultData.toSuccessDataObj(code, "短信发送成功，请注意查收。");
    }

    private void send(SmsinfoModel model, Integer tempId) {
        Map<String, Object> sendParam = new HashMap<>();
        Map<String, String> tempParam = new HashMap<>();
        tempParam.put("code", model.getVerifycode());
        sendParam.put("mobile", model.getPhoneno());
        sendParam.put("temp_id", tempId);
        sendParam.put("temp_para", tempParam);
        String postParam = JSON.toJSONString(sendParam);
        Map<String, String> requestProperty = new HashMap<String, String>();
        Map<String, String> sms = applicationEntity.getSms();
        String appkey = sms.get("appKey");
        String jmsUrl = sms.get("url");
        String masterSecret = sms.get("masterSecret");
        requestProperty.put("Authorization", "Basic " + Base64.encodeBase64String((appkey + ":" + masterSecret).getBytes()));
        requestProperty.put("Content-Type", "application/json");
        String str = HttpRequest.sendPost(jmsUrl, postParam, requestProperty);
        model.setResultMsg(str);
        insert(model);
    }

    public ResultData getData(SmsinfoModel model) {
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

    public SmsinfoModel getModelById(SmsinfoModel model) {
        List<SmsinfoModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<SmsinfoModel> getList(SmsinfoModel model) {
        return smsinfoMapper.getList(model);
    }

    public int getCount(SmsinfoModel model) {
        return smsinfoMapper.getCount(model);
    }

    public SmsinfoModel insert(SmsinfoModel model) {
        smsinfoMapper.insert(model);
        return model;
    }

    public SmsinfoModel update(SmsinfoModel model) {
        smsinfoMapper.update(model);
        return model;
    }

    public List<SmsinfoModel> batchUpdate(List<SmsinfoModel> modelList) {
        for (SmsinfoModel model : modelList) {
            smsinfoMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(SmsinfoModel model) {
        smsinfoMapper.disableOrEnable(model);
    }

    public void delete(SmsinfoModel model) {
        smsinfoMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        SmsinfoModel model = new SmsinfoModel();
        model.setIdList(idList);
        smsinfoMapper.delete(model);
        return ResultData.toSuccessString();
    }

}