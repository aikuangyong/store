package com.store.api;

import com.store.api.model.data.StoreVO;
import com.store.model.*;
import com.store.service.*;
import com.store.utils.CodeUtil;
import com.store.utils.MapUtil;
import com.store.utils.MathUtil;
import com.store.utils.StringUtils;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.transform.Result;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/regular")
@Api(value = "定期购相关接口;步骤getRegularDetail(获取定期购信息)->createOrderByRegular(根据定期购信息下单获取订单)->getRegularOrder(按照用户获取订单信息)", tags = "定期购")
public class ApiRegular {

    private static final Logger LOGGER = Logger.getLogger(ApiRegular.class);

    @Autowired
    private RegularService regularService;

    @Autowired
    private RegularsuborderService regularsuborderService;

    @Autowired
    private RegularcomponentService regularcomponentService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private UseraddressService addressService;

    @Autowired
    private MapUtil mapUtil;

    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户iD", required = true)})
    @ApiOperation(value = "定期购的子订单下单;", notes = "定期购的子订单下单")
    @RequestMapping(value = "/clearRegularOrder", method = RequestMethod.GET)
    @ResponseBody
    public String clearRegularOrder(String userid) {
        regularsuborderService.clearRegular(userid);
        return ResultData.toSuccessString();
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "suborderid", defaultValue = "1", value = "子订单的ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "vid", defaultValue = "1", value = "1", required = true)})
    @ApiOperation(value = "定期购的子订单下单;", notes = "定期购的子订单下单")
    @ApiResponses({@ApiResponse(code = 200, message = "")})
    @RequestMapping(value = "/addSuborder", method = RequestMethod.GET)
    @ResponseBody
    public String addSuborder(String suborderid, String vid) {
        try {
            RegularsuborderModel regularsuborderModel = new RegularsuborderModel();
            regularsuborderModel.setSuborderid(suborderid);
            List dataList = regularsuborderService.getList(regularsuborderModel);
            if (CollectionUtils.isEmpty(dataList)) {
                return ResultData.toErrorString("错误的子订单信息");
            }
            regularService.addSuborder(suborderid, vid);
            return ResultData.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "rid", defaultValue = "", value = "定期购的ID,默认不用传", required = false)})
    @ApiOperation(value = "获取定期购信息", notes = "获取定期购信息")
    @ApiResponses({@ApiResponse(code = 200, message = "{\"dataCount\": 0,\"dataList\": {\"priceInfo\": {\"3###90\": \"13\", //价格计算公式,3g配送90次 价格13元\"5###30\": \"14\",\"5###60\": \"15\",\"3###30\": \"11\",\"5###90\": \"16\",\"3###60\": \"12\"},\"sendInfo\": {\"sendPrice\": \"2\", //配送费用2元\"costPrice\": \"3\" //满3元免邮},\"util\": [{\"val\": \"3g\",\"key\": \"3\"},{\"val\": \"5g\",\"key\": \"5\"}],\"sendDay\": [{\"val\": \"每日送\",\"key\": \"1\"},{\"val\": \"两日送\",\"key\": \"2\"},{\"val\": \"三日送\",\"key\": \"3\"},{\"val\": \"自选\",\"key\": \"4\"}],\"sendTimeStage\": [{\"createtime\": 1535121755000,\"dataCount\": 0,\"endtime\": \"13\",\"groupseq\": \"0\",\"id\": 43,\"pageNow\": 0,\"pageNumber\": 1,\"pageSize\": 15,\"showtext\": \"8:00-13:00\", //配送时间段\"starttime\": \"8\"},{\"createtime\": 1535121755000,\"dataCount\": 0,\"endtime\": \"18\",\"groupseq\": \"1\",\"id\": 44,\"pageNow\": 0,\"pageNumber\": 1,\"pageSize\": 15,\"showtext\": \"16:00-18:00\", //配送时间段\"starttime\": \"16\"}],\"sendStage\": \"1\", //配送范围\"regularInfo\": {\"component\": \"\",\"componentList\": [],\"content\": \"12\",\"createtime\": 1534051481000,\"dataCount\": 0,\"pageNow\": 0,\"pageNumber\": 1,\"pageSize\": 15,\"regulardetails\": \"<p>12</p>\", //详情 \"regularimg\": \"fc606448-941f-b131-4590-1ba5c761ea87\",\"regularname\": \"12\", //商品名称\"rid\": \"0502bfce-9df0-11e8-93d5-00ff57a662b1\",\"valid\": \"1\"},\"sendCount\": [{\"val\": \"30次\",\"key\": \"30\"},{\"val\": \"60次\",\"key\": \"60\"},{\"val\": \"90次\",\"key\": \"90\"}]},\"pageCount\": 0,\"pageNumber\": 0,\"state\": \"SUCCESS\"}")})
    @RequestMapping(value = "/getRegularDetail", method = RequestMethod.GET)
    @ResponseBody
    public String getRegularDetail(@RequestParam(value = "rid", required = false) String rid) {
        return ResultData.toSuccessString(regularService.getRegularDetail(rid));
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "", value = "用户Id", required = true)})
    @ApiOperation(value = "获取用户已下单的定期购套餐", notes = "获取用户已下单的定期购套餐;status等于1表示订单还在配送中,等2表示已结束,suborderno在修改的时候需要用")
    @ApiResponses({@ApiResponse(code = 200, message = "")})
    @RequestMapping(value = "/getRegularOrderList", method = RequestMethod.GET)
    @ResponseBody
    public String getRegularOrderList(String userid) {
        UserRegularOrderInfo orderInfo = new UserRegularOrderInfo();
        orderInfo.setUserid(userid);
        return ResultData.toSuccessString(regularService.getRegularOrderList(orderInfo));
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "", value = "用户id", required = true)})
    @ApiOperation(value = "我的套餐页面数据接口", notes = "我的套餐页面数据接口")
    @ApiResponses({@ApiResponse(code = 200, message = "{\"dataCount\": 0,\"dataList\": [{\"donestagetime\": \"16:00-18:00\", //完成配送的时间段\"orderprice\": \"100\", //订单价格\"regularno\": \"201808282159462270632\", //订单编号\"sendcount\": \"30\", //配送次数\"sendtype\": \"自选\", //配送模式;\"sendweek\": \"1,3,4,6,7\", //自定义的天数\"startsendtime\": \"2018-09-20 18:00\", //配送完成时间\"status\": \"1\", // \"suborderid\": \"21833fba5684418d61ed138fd8c6d31e\", //子订单的ID\"surplus\": \"30\", //剩余次数\"unit\": \"3g\", //含量\"userid\": 49897871224ca0fd072c319dbdec1a1b //用户ID}],\"dataObj\": null,\"msg\": null,\"pageCount\": 0,\"pageNumber\": 0,\"state\": \"SUCCESS\"}")})
    @RequestMapping(value = "/getRegularOrder", method = RequestMethod.GET)
    @ResponseBody
    public String getRegularOrder(@RequestParam(value = "userid", required = true) String userid) {
        try {
            RegularOrderInfo orderInfo = new RegularOrderInfo();
            orderInfo.setUserid(userid);
            Map<String, List<RegularsuborderModel>> returnHash = regularService.getUserMealPackage(orderInfo);
            return ResultData.toSuccessDataObj(returnHash);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "suborderno", defaultValue = "1", value = "定期购订单的编号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "sendtime", defaultValue = "1", value = "取消那一天的日期", required = true)})
    @ApiOperation(value = "取消定期购的子订单", notes = "取消定期购的子订单")
    @ApiResponses({@ApiResponse(code = 200, message = "")})
    @RequestMapping(value = "/cancelRegularOrder", method = RequestMethod.GET)
    @ResponseBody
    public String cancelRegularOrder(String userid, String suborderno, String sendtime) {
        try {
            RegularsuborderModel suborderModel = new RegularsuborderModel();
            suborderModel.setUserid(userid);
            suborderModel.setSuborderno(suborderno);
            suborderModel.setSendtime(sendtime);
            regularService.cancelSuborder(suborderModel);
            return ResultData.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "49897871224ca0fd072c319dbdec1a1b", value = "用户id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "regularno", defaultValue = "201809122330403748867", value = "定期购的主订单号;对应getRegularOrderList接口的orderno字段", required = true),
            //@ApiImplicitParam(paramType = "query", dataType = "String", name = "subregularno", defaultValue = "201809122330403748867", value = "定期购的子订单号;对应getRegularOrderList接口的suborderno字段", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "sendtime", defaultValue = "2018-10-25", value = "2018-09-03;定期购的开始配送时间;对应getRegularOrderList接口的startsendtime字段", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "edittype", defaultValue = "1", value = "1地址2时段", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "editinfo", defaultValue = "41645303-a948-11e8-bd6a-00ff57a662b1", value = "地址就传addressid/时段就传对应的id", required = true)})
    @ApiOperation(value = "编辑定期购的子订单", notes = "编辑定期购的子订单")
    @ApiResponses({@ApiResponse(code = 200, message = "")})
    @RequestMapping(value = "/editRegularOrderInfo", method = RequestMethod.GET)
    @ResponseBody
    public String editRegularOrderInfo(String userid, String regularno, String sendtime, String edittype, String editinfo) {
        try {
            RegularsuborderModel suborderModel = new RegularsuborderModel();
            suborderModel.setUserid(userid);
            suborderModel.setRegularno(regularno);
            suborderModel.setSendtime(sendtime);
            boolean bl = regularService.editRegularOrderInfo(suborderModel, edittype, editinfo);
            if (bl) {
                return ResultData.toSuccessString();
            } else {
                return ResultData.toErrorString("数据有误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/createOrderByRegular", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "rid", defaultValue = "0502bfce-9df0-11e8-93d5-00ff57a662b1", value = "定期购的ID,必传", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "49897871224ca0fd072c319dbdec1a1b", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "addressid", defaultValue = "41645303-a948-11e8-bd6a-00ff57a662b1", value = "收货地址的ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderprice", defaultValue = "100", value = "订单金额", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "content", defaultValue = "备注", value = "备注信息", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "sendstarttime", defaultValue = "2018-10-29", value = "配送时间,格式:yyyy-MM-dd(2018-08-27)", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "unit", defaultValue = "3", value = "燕窝含量,传不带单位(ex:3g)的那个id过来", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "timestage", defaultValue = "1", value = "配送时段,传ID过来", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "sendcount", defaultValue = "30", value = "配送次数", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "sendday", defaultValue = "1", value = "配送方式;如果是自定义,ID=4,则sendweek不能为空", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "cardno", defaultValue = "MEMB20180810000004", value = "配送方式;如果是自定义,ID=4,则sendweek不能为空", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "paytype", defaultValue = "1", value = "支付方式;1:支付宝;2:微信;3:打折卡", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "sendweek", defaultValue = "1,3,4,6,7", value = "如果选择的是自定义,则该字段为必传", required = false)})
    @ApiOperation(value = "根据定期购的购买信息产生一个定期购订单", notes = "根据定期购的购买信息产生一个定期购订单")
    public String createOrderByRegular(String rid, String userid, String addressid, String orderprice, String unit, String timestage, int sendcount, String sendday, String sendstarttime, @RequestParam(value = "sendweek", required = false) String sendweek, String paytype, @RequestParam(value = "cardno", required = false) String cardno, @RequestParam(value = "cardno", required = false, defaultValue = "") String content) {
        try {
            RegularsuborderModel checkSuborder = new RegularsuborderModel();
            checkSuborder.setSendtime(sendstarttime);
            checkSuborder.setUserid(userid);
            List<RegularsuborderModel> checkList = regularsuborderService.getList(checkSuborder);
            if (checkList.size() > 0) {
                return ResultData.toErrorString("当前时间范围内已存在定期购套餐");
            }

            RegularorderModel regularorderModel = new RegularorderModel();
            RegularorderdetailModel detailModel = new RegularorderdetailModel();
            RegularModel regularModel = new RegularModel();
            rid = regularService.getModelById(regularModel).getRid();

            try {
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("sendcount", sendcount);
                paramMap.put("unit", unit);
                //计算定期购的价格
                RegularcomponentModel regularcomponentModel = regularcomponentService.getRegularPrice(paramMap);
                String regularPrice = regularcomponentModel.getVal();

                //计算需要配送的距离
                UseraddressModel addressModel = new UseraddressModel();
                addressModel.setAddressid(addressid);
                addressModel = addressService.getModelById(addressModel);
                StoreModel storeModel = new StoreModel();
                List<StoreVO> voList = storeService.getListToAppWidth(storeModel, addressModel.getLng(), addressModel.getLat());
                StoreVO storeVO = voList.get(0);
                double distance = mapUtil.getDistance(Double.parseDouble(storeVO.getLat()), Double.parseDouble(storeVO.getLng()), Double.parseDouble(addressModel.getLat()), Double.parseDouble(addressModel.getLng()));
                int regularDistance = (int) (Math.abs(distance) / 1000);

                //计算配送信息;sendPrice:配送费用;costPrice:多钱免配送费
                Map<String, Object> costConfig = regularService.getSendCostConfig();
                String sendPrice = StringUtils.defaultIfBlank(String.valueOf(costConfig.get("sendPrice")), "0");
                String costPrice = StringUtils.defaultIfBlank(String.valueOf(costConfig.get("costPrice")), "0");
                Double subPrice = Double.parseDouble(MathUtil.sub(regularPrice, costPrice));
                //计算配送信息;sendStage
                String sendStage = StringUtils.defaultIfBlank(regularService.getSendStage(), "0");
                int sendStageInt = Integer.parseInt(sendStage);
                //如果免配送是两公里,但实际配送要四公里,或者餐费小于折扣费
                if (sendStageInt < regularDistance || subPrice < 0) {
                    orderprice = MathUtil.add(regularPrice, sendPrice);
                } else {
                    orderprice = regularPrice;
                }
            } catch (Exception e) {
                LOGGER.error("calc-orderprice-error:" + e.getMessage());
                return ResultData.toErrorString();
            }

            regularorderModel.setOrderno(CodeUtil.getOrderNo(2));
            regularorderModel.setTradetime(new Date());
            regularorderModel.setPrice(Double.valueOf(orderprice));
            regularorderModel.setUserid(userid);
            regularorderModel.setAddressid(addressid);
            regularorderModel.setRegularid(rid);
            regularorderModel.setSendstarttime(sendstarttime);
            regularorderModel.setPaytype(paytype);
            regularorderModel.setCardno(cardno);
            detailModel.setUserid(userid);
            detailModel.setUnit(unit);
            detailModel.setContent(content);
            detailModel.setSendcount(sendcount);
            detailModel.setSendday(sendday);
            detailModel.setTimestage(timestage);
            detailModel.setSendweek(sendweek);
            detailModel.setPaytype(paytype);
            detailModel.setOrderprice(Double.parseDouble(orderprice));
            regularorderModel.setRegularorderdetailModel(detailModel);
            if ("3".equals(paytype)) {
                regularService.createRegularOrder(regularorderModel);
            } else {
                regularService.saveRegularInfo(regularorderModel);
            }
            Map<String, String> returnMap = new HashMap<>();
            returnMap.put("orderno", regularorderModel.getOrderno());
            returnMap.put("price", String.valueOf(regularorderModel.getPrice()));
            return ResultData.toSuccessString(returnMap);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
            return ResultData.toErrorString();
        }
    }

}