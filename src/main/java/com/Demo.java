package com;

import com.store.utils.DateUtil;
import com.store.utils.EncryptUtils;
import com.store.utils.HttpRequest;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.poifs.filesystem.EntryUtils;

import java.util.*;

public class Demo {

    public static void main(String[] args) {
        String appid = "f93fb45f-ed25-466a-9215-d0d93a809f98";
        String secret = "2194BE12F45556DDE33E8139C1CC5518";
        String ts = "20180907224602";
        String v = "2.0";
        String sign = EncryptUtils.md5(appid + secret + ts + v);
        System.out.println(sign);
        Map<String, String> param = new HashMap<>();
        param.put("appid", appid);
        param.put("ts", ts);
        param.put("v", v);
        param.put("sign", sign);
        param.put("model", "customer");
        param.put("method", "get");
        param.put("msg", "{\"mobile\":\"13961618173\"}");
        String[] urlParam = new String[param.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : param.entrySet()) {
            urlParam[i++] = entry.getKey() + "=" + entry.getValue();
        }
        String urlP = StringUtils.join(urlParam, "&");
        System.out.println(urlP);
        System.out.println(HttpRequest.sendPost("http://47.99.51.145:8889/VipaxWS/ws", urlP, null));
//        String appid = "f93fb45f-ed25-466a-9215-d0d93a809f98";
//        String secret = "2194BE12F45556DDE33E8139C1CC5518";
//        String ts = "20180907224602";
//        String v = "2.0";
//        String sign = EncryptUtils.md5(appid + secret + ts + v);
//        System.out.println(sign);
//        Map<String, String> param = new HashMap<>();
//        param.put("appid", appid);
//        param.put("ts", ts);
//        param.put("v", v);
//        param.put("sign", sign);
//        List<Map<String,String>> dataList = new ArrayList();
//        String[] urlParam = new String[param.size()];
//        int i = 0;
//        for (Map.Entry<String, String> entry : param.entrySet()) {
//            urlParam[i++] = entry.getKey() + "=" + entry.getValue();
//        }
//        String urlP = StringUtils.join(urlParam, "&");
//        System.out.println(urlP);
//        System.out.println(HttpRequest.sendPost("http://47.99.51.145:8889/VipaxWS/ws", urlP, null));
    }
}