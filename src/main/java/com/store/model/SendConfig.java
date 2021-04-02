package com.store.model;

import java.util.List;
import java.util.Map;

public class SendConfig {

    private List<SendtimeconfigModel> configTimeStage;

    private String sendRange;

    private Map<String,String> sendCost;

    public List<SendtimeconfigModel> getConfigTimeStage() {
        return configTimeStage;
    }

    public void setConfigTimeStage(List<SendtimeconfigModel> configTimeStage) {
        this.configTimeStage = configTimeStage;
    }

    public String getSendRange() {
        return sendRange;
    }

    public void setSendRange(String sendRange) {
        this.sendRange = sendRange;
    }

    public Map<String, String> getSendCost() {
        return sendCost;
    }

    public void setSendCost(Map<String, String> sendCost) {
        this.sendCost = sendCost;
    }
}