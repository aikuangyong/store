package com.store.utils;

import com.store.model.WePay;

public class SendXml {

    public static String qureyOrderXml(String out_trade_no, String nonce_str, String sign) {
        StringBuffer buff = new StringBuffer("<xml>");
        buff.append("<appid>")
                .append(WePay.APP_ID)
                .append("</appid>")
                .append("<mch_id>")
                .append(WePay.MCH_ID)
                .append("</mch_id>")
                .append("<nonce_str>")
                .append(nonce_str)
                .append("</nonce_str>")
                .append("<sign>")
                .append(sign)
                .append("</sign>")
                .append("<out_trade_no>")
                .append(out_trade_no)
                .append("</out_trade_no>");
        buff.append("</xml> ");
        return buff.toString();
    }

    public static String sendOrderXml(String nonce_str, String sign, String total_fee, String spbill_create_ip,
                                      String out_trade_no, String body, String notify_url, String trade_type, String openid) {
        StringBuffer buff = new StringBuffer("<xml>");
        buff.append("<appid>")
                .append(WePay.APP_ID)
                .append("</appid>")
                .append("<mch_id>")
                .append(WePay.MCH_ID)
                .append("</mch_id>")
                .append("<nonce_str>")
                .append(nonce_str)
                .append("</nonce_str>")
                .append("<sign>")
                .append(sign)
                .append("</sign>")
                .append("<out_trade_no>")
                .append(out_trade_no)
                .append("</out_trade_no>")
                .append("<body>")
                .append(body)
                .append("</body>")
                .append("<total_fee>")
                .append(total_fee)
                .append("</total_fee>")
                .append("<spbill_create_ip>")
                .append(spbill_create_ip)
                .append("</spbill_create_ip>")
                .append("<notify_url>")
                .append(notify_url)
                .append("</notify_url>")
                .append("<trade_type>")
                .append(trade_type)
                .append("</trade_type>");
        if (StringUtils.isNotBlank(openid)) {
            buff.append("<openid>").append(openid).append("</openid>");
        }
        buff.append("</xml> ");
        return buff.toString();
    }
}