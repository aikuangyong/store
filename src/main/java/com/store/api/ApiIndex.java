package com.store.api;

import com.alibaba.fastjson.JSON;
import com.store.api.model.data.SiteVO;
import com.store.api.model.data.StoreVO;
import com.store.model.*;
import com.store.model.config.ApplicationEntity;
import com.store.service.*;
import com.store.utils.*;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/index")
@Api(value = "获取公共数据", tags = "获取公共数据")
public class ApiIndex {

    private final static Logger LOGGER = Logger.getLogger(ApiIndex.class);

    @Autowired
    private ApplicationEntity applicationEntity;

    @Autowired
    private ProvincesService provincesService;

    @Autowired
    private RegularService regularService;

    @Autowired
    private CitiesService citiesService;

    @Autowired
    private AreasService areasService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private SiteinfomationService siteService;

    @Autowired
    private VersionmanagerService versionmanagerService;

    @Autowired
    private SitemsgService sitemsgService;

    @Autowired
    private MapUtil mapUtil;


    @RequestMapping(value = "/getSaleServiceType", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "返回售后类型的选项", notes = "返回系统配置的参数;包含")
    public String getSaleServiceType() {
        List<String> dataList = new ArrayList<>();
        dataList.add("仅退款");
        dataList.add("退货退款");
        return ResultData.toSuccessString(dataList);
    }

    @RequestMapping(value = "/getOpenid", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "code", defaultValue = "1", value = "用户的code", required = true)})
    @ApiOperation(value = "获取openid", notes = "返回系统配置的参数;包含")
    public String getOpenid(String code) {
        try {
            String appid = applicationEntity.getWx().get("appid");
            String secret = applicationEntity.getWx().get("secret");
            String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
            LOGGER.error(requestUrl);
            String responseStr = HttpRequest.sendGet(requestUrl, null);
            Map<String, String> returnMap = JSON.parseObject(responseStr, Map.class);
            return ResultData.toSuccessString(returnMap);
        } catch (Exception e) {
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/getMusic", method = RequestMethod.GET)
    @ResponseBody
    public String getMusic(String data){
        String a = data;
        return "";
    }

    @RequestMapping(value = "/getMessageDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "msgid", defaultValue = "1", value = "消息ID", required = true)})
    @ApiOperation(value = "按照消息编号获取一条信息", notes = "")
    public String getMessageDetail(String userid, String msgid) {
        try {
            SitemsgModel model = new SitemsgModel();
            model.setMsgid(msgid);
            SitemsgModel dataObj = sitemsgService.getModelById(model);
            return ResultData.toSuccessDataObj(dataObj);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "", required = true)})
    @ApiOperation(value = "获取消息列表")
    public String getMessage(String userid) {
        try {
            SitemsgModel model = new SitemsgModel();
            model.noPage();
            List<SitemsgModel> dataList = sitemsgService.getList(model);
            return ResultData.toSuccessString(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/alipayConfig", method = {RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "返回支付宝支付的相关参数", notes = "返回系统配置的参数;包含")
    public String alipay() {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("ALI_APID", AlipayConfig.ALI_APID);
        returnMap.put("RSA2_PRIVATE", AlipayConfig.ALIPAY_PUBLIC_KEY);
        returnMap.put("PID", AlipayConfig.PID);
        return ResultData.toSuccessString(returnMap);
    }

    @RequestMapping(value = "/getConfig", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "返回系统配置的参数;包含配送距离(sendstage);配送时段的区间(sendtimestage)", notes = "返回系统配置的参数;包含")
    public String getConfig() {
        try {

            Map<String, Object> returnMap = regularService.getConfig();
            return ResultData.toSuccessString(returnMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }

    }

    @RequestMapping(value = "/getSiteInfomation", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "type", defaultValue = "1", value = "关于软件(1)/注册协议(2)/帮助中心(3)", required = true)})
    @ApiOperation(value = "获取站点信息关于软件/注册协议/帮助中心", notes = "获取站点信息关于软件(1)/注册协议(2)/帮助中心(3)")
    public String getSiteInfomation(String type) {
        try {
            SiteinfomationModel siteModel = new SiteinfomationModel();
            siteModel.setId(type);
            siteModel.noPage();
            siteModel = siteService.getModelById(siteModel);
            return ResultData.toSuccessString(new SiteVO(siteModel));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/getDistance", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "param", defaultValue = "[{lat1:121.480073,lng1:31.232347,lat2:120.480073,lng2:31.232305},{lat1:125.480073,lng1:32.232347,lat2:125.380073,lng2:33.132305}]", value = "参数", required = true)})
    @ApiOperation(value = "计算N对坐标之间的距离", notes = "计算N对坐标之间的距离")
    public String getDistance(String param) {
        List<Map<String, Object>> arrList = (List<Map<String, Object>>) JSON.parse(param);
        for (Map<String, Object> map : arrList) {
            Double lat1 = Double.parseDouble(map.get("lat1").toString()),
                    lng1 = Double.parseDouble(map.get("lng1").toString()), lat2 = Double.parseDouble(map.get("lat2").toString()),
                    lng2 = Double.parseDouble(map.get("lng2").toString());
            double distance = mapUtil.getDistance(lat1, lng1, lat2, lng2);
            map.put("distance", distance);
        }
        return ResultData.toSuccessString(arrList);
//        Map<String, Object> returnHash = (Map<String, Object>) JSON.parse(returnText);
//        return ResultData.toSuccessDataObj(returnHash.get("result"));
    }

    @RequestMapping(value = "/convertPositionToBDPosition", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "lng", defaultValue = "121.480073", value = "经度", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "lat", defaultValue = "31.232347", value = "维度", required = true)})
    @ApiOperation(value = "把原始地址转换成百度地图的地址", notes = "把原始地址转换成百度地图的地址")
    public String convertPositionToBDPosition(String lat, String lng) {
        String returnText = mapUtil.convertPositionToBDPosition(lat, lng);
        Map<String, Object> returnHash = (Map<String, Object>) JSON.parse(returnText);
        return ResultData.toSuccessDataObj(returnHash.get("result"));
    }

    @RequestMapping(value = "/positionToAddress", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "lng", defaultValue = "121.480073", value = "经度", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "lat", defaultValue = "31.232347", value = "维度", required = true)})
    @ApiOperation(value = "根据经纬度获取地址信息", notes = "根据百度地图的经纬度获取地址信息")
    public String positionToAddress(String lat, String lng) {
        String returnText = mapUtil.positionToAddress(lat, lng);
        returnText = returnText.substring(returnText.lastIndexOf("renderReverse&&renderReverse(") + 29, returnText.lastIndexOf(")"));
        Map<String, Object> returnHash = (Map<String, Object>) JSON.parse(returnText);
        return ResultData.toSuccessDataObj(returnHash.get("result"));
    }

    @RequestMapping(value = "/findProvince", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取省份", notes = "获取省份")
    public String findProvince() {
        ProvincesModel provincesModel = new ProvincesModel();
        provincesModel.noPage();
        List<ProvincesModel> dataList = provincesService.getList(provincesModel);
        return ResultData.toSuccessString(dataList);
    }

    @RequestMapping(value = "/getStore", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询所有门店", notes = "查询所有门店")
    public String getStore() {
        StoreModel storeModel = new StoreModel();
        List<StoreVO> voList = storeService.getListToApp(storeModel);
        return ResultData.toSuccessString(voList);
    }

    @RequestMapping(value = "/getStoreWidthDistance", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询所有门店包含是否包含距离;distributeScope(1:在范围;2:不在范围)", notes = "查询所有门店")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "lng", defaultValue = "121.478276", value = "经度-传入百度地图的坐标", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "lat", defaultValue = "31.231791", value = "维度-传入百度地图的坐标", required = true)})
    public String getStoreWidthDistance(String lng, String lat) {
        StoreModel storeModel = new StoreModel();
        List<StoreVO> voList = storeService.getListToAppWidth(storeModel, lng, lat);
        return ResultData.toSuccessString(voList);
    }

    @RequestMapping(value = "/findCity", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "provinceid", defaultValue = "1", value = "省份ID", required = true)})
    @ApiOperation(value = "获取城市", notes = "获取城市")
    public String findCity(String provinceid) {
        CitiesModel citiesModel = new CitiesModel();
        citiesModel.setProvinceid(provinceid);
        citiesModel.noPage();
        List<CitiesModel> dataList = citiesService.getList(citiesModel);
        return ResultData.toSuccessString(dataList);
    }

    @RequestMapping(value = "/getNewVersion", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取最新的版本", notes = "")
    public String getNewVersion() {
        VersionmanagerModel model = versionmanagerService.getNewVewsion(null);
        return ResultData.toSuccessDataObj(model);
    }

    @RequestMapping(value = "/findArea", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "cityid", defaultValue = "1", value = "城市ID", required = true)})
    @ApiOperation(value = "获取区域", notes = "获取区域")
    public String findArea(String cityid) {
        AreasModel areasModel = new AreasModel();
        areasModel.setCityid(cityid);
        List<AreasModel> dataList = areasService.getList(areasModel);
        return ResultData.toSuccessString(dataList);
    }
}