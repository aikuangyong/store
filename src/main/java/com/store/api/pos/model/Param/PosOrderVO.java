package com.store.api.pos.model.Param;

import com.store.utils.ConstantVariable;
import com.store.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PosOrderVO {

    private String total_fee;
    private String paid_fee;
    private String coupon_fee;
    private String order_no;
    private String pay_type;
    private String pay_time;
    private String send_type;
    private String remark;
    private String nick_name;
    private List<PosGoodInfo> goods;
    private String store_name;
    private String order_price;
    private String sale_price;
    private String store_code;
    private String mobile;

    public PosOrderVO(PosOrderInfo orderInfo){

        this.total_fee = orderInfo.getTotal_fee();
        this.paid_fee = orderInfo.getPaid_fee();
        this.coupon_fee = orderInfo.getCoupon_fee();
        this.order_no = orderInfo.getOrder_no();
        if(ConstantVariable.WX_PAY.equals(pay_type)){
            this.pay_type = "微信";
        }else if(ConstantVariable.ALI_PAY.equals(pay_type)){
            this.pay_type = "支付宝";
        }else if(ConstantVariable.CARD_PAY.equals(pay_type)){
            this.pay_type = "打折卡";
        }else {
            this.pay_type = "";
        }

        //配送方式;1:店内用餐;2:打包带走;3:外送
        if ("1".equals(orderInfo.getSend_type())) {
            this.send_type = "店内用餐";
        } else if ("2".equals(orderInfo.getSend_type())) {
            this.send_type = "打包带走";
        } else {
            this.send_type = "外送";
        }

        if(StringUtils.isBlank(orderInfo.getPay_time())){
            this.pay_time = "";
        }else {
            this.pay_time = orderInfo.getPay_time();
        }
        this.remark = orderInfo.getRemark();
        this.nick_name = orderInfo.getNick_name();
        this.mobile = orderInfo.getMobile();
        this.store_name = orderInfo.getStore_name();
        this.store_code = orderInfo.getStore_code();

    }

}