package com.store.api;

import com.alibaba.fastjson.JSON;
import com.store.model.ResultData;
import com.store.model.pos.PosData;
import com.store.model.pos.PosUser;
import com.store.service.CustomerService;
import com.store.service.OrderinfoService;
import com.store.service.PosUserService;
import com.store.utils.CodeUtil;
import com.store.utils.ConstantVariable;
import com.store.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.jsoup.helper.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/pos")
@Api(value = "获取POS的数据", tags = "获取POS的数据")
public class ApiPos {

    @Autowired
    private PosUserService posUserService;

    @Autowired
    private OrderinfoService orderinfoService;

    @Autowired
    private CustomerService customerService;

    @ResponseBody
    @RequestMapping(value = "/getStockRealtime", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "goodsNo", defaultValue = "MEMB20180810000004", value = "支付卡的卡号", required = true)})
    @ApiOperation(value = "商品编号", notes = "商品编号")
    public String getStockRealtime(String goodsNo) {
        return ResultData.toSuccessString(posUserService.getStockRealtime(goodsNo));
    }

    @ResponseBody
    @RequestMapping(value = "/getConsumeInfo", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "49897871224ca0fd072c319dbdec1a1b", value = "用户编号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "cardno", defaultValue = "MEMB20180810000004", value = "支付卡的卡号", required = true)})
    @ApiOperation(value = "获取消费信息;type:1消费;2:充值", notes = "取消订单")
    public String getConsumeInfo(String userid, String cardno) {
        return ResultData.toSuccessString(orderinfoService.getCounsumeInfo(userid, cardno));
    }

    @ResponseBody
    @RequestMapping(value = "/consumeCard", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "49897871224ca0fd072c319dbdec1a1b", value = "用户编号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderno", defaultValue = "201809271334531636668", value = "订单号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "ordertype", defaultValue = "1", value = "订单类型;1:普通订单;2:定期购订单", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "cardno", defaultValue = "MEMB20180810000004", value = "支付卡的卡号", required = true)})
    @ApiOperation(value = "会员储值消费", notes = "取消订单")
    public String consumeCard(String userid, String orderno, String ordertype, String cardno) {
        PosData posData = orderinfoService.consumeCard(userid, orderno, ordertype, cardno);
        if (PosData.ERROR.equals(posData.getCode())) {
            return ResultData.toErrorString(posData.getMessage());
        } else {
            return ResultData.toSuccessString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/editCard", method = RequestMethod.POST)
    public String editCard(String param) {
        try {
            List<Map<String, Object>> dataList = JSON.parseObject(param, List.class);
            for (Map<String, Object> dataMap : dataList) {
                String mobile = String.valueOf(dataMap.get("mobile"));
                String cardno = String.valueOf(dataMap.get("wxConsumeNo"));
                String nowPrice = String.valueOf(dataMap.get("nowPrice"));
                String remark = DateUtil.getTimeStr();
                PosData posData = posUserService.updateCard(mobile, cardno, remark, String.valueOf(nowPrice));
            }
            return ResultData.toSuccessString();
        } catch (Exception e) {
            return ResultData.toErrorString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getPaidConsumeLog", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户编号", required = true)})
    @ApiOperation(value = "会员储值消费记录", notes = "")
    public String getPaidConsumeLog(String userid) {
        return ResultData.toSuccessString(orderinfoService.getPaidConsumeLog(userid));
    }

    @ResponseBody
    @RequestMapping(value = "/getCardList", method = RequestMethod.GET)
    @ApiOperation(value = "获取会员卡列表", notes = "会员卡绑定")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "49897871224ca0fd072c319dbdec1a1b", value = "用户id", required = true)})
    public String getCardList(String userid) {
        try {
            return ResultData.toSuccessString(customerService.getCardList(userid));
        } catch (Exception e) {
            return ResultData.toErrorString(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/bindCard", method = RequestMethod.POST)
    @ApiOperation(value = "会员卡绑定", notes = "会员卡绑定")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "49897871224ca0fd072c319dbdec1a1b", value = "用户id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "cardno", defaultValue = "MEMB20180810000004", value = "卡号", required = true)})
    public String bindCard(String userid, String cardno) {
        try {
            return ResultData.toSuccessString(posUserService.bindCard(userid, cardno));
        } catch (IllegalArgumentException e) {
            return ResultData.toErrorString(e.getMessage());
        } catch (Exception e) {
            return ResultData.toErrorString("绑定失败，请确认卡号是否无误。");
        }
    }

}