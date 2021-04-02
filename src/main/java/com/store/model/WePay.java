package com.store.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import com.store.utils.ConstantVariable;
import com.store.utils.EncryptUtils;
import com.store.utils.HttpRequest;
import com.store.utils.SendXml;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSON;

public class WePay {

    public final static String APP_ID = "wx1cc38c94b01bbf82";

    public final static String MCH_ID = "1511720071";
//    public final static String MCH_ID = "1511720071";
//    public final static String MCH_ID = "1511720072";

    public final static String APP_KEY = "2E7CF6B703C6A145576FC3B67FAD8D92";

    public final static String APP_SECRET = "63c2e63f63fd22e717d1d9cc6bd1b70a";

    public static String sendOrderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static String queryOrderUrl = "https://api.mch.weixin.qq.com/pay/orderquery";

    public final static String ACCESS_TOKEN =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APP_ID + "&secret="
                    + APP_SECRET;

    public final static String WEIXIN_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
            + APP_ID + "&secret=" + APP_SECRET + "&code=@code&grant_type=authorization_code";

    public static void main(String[] args)
            throws UnsupportedEncodingException {
        WePay wp = new WePay();
        String body = "1111";
        String out_trade_no = "test20170402";
        String total_fee = "1";
        String spbill_create_ip = "127.0.0.1";
        String notify_url = "111";
        String trade_type = ConstantVariable.WE_NATIVE_PAY;
        String openid = "";
        HashMap<String, String> hash =
                wp.resultSendOrder(body, out_trade_no, total_fee, spbill_create_ip, notify_url, trade_type, openid);
        // HashMap<String, String> hash = wp.resultQueryOrder(out_trade_no);
        System.out.println(JSON.toJSONString(hash));
    }

    public HashMap<String, String> resultQueryOrder(String out_trade_no) {
        String resultXml = "";
        try {
            resultXml = queryOrder(out_trade_no);
            return this.parse(resultXml);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    public HashMap<String, String> parse(String protocolXML) {
        HashMap<String, String> hash = new HashMap<String, String>();
        try {
            Document doc = (Document) DocumentHelper.parseText(protocolXML);
            Element books = doc.getRootElement();
            System.out.println("根节点" + books.getName());
            // Iterator users_subElements = books.elementIterator("UID");//指定获取那个元素
            Iterator Elements = books.elementIterator();
            while (Elements.hasNext()) {
                Element ele = (Element) Elements.next();
                System.out.println("节点" + ele.getName() + "\ttext=" + ele.getText());
                hash.put(ele.getName(), ele.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return hash;
        }
        return hash;
    }

    public String queryOrder(String out_trade_no)
            throws UnsupportedEncodingException {
        String nonce_str = EncryptUtils.md5(UUID.randomUUID().toString()).substring(0, 8);
        HashMap<String, String> sendOrderHash = new HashMap<String, String>();
        sendOrderHash.put("appid", APP_ID);
        sendOrderHash.put("mch_id", MCH_ID);
        sendOrderHash.put("nonce_str", nonce_str);
        sendOrderHash.put("out_trade_no", out_trade_no);
        String sortStr = formatUrlMap(sendOrderHash, false, true);
        sortStr = sortStr + "&key=" + APP_KEY;
        String sign = EncryptUtils.md5(sortStr);
        sendOrderHash.put("sign", sign.toUpperCase());
        String xml = SendXml.qureyOrderXml(out_trade_no, nonce_str, sign);
        String result = HttpRequest.sendPost(queryOrderUrl, xml, null);
        return result;
    }

    public HashMap<String, String> resultSendOrder(String body, String out_trade_no, String total_fee,
                                                   String spbill_create_ip, String notify_url, String trade_type, String openid) {
        String resultXml = "";
        try {
            resultXml = sendOrder(out_trade_no, body, total_fee, spbill_create_ip, notify_url, trade_type, openid);
            return parse(resultXml);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public String sendOrder(String body, String out_trade_no, String total_fee, String spbill_create_ip,
                            String notify_url, String trade_type, String openid)
            throws UnsupportedEncodingException {
        String nonce_str = "8540ce37";
        HashMap<String, String> sendOrderHash = new HashMap<String, String>();
        sendOrderHash.put("appid", APP_ID);
        sendOrderHash.put("mch_id", MCH_ID);
        sendOrderHash.put("nonce_str", nonce_str);
        sendOrderHash.put("body", body);
        sendOrderHash.put("out_trade_no", out_trade_no);
        sendOrderHash.put("total_fee", total_fee);
        sendOrderHash.put("spbill_create_ip", spbill_create_ip);
        sendOrderHash.put("notify_url", notify_url);
        sendOrderHash.put("trade_type", trade_type);
        if (StringUtils.isNotBlank(openid)) {
            sendOrderHash.put("openid", openid);
        }
        String sortStr = formatUrlMap(sendOrderHash, false, true);
        System.out.println(sortStr);
        sortStr = sortStr + "&key=" + APP_KEY;
        String sign = EncryptUtils.md5(new String(sortStr.getBytes("UTF-8")));
        sendOrderHash.put("sign", sign.toUpperCase());
        String xml = SendXml.sendOrderXml(nonce_str,
                sign,
                total_fee,
                spbill_create_ip,
                out_trade_no,
                body,
                notify_url,
                trade_type,
                openid);
        System.out.println(xml);
        String result = HttpRequest.sendPost(sendOrderUrl, xml, null);
        return result;
    }

    /**
     * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br>
     * 实现步骤: <br>
     *
     * @param paraMap    要排序的Map对象
     * @param urlEncode  是否需要URLENCODE
     * @param keyToLower 是否需要将Key转换为全小写 true:key转化成小写，false:不转化
     * @return
     */
    public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower) {
        String buff = "";
        Map<String, String> tmpMap = paraMap;
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (StringUtils.isNotBlank(item.getKey())) {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (urlEncode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    if (keyToLower) {
                        buf.append(key.toLowerCase() + "=" + val);
                    } else {
                        buf.append(key + "=" + val);
                    }
                    buf.append("&");
                }

            }
            buff = buf.toString();
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            return null;
        }
        return buff;
    }
}