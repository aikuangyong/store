package com.store.api.pos;

import com.alibaba.fastjson.JSON;
import com.store.api.pos.model.Param.PosBaseModel;
import com.store.api.pos.model.Param.PosOrderDetail;
import com.store.api.pos.model.PosReturnModel;
import com.store.controller.BaseController;
import com.store.model.OrderinfoModel;
import com.store.model.RegularorderdetailModel;
import com.store.model.RegularsuborderModel;
import com.store.model.pos.QueryPosModel;
import com.store.service.OrderinfoService;
import com.store.service.RegularsuborderService;
import com.store.utils.ConstantVariable;
import com.store.utils.DateUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.DateUtils;

import java.util.UUID;

@Controller
@RequestMapping("/api/pos/order")
@Api(value = "订单相关的数据", tags = "订单相关")
public class ApiPosOrder extends BaseController {

    @Autowired
    private OrderinfoService orderinfoService;

    @Autowired
    private RegularsuborderService suborderService;

    @ApiOperation(value = "查询店铺未处理订单", notes = "")
    @RequestMapping(value = "/getUnprocessOrders", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "param", defaultValue = "{\"shopId\":\"795624\"}", value = "请求参数,业务相关", required = false)})
    @ResponseBody
    public String getUnprocessOrders(@RequestParam(value = "param", required = false, defaultValue = "{}") String param, PosBaseModel posBaseModel) {
        String id = UUID.randomUUID().toString();
        try {
            validateSign(posBaseModel);
            QueryPosModel queryPosModel = JSON.parseObject(param, QueryPosModel.class);
            queryPosModel.setSendTime(DateUtil.getDay());
            PosReturnModel returnModel = orderinfoService.getOrderIdList(id, queryPosModel);
            return returnModel.toString();
        } catch (ValidatePosException e) {
            return PosReturnModel.returnError(id, "VALIDATION_FAILED", e.getMessage()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return PosReturnModel.returnError(id, "SYSTEM_ERROE", "系统内部异常").toString();
        }
    }

    @ApiOperation(value = "获取订单", notes = "")
    @RequestMapping(value = "/getOrder", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "param", defaultValue = "{\"orderId\":\"0a0faa89475537f26cce3257fb121ccd\"}", value = "请求参数,业务相关", required = false)})
    @ResponseBody
    public String list(@RequestParam(value = "param", required = false, defaultValue = "{\"orderId\":\"1\"}") String param, PosBaseModel posBaseModel) {
        String id = UUID.randomUUID().toString();
        try {
            validateSign(posBaseModel);
            QueryPosModel queryPosModel = JSON.parseObject(param, QueryPosModel.class);
            PosOrderDetail posOrderDetail = orderinfoService.getOrderDetail(queryPosModel);
            return PosReturnModel.returnSuccess(id, posOrderDetail).toString();
        } catch (ValidatePosException e) {
            return PosReturnModel.returnError(id, "VALIDATION_FAILED", e.getMessage()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return PosReturnModel.returnError(id, "SYSTEM_ERROE", "系统内部异常").toString();
        }
    }

    public static void main(String[] args) {
        QueryPosModel m = new QueryPosModel();
        m.setPage_no(1);
        m.setPage_size(50);
        m.setEnd_time("2018-12-01");
        m.setStart_time("2018-08-30");
        m.setMobile("18091795525");
        m.setOrder_status("1");
        m.setStore_code("");
        System.out.println(JSON.toJSONString(m));
    }

    @ApiOperation(value = "确认订单", notes = "")
    @RequestMapping(value = "/confirmOrderLite", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "param", defaultValue = "{\"orderId\":\"1\"}", value = "请求参数,业务相关", required = false)})
    @ResponseBody
    public String edit(@RequestParam(value = "", defaultValue = "{\"orderId\":\"1\"}", required = false) String param, PosBaseModel posBaseModel) {
        String id = UUID.randomUUID().toString();
        try {
            validateSign(posBaseModel);
            QueryPosModel queryPosModel = JSON.parseObject(param, QueryPosModel.class);
            RegularsuborderModel suborderModel = new RegularsuborderModel();
            //1:待配送;2:配送中;3:配送完成;4:已取消
            suborderModel.setStatus(ConstantVariable.ORDER_2);
            suborderModel.setSuborderid(queryPosModel.getOrderId());
            suborderService.update(suborderModel);
            return PosReturnModel.returnSuccess(id, null).toString();
        } catch (ValidatePosException e) {
            return PosReturnModel.returnError(id, "VALIDATION_FAILED", e.getMessage()).toString();
        } catch (Exception e) {
            return PosReturnModel.returnError(id, "SYSTEM_ERROE", "系统异常").toString();
        }
    }
}