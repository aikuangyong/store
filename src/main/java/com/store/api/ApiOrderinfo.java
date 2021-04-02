package com.store.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.store.api.model.order.OrderDetailVO;
import com.store.api.model.order.OrderInfoVO;
import com.store.model.*;
import com.store.model.config.ApplicationEntity;
import com.store.service.*;
import com.store.utils.*;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.store.controller.BaseController;
import org.apache.log4j.Logger;
import org.apache.poi.poifs.filesystem.EntryUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.*;
import java.util.*;

@RequestMapping("/api/orderinfo")
@Controller
@Api(value = "订单相关;", tags = "订单相关")
public class ApiOrderinfo extends BaseController {


    private static final Logger LOGGER = Logger.getLogger(ApiOrderinfo.class);

    @Autowired
    private ApplicationEntity applicationEntity;

    @Autowired
    private PosUserService posUserService;

    @Autowired
    private OrderinfoService orderinfoService;

    @Autowired
    private OrderdetailService orderdetailService;

    @Autowired
    private OrderevalService orderevalService;

    @Autowired
    private AfterserviceService afterserviceService;

    @Autowired
    private RegularHistoryService regularHistoryService;

    @Autowired
    private RegularService regularService;

    @RequestMapping(value = "/getPosPay", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取POS支付的状态", notes = "返回系统配置的参数;包含")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "orderno", defaultValue = "", value = "订单编号", required = true)})
    public String getPosPay(String orderNo) {
        Map<String, String> paramMap = applicationEntity.getPay();
        WePay we = new WePay();
        String key = paramMap.get("key");
        String posNo = paramMap.get("posNo");
        String data = posNo + System.currentTimeMillis();
        String sign = EncryptUtils.genHMAC(data, key);
        String requestUrl = "https://pay.jiakevip.com/ppserver/dbwxpay/h5query?out_trade_no=" + orderNo + "&sign=" + sign;
        String responseStr = HttpRequest.sendGet(requestUrl, "");
        HashMap<String, String> hash = we.parse(responseStr);
        String suc = "SUCCESS";
        if (suc.equals(hash.get("return_code"))) {
            if (suc.equals(hash.get("result_code"))) {
                if (suc.equals(hash.get("trade_state"))) {
                    orderCallBack(orderNo, hash.get("total_fee"), ConstantVariable.WX_PAY);
                    return ResultData.toSuccessDataObjString(hash);
                }
            }
        }
        return ResultData.toErrorString(hash, "支付失败，请重新下单");
    }

    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "返回取餐码", notes = "返回系统配置的参数;包含")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "paytype", defaultValue = "1", value = "支付类型,wx:ali", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderno", defaultValue = "", value = "订单编号", required = true)})
    public String pay(String paytype, String orderno) {
        List<Map<String, Object>> returnList = new ArrayList<>();
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, String> paramMap = applicationEntity.getPay();
        String key = paramMap.get("key");
        String posNo = paramMap.get("posNo");
        String data = posNo + System.currentTimeMillis();
        String sign = EncryptUtils.genHMAC(data, key);
        RegularHistoryModel regularHistoryModel = new RegularHistoryModel();
        regularHistoryModel.setOrderno(orderno);

        regularHistoryModel = regularHistoryService.getModelById(regularHistoryModel);
        if (null == regularHistoryModel) {
            OrderinfoModel orderinfoModel = new OrderinfoModel();
            orderinfoModel.setOrderno(orderno);
            orderinfoModel = orderinfoService.getModelById(orderinfoModel);
            returnMap.put("out_trade_no", orderno);
            String price = MathUtil.multiply(orderinfoModel.getSaleprice(), 100);
            price = price.replace(".00", "");
            returnMap.put("total_fee", price);
            returnMap.put("sign", sign);
            returnList.add(returnMap);
            return ResultData.toSuccessString(returnList);
        } else {
            RegularorderModel regularorderModel = JSON.parseObject(regularHistoryModel.getOrderinfo(), RegularorderModel.class);
            returnMap.put("out_trade_no", orderno);
            String price = MathUtil.multiply(regularorderModel.getRegularorderdetailModel().getOrderprice(), 100);
            price = ConstantVariable.CENTS_FORMAT(price);
            returnMap.put("total_fee", price);
            returnMap.put("sign", sign);
            returnList.add(returnMap);
            return ResultData.toSuccessString(returnList);
        }
    }

    @RequestMapping(value = "/alipay", method = {RequestMethod.GET, RequestMethod.POST})
    public void alipay(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = new HashMap<String, String>();

        try {
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            System.out.println("callback_alipay-start:" + JSON.toJSONString(params));
            LOGGER.info("callback_alipay-start");
            if (checkAliPay(request)) {
                String orderId = request.getParameter("out_trade_no");
                String total_fee = MathUtil.multiply(request.getParameter("total_amount"), 100);
                total_fee = ConstantVariable.CENTS_FORMAT(total_fee);
                orderCallBack(orderId, total_fee, ConstantVariable.ALI_PAY);
            }
        } catch (UnsupportedEncodingException | AlipayApiException e) {
            LOGGER.error("callback_alipay:" + e.getMessage());
        }
    }

    @RequestMapping(value = "/wxpay_tardo", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderNo", defaultValue = "1", value = "支付类型,wx:ali", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "userId", defaultValue = "1", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "openId", defaultValue = "o3IQr5Fdo2t-1kCQtBU8NQO5dbVE", value = "支付对应的openid", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "bussinesType", defaultValue = "1", value = "支付类型,1:普通订单;2:定期购订单", required = true)})
    @ResponseBody
    public String start_wxpay(String orderNo, String userId, String openId, String bussinesType) {
        WePay wePay = new WePay();
        String total_fee = null;
        String goods_name = "";
        HashMap<String, String> payHash = new HashMap<>();
        if ("1".equals(bussinesType)) {
            OrderinfoModel orderinfoModel = new OrderinfoModel();
            orderinfoModel.setOrderno(orderNo);
            orderinfoModel = orderinfoService.getModelById(orderinfoModel);
            OrderdetailModel detailModel = new OrderdetailModel();
            detailModel.setOrderno(orderNo);
            detailModel = orderdetailService.getModelById(detailModel);
            if (null == orderinfoModel || null == detailModel) {
                return ResultData.toErrorString("查无此订单");
            }
            Double saleprice = orderinfoModel.getSaleprice();
            total_fee = MathUtil.multiply(saleprice, 100);
            goods_name = detailModel.getVegetablename();
        } else {
            RegularHistoryModel regularHistoryModel = new RegularHistoryModel();
            regularHistoryModel.setOrderno(orderNo);
            regularHistoryModel = regularHistoryService.getModelById(regularHistoryModel);
            RegularorderModel regularorderModel = JSON.parseObject(regularHistoryModel.getOrderinfo(), RegularorderModel.class);
            total_fee = MathUtil.multiply(regularorderModel.getPrice(), 100);
            goods_name = "贝瑞博士-线上消费";
        }
        payHash = wePay.resultSendOrder(orderNo,
                goods_name,
                total_fee.replace(".00", ""),
                "127.0.0.1",
                "http://www.coding88.com/store/api/orderinfo/wxpay",
                ConstantVariable.WE_JSAPI_PAY,
                openId);
        String suc = "SUCCESS";
        payHash.put("ts", "");
        if (suc.equals(payHash.get("return_code"))) {
            return ResultData.toSuccessString(payHash, "下单成功");
        }
        return ResultData.toErrorString(payHash, "下单失败");
    }


    @RequestMapping(value = "/wxpay", method = {RequestMethod.POST})
    @ResponseBody
    public void wxpay(HttpServletRequest request) {
        LOGGER.info("~~~~~~~~~~~~~~wePay:" + JSON.toJSONString(request.getParameterMap()));
        System.out.println("~~~~~~~~~~~~~~wePay:" + JSON.toJSONString(request.getParameterMap()));
        WePay we = new WePay();
        Map<String, String[]> map = request.getParameterMap();
        System.err.println(JSON.toJSONString(map));
        InputStream sis = null;
        BufferedReader bufferedReader = null;
        try {
            String suc = "SUCCESS";
            sis = request.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(sis));
            StringBuilder result = new StringBuilder();
            String line = null;
            while (null != (line = bufferedReader.readLine())) {
                result.append(line + "\n");
            }
            HashMap<String, String> hash = we.parse(result.toString());
            String orderno = hash.get("out_trade_no");
            hash = we.resultQueryOrder(orderno);
            if (suc.equals(hash.get("return_code"))) {
                if (suc.equals(hash.get("result_code"))) {
                    if (suc.equals(hash.get("trade_state"))) {
                        orderCallBack(orderno, hash.get("total_fee"), ConstantVariable.WX_PAY);
                    }
                }
            }

//            String orderno = hash.get("out_trade_no");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sis.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void orderCallBack(String orderId, String total_amount, String payType) {
        RegularHistoryModel regularHistoryModel = new RegularHistoryModel();
        regularHistoryModel.setOrderno(orderId);
        regularHistoryModel = regularHistoryService.getModelById(regularHistoryModel);
        if (null == regularHistoryModel) {
            OrderinfoModel orderinfoModel = new OrderinfoModel();
            orderinfoModel.setOrderno(orderId);
            orderinfoModel = orderinfoService.getModelById(orderinfoModel);
            orderinfoModel.setOrderstatus(ConstantVariable.ORDER_2);
            orderinfoModel.setPaytype(payType);
            orderinfoModel.setPaytime(new Date());
            orderinfoModel.setSaleprice(Double.parseDouble(MathUtil.centsToYuan(total_amount)));
            orderinfoService.update(orderinfoModel);
            OrderdetailModel detailModel = new OrderdetailModel();
            detailModel.setSaleprice(Double.parseDouble(MathUtil.centsToYuan(total_amount)));
            detailModel.setOrderno(orderId);
            orderdetailService.update(detailModel);
            try {
                posUserService.submitOrder(orderId);
            } catch (Exception e) {
                LOGGER.error("submitOrder:" + JSON.toJSONString(orderinfoModel) + "-" + e.getMessage());
            }
        } else {
            String regularOrderInfo = regularHistoryModel.getOrderinfo();
            RegularorderModel regularorderModel = JSON.parseObject(regularOrderInfo, RegularorderModel.class);
            regularorderModel.setPaytype(payType);
            regularorderModel.getRegularorderdetailModel().setPaytype(payType);
            regularService.createRegularOrder(regularorderModel);
        }
    }

    @RequestMapping(value = "/getEatingCode", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "返回取餐码", notes = "返回系统配置的参数;包含")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "申请人ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderno", defaultValue = "1", value = "订单编号", required = true)})
    public String getEatingCode(String userid, String orderno) {
        Map<String, String> returnHash = new HashMap<>();
        String eatingCode = CodeUtil.getRandomNumber(7);
        returnHash.put("code", orderno.substring(orderno.length() - 6, orderno.length()));
        return ResultData.toSuccessDataObj(returnHash);
    }

    @RequestMapping(value = "/getSalesServiceList", method = {RequestMethod.GET})
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "申请人ID", required = true)})
    @ApiOperation(value = "按照用户获取售后列表", notes = "")
    public String getSalesServiceList(String userid) {
        AfterserviceModel model = new AfterserviceModel();
        model.setUserid(userid);
        List<AfterserviceModel> dataList = afterserviceService.getAppList(model);
        return ResultData.toSuccessString(dataList);
    }

    public boolean checkAliPay(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException, UnsupportedEncodingException, AlipayApiException {
        // // 获取支付宝POST过来反馈信息
        // Map<String, String> params = new HashMap<String, String>();
        // Map requestParams = request.getParameterMap();
        // for (Iterator iter = requestParams.keySet().iterator();
        // iter.hasNext();) {
        // String name = (String) iter.next();
        // String[] values = (String[]) requestParams.get(name);
        // String valueStr = "";
        // for (int i = 0; i < values.length; i++) {
        // valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr
        // + values[i] + ",";
        // }
        // // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
        // // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
        // params.put(name, valueStr);
        // }
        // // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // // 商户订单号
        //
        // String out_trade_no = new
        // String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),
        // "UTF-8");
        // // 支付宝交易号
        //
        // String trade_no = new
        // String(request.getParameter("trade_no").getBytes("ISO-8859-1"),
        // "UTF-8");
        //
        // // 交易状态
        // String trade_status = new
        // String(request.getParameter("trade_status").getBytes("ISO-8859-1"),
        // "UTF-8");
        //
        // // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        // // 计算得出通知验证结果
        // // boolean AlipaySignature.rsaCheckV1(Map<String, String> params,
        // String
        // // publicKey, String charset, String sign_type)
        // boolean verify_result = AlipaySignature.rsaCheckV1(params,
        // AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET,
        // "RSA2");
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        // 切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        // boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String
        // publicKey, String charset, String sign_type)
        boolean verify_result = true;//AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
        // 交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
        if (verify_result) {// 验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            // 请在这里加上商户的业务逻辑程序代码
            // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            if (trade_status.equals("TRADE_FINISHED")) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                // 如果有做过处理，不执行商户的业务程序
                // 注意：
                // 如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                // 如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
                return false;
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                // 如果有做过处理，不执行商户的业务程序
                // 注意：
                // 如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
                return true;
            }
            // ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
            //////////////////////////////////////////////////////////////////////////////////////////
            return false;
        } else {// 验证失败
            return false;
        }
    }

    @RequestMapping(value = "/salesService", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "content", defaultValue = "1", value = "售后内容", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "reason", defaultValue = "1", value = "备注", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "申请人ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderno", defaultValue = "1", value = "订单号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "detailid", defaultValue = "1", value = "对应的详情ID", required = true)})
    @ApiOperation(value = "申请售后,上传图片的name是file", notes = "")
    public String salesService(String content, String reason, String userid, @ApiParam(value = "上传图片") MultipartHttpServletRequest request, String orderno, String detailid) {
        try {
            if (StringUtils.isBlank(detailid)) {
                return ResultData.toErrorString("详情订单的编号不能为空");
            }
            AfterserviceModel model = new AfterserviceModel();
            List<MultipartFile> files = request.getFiles("file");
            model.setContent(content);
            model.setReason(reason);
            model.setOrderno(orderno);
            model.setDetailid(detailid);
            model.setUserid(userid);
            model.setStatus(ConstantVariable.SALE_SERVICE_READY_CHECK);
            model.setBackpaystatus(ConstantVariable.SALE_SERVICE_NO_BACK_PAY);
            model.setBackpayno(CodeUtil.getOrderNo(9));
            afterserviceService.add(model, files);
            return ResultData.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/salesServiceNoImg", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "content", defaultValue = "1", value = "售后内容", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "reason", defaultValue = "1", value = "备注", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "申请人ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderno", defaultValue = "1", value = "订单号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "detailid", defaultValue = "1", value = "对应的详情ID", required = true)})
    @ApiOperation(value = "申请售后,不包含图片的空额脸皮", notes = "")
    public String salesServiceNoImg(String content, String reason, String userid, String orderno, String detailid) {
        try {
            if (StringUtils.isBlank(detailid)) {
                return ResultData.toErrorString("详情订单的编号不能为空");
            }

            OrderinfoModel orderinfoModel = new OrderinfoModel();
            orderinfoModel.setOrderno(orderno);
            List orderlist = orderinfoService.getList(orderinfoModel);
            if (CollectionUtils.isEmpty(orderlist)) {
                return ResultData.toErrorString("无效的订单号");
            }
            OrderdetailModel detailModel = new OrderdetailModel();
            detailModel.setDetailid(detailid);
            orderlist = orderdetailService.getList(detailModel);
            if (CollectionUtils.isEmpty(orderlist)) {
                return ResultData.toErrorString("无效的订单详情信息");
            }
            AfterserviceModel model = new AfterserviceModel();
            List<MultipartFile> files = null;//request.getFiles("file");
            model.setContent(content);
            model.setReason(reason);
            model.setOrderno(orderno);
            model.setDetailid(detailid);
            model.setUserid(userid);
            model.setStatus(ConstantVariable.SALE_SERVICE_READY_CHECK);
            model.setBackpaystatus(ConstantVariable.SALE_SERVICE_NO_BACK_PAY);
            model.setBackpayno(CodeUtil.getOrderNo(9));
            afterserviceService.add(model, files);
            return ResultData.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/editOrder", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "订单编号", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderstatus", defaultValue = "1", value = "orderstatus", required = false)})
    @ApiOperation(value = "按照用户获取订单的列表信息", notes = "按照用户获取订单的列表信息;订单状态(orderstatus):1:待付款;2:待发货;3:配送中;4:待提货;5:待评价;6:已取消;查询全部不传即可")
    public String editOrder(String orderno, String orderstatus, String paytype) {
        try {
            OrderinfoModel orderinfoModel = new OrderinfoModel();
            orderinfoModel.setOrderno(orderno);
            orderinfoModel = orderinfoService.getModelById(orderinfoModel);
            orderinfoModel.setOrderstatus(orderstatus);
            orderinfoModel.setPaytype(paytype);
            orderinfoModel.setPaytime(new Date());
            orderinfoService.update(orderinfoModel);
            OrderdetailModel detailModel = new OrderdetailModel();
            detailModel.setOrderno(orderno);
            return ResultData.toSuccessString();
        } catch (Exception e) {
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/deleteOrder", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderno", defaultValue = "1", value = "订单编号", required = true)})
    @ApiOperation(value = "删除订单，只能删除状态为1,5,6的", notes = "按照用户获取订单的列表信息;订单状态(orderstatus):1:待付款;2:待发货;3:配送中;4:待提货;5:待评价;6:已取消;查询全部不传即可")
    public String deleteOrder(String userid, String orderno) {
        try {
            orderinfoService.deleteOrder(userid, orderno);
            return ResultData.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString(e.getMessage());
        }
    }

    @RequestMapping(value = "/getOrderList", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户ID", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderstatus", defaultValue = "", value = "orderstatus", required = false)})
    @ApiOperation(value = "按照用户获取订单的列表信息", notes = "按照用户获取订单的列表信息;订单状态(orderstatus):1:待付款;2:待发货;3:配送中;4:待提货;5:待评价;6:已取消;查询全部不传即可")
    public String getOrderList(String userid, @RequestParam(defaultValue = "", required = false) String orderstatus) {
        OrderinfoModel orderModel = new OrderinfoModel();
        orderModel.setUserid(userid);
        orderModel.setOrderstatus(orderstatus);
        List<OrderInfoVO> orderList = orderinfoService.getAppOrderList(orderModel);
        return ResultData.toSuccessString(orderList);
    }

    @RequestMapping(value = "/balanceByCard", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户ID", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "cardno", defaultValue = "MEMB20180810000004", value = "打折卡编号", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderno", defaultValue = "1", value = "订单编号", required = false)})
    @ApiOperation(value = "按照用户获取订单的列表信息", notes = "按照用户获取订单的列表信息;订单状态(orderstatus):1:待付款;2:待发货;3:配送中;4:待提货;5:待评价;6:已取消")
    public String balanceByCard(String userid, String cardno, String orderno) {
        return null;
    }

    @RequestMapping(value = "/getOrderDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderno", defaultValue = "1", value = "订单号", required = true)})
    @ApiOperation(value = "获取订单详情;sendtype:配送类型;1:店内用餐;2:打包带走;3:外送")
    public String getOrderDetail(String userid, String orderno) {
        try {
            OrderinfoModel orderModel = new OrderinfoModel();
            orderModel.setUserid(userid);
            orderModel.setOrderno(orderno);
            OrderInfoVO vo = orderinfoService.getOrderDetail(orderModel);
            return ResultData.toSuccessDataObj(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/cancelOrder", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户ID", required = false)})
    @ApiOperation(value = "取消订单", notes = "取消订单")
    public String cancelOrder(String userid, String orderno) {
        try {
            OrderinfoModel orderModel = new OrderinfoModel();
            orderModel.setUserid(userid);
            orderModel.setOrderno(orderno);
            String msg = orderinfoService.cancelOrder(orderModel);
            if (StringUtils.isBlank(msg)) {
                return ResultData.toSuccessString();
            } else {
                return ResultData.toErrorString(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/evalOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "49897871224ca0fd072c319dbdec1a1b", value = "用户编号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderno", defaultValue = "201809182002461623804", value = "订单编号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "evalinfo", defaultValue = "[{vid:\"2ab858c0-a1e5-11e8-afd7-00ff57a662b1\",evalscore:\"3\",evalcontent:\"AAAA\"},{vid:\"36d53b04-a1e5-11e8-afd7-00ff57a662b1\",evalscore:\"3\",evalcontent:\"AAAA\"},{vid:\"43424ce8-a1e5-11e8-afd7-00ff57a662b1\",evalscore:\"3\",evalcontent:\"AAAA\"}]", value = "评价的详情,传入json数组", required = true)})
    @ApiOperation(value = "评价订单", notes = "取消订单")
    public String evalOrder(String userid, String orderno, String evalinfo) {
        try {
            List<JSONObject> jsonList = JSON.parseObject(evalinfo, List.class);
            List<OrderevalModel> evalList = new ArrayList<>();
            if (null == jsonList || jsonList.size() == 0) {
                return ResultData.toSuccessString();
            }
            OrderinfoModel orderinfoModel = new OrderinfoModel();
            orderinfoModel.setOrderno(orderno);
            List orderList = orderinfoService.getList(orderinfoModel);
            if (CollectionUtils.isEmpty(orderList)) {
                return ResultData.toErrorString("订单号无效");
            }

            List<String> productIdList = new ArrayList<>();
            for (JSONObject jsonObject : jsonList) {
                OrderevalModel evalModel = new OrderevalModel();
                evalModel.setVegetable(jsonObject.get("vid").toString());
                evalModel.setEvalcontent(jsonObject.get("evalcontent").toString());
                evalModel.setEvalscore(jsonObject.get("evalscore").toString());
                evalModel.setUserid(userid);
                evalModel.setOrderno(orderno);
                productIdList.add(evalModel.getVegetable());
                evalList.add(evalModel);
            }
            orderevalService.evalOrder(evalList, productIdList, userid, orderno);
            return ResultData.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString(e.getMessage());
        }
    }

//    @RequestMapping(value = "/getOrderDetailList", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户ID", required = false),
//            @ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "订单号", required = false)})
//    @ApiOperation(value = "按照订单号获取订单详情列表", notes = "")
//    public String getOrderDetailList(String userid, String orderno) {
//        try {
//            OrderinfoModel orderModel = new OrderinfoModel();
//            orderModel.setUserid(userid);
//            orderModel.setOrderno(orderno);
//            List<OrderDetailVO> detailList = orderinfoService.getOrderDetailList(orderModel);
//            return ResultData.toSuccessDataObj(detailList);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultData.toErrorString();
//        }
//    }

    //    @RequestMapping(value = "/orderList", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户ID", required = false)})
//    @ApiOperation(value = "按照用户获取订单的列表信息", notes = "按照用户获取订单的列表信息;订单状态(orderstatus):1:待付款;2:待发货;3:配送中;4:待提货;5:待评价;6:已取消")
    public String orderList(String userid) {
        try {
            OrderinfoModel orderinfoModel = new OrderinfoModel();
            orderinfoModel.setUserid(userid);
            orderinfoModel.noPage();
            List<OrderInfoVO> voList = orderinfoService.getListToApp(orderinfoModel);
            return ResultData.toSuccessString(voList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/createOrderByShopcar", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "49897871224ca0fd072c319dbdec1a1b", value = "用户ID", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "addressid", defaultValue = "bed48851-b320-11e8-bd09-00ff57a662b1", value = "地址ID", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "carid", defaultValue = "4868ded0-ba90-11e8-ae12-00ff57a662b1,4bf5f25f-ba90-11e8-ae12-00ff57a662b1,cde714b3-ba8f-11e8-ae12-00ff57a662b1", value = "购物车的ID", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "storeid", defaultValue = "5729014c-aeaa-11e8-905c-00ff57a662b1", value = "门店的ID", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "sendtype", defaultValue = "1", value = "配送类型;1:店内用餐;2:打包带走;3:外送", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "paytype", defaultValue = "1", value = "支付方式;1:支付宝;2:微信;3:打折卡", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "sendinfo", defaultValue = "1", value = "配送信息", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "cardno", defaultValue = "MEMB20180810000004", value = "打折卡的卡号", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "sendtime", defaultValue = "2018-09-10 08:00-10:00", value = "需要配送的时间段(2018-09-01 08:00-10:00)", required = false)})
    @ApiOperation(value = "按购物车进行结算", notes = "按购物车进行结算")
    public String createOrderByShopcar(String userid, String addressid, String storeid, String sendtype, String sendtime, String carid, String paytype, @RequestParam(value = "", required = false) String sendinfo, @RequestParam(value = "", required = false) String cardno) {
        try {
            OrderinfoModel orderModel = new OrderinfoModel();
            orderModel.setOrderno(CodeUtil.getOrderNo(1));
            orderModel.setSendtype(sendtype);
            orderModel.setSendtime(sendtime);
            orderModel.setUserid(userid);
            orderModel.setAddressid(addressid);
            orderModel.setStoreid(storeid);
            orderModel.setPaytype(paytype);
            orderModel.setCardno(cardno);
            orderModel.setSendinfo(sendinfo);
            orderinfoService.createOrderByShopcar(orderModel, carid);
            return ResultData.toSuccessString(orderModel);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/createOrderByProduct", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "49897871224ca0fd072c319dbdec1a1b", value = "用户ID", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "addressid", defaultValue = "41645303-a948-11e8-bd6a-00ff57a662b1", value = "地址ID", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "storeid", defaultValue = "5729014c-aeaa-11e8-905c-00ff57a662b1", value = "门店的ID", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "sendtype", defaultValue = "1", value = "配送类型;1:店内用餐;2:打包带走;3:外送", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "sendtime", defaultValue = "1", value = "需要配送的时间段(2018-09-01 08:00-10:00)", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "paytype", defaultValue = "1", value = "支付方式;1:支付宝;2:微信;3:打折卡", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "sendinfo", defaultValue = "1", value = "配送信息", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "cardno", defaultValue = "MEMB20180810000004", value = "打折卡的卡号", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "vid", defaultValue = "36d53b04-a1e5-11e8-afd7-00ff57a662b1", value = "商品ID", required = false)})
    @ApiOperation(value = "按单件商品进行结算", notes = "按单件商品进行结算")
    public String createOrderByProduct(String userid, String addressid, String storeid, String sendtype, String sendtime, String vid, String paytype, String sendinfo, String cardno) {
        try {

            OrderinfoModel orderModel = new OrderinfoModel();
            orderModel.setOrderno(CodeUtil.getOrderNo(1));
            orderModel.setSendtype(sendtype);
            orderModel.setSendtime(sendtime);
            orderModel.setUserid(userid);
            orderModel.setAddressid(addressid);
            orderModel.setStoreid(storeid);
            orderModel.setPaytype(paytype);
            orderModel.setSendinfo(sendinfo);
            orderModel.setCardno(cardno);
            orderinfoService.createOrderByProduct(orderModel, vid);
            return ResultData.toSuccessDataObj(orderModel);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString("");
        }
    }

}