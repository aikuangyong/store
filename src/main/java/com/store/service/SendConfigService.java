package com.store.service;

import com.alibaba.fastjson.JSON;
import com.store.model.ConfigtblModel;
import com.store.model.SendConfig;
import com.store.model.SendtimeconfigModel;
import com.store.utils.ConstantVariable;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SendConfigService {


    @Autowired
    private SendtimeconfigService sendtimeconfigService;

    @Autowired
    private ConfigtblService configtblService;

    private static final Logger LOGGER = Logger.getLogger(SendConfigService.class);

    public boolean saveSendConfig(SendConfig configModel) {
        try {
            List<SendtimeconfigModel> timeConfigList = configModel.getConfigTimeStage();
            Map<String, String> sendCost = configModel.getSendCost();
            String sendRange = configModel.getSendRange();
            sendtimeconfigService.deleteAll();
            for (SendtimeconfigModel timeConfigModel : timeConfigList) {
                timeConfigModel.setCreatetime(new Date());
                timeConfigModel.setId(Integer.parseInt(timeConfigModel.getGroupseq()));
                sendtimeconfigService.insert(timeConfigModel);
            }
            ConfigtblModel sendRangeModel = new ConfigtblModel();
            sendRangeModel.setDatatype(ConstantVariable.SEND_RANGE);
            sendRangeModel.setComments(sendRange);
            configtblService.saveConfigModel(sendRangeModel);

            ConfigtblModel sendCostModel = new ConfigtblModel();
            sendCostModel.setDatatype(ConstantVariable.SEND_COST);
            sendCostModel.setComments(JSON.toJSONString(sendCost));
            configtblService.saveConfigModel(sendCostModel);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }

    }

    private SendConfig getSendConfig() {
        return null;
    }
}