package com.store.utils;


import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSON;
import com.store.model.SmsinfoModel;
import com.store.model.config.ApplicationEntity;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PushUtil {

    private static final Logger LOG = Logger.getLogger(PushUtil.class);

    @Autowired
    private ApplicationEntity applicationEntity;

//    public void push(Map<String, String> contentParam) {
//        Map<String, Object> sendParam = new HashMap<>();
//        Map<String, String> tempParam = new HashMap<>();
//        sendParam.put("platform", "all");
//        sendParam.put("audience", "all");
//        sendParam.put("notification", contentParam);
////        sendParam.put("message", contentParam);
//        String postParam = JSON.toJSONString(sendParam);
//        Map<String, String> requestProperty = new HashMap<String, String>();
//        Map<String, String> push = applicationEntity.getPush();
//        String appkey = push.get("appKey");
//        String jmsUrl = push.get("url");
//        String masterSecret = push.get("masterSecret");
//        requestProperty.put("Authorization", "Basic " + Base64.encodeBase64String((appkey + ":" + masterSecret).getBytes()));
////        requestProperty.put("Content-Type", "application/json");
//        String str = HttpRequest.sendPost(jmsUrl, "", requestProperty);
//        System.out.println(str);
//    }

    public static PushPayload buildPushObject_all_all_alert(String title, String id) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.android("通知", "1111", null))
                .build();
    }

    public void push(Map<String, String> param) {
        Map<String, String> push = applicationEntity.getPush();
        JPushClient jpushClient = new JPushClient(push.get("masterSecret"), push.get("appKey"), null, ClientConfig.getInstance());

        // For push, all you need do is to build PushPayload object.
        String title = param.get("title");
        String id = param.get("id");
        PushPayload payload = buildPushObject_all_all_alert(title, id);
        try {
            PushResult result = jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            LOG.error("Connection error, should retry later", e);
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            LOG.error("Should review the error, and fix the request", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

}