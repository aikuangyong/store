package com.store.utils;

import com.alibaba.fastjson.JSON;
import com.store.model.config.ApplicationEntity;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MapUtil {

    @Autowired
    private ApplicationEntity entity;

    private final String URL_FIND_ADDRESS = "http://api.map.baidu.com/geocoder/v2/";

    private final String URL_CONVERT = "http://api.map.baidu.com/geoconv/v1/";

    private static double EARTH_RADIUS = 6371.393;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算两个经纬度之间的距离
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;
    }


    public String positionToAddress(String lat, String lng) {
        String mapAk = entity.getApi().get("mapAk");
        String param = "callback=renderReverse&location=%s,%s&output=json&pois=1&ak=%s";
        String urlParam = String.format(param, lng, lat, mapAk);
        return HttpRequest.sendGet(URL_FIND_ADDRESS, urlParam);
    }

    public String convertPositionToBDPosition(String lat, String lng) {
        String mapAk = entity.getApi().get("mapAk");
        String param = "coords=%s,%s&from=1&to=5&ak=%s";
        String urlParam = String.format(param, lng, lat, mapAk);
        return HttpRequest.sendGet(URL_CONVERT, urlParam);
    }
}