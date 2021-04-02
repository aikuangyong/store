package com.store.utils;

import java.text.DecimalFormat;

public class ConstantVariable {


    public final static String IMG_HOST_URL = "http://static.coding88.com/upload/";

    public final static String APP_IMG_STYLE = "<style>.rich-img{width:100%;}</style>";

    public final static String VERIFY_CODE = "verifyCode";


    public final static DecimalFormat PRICE_FORMAT = new DecimalFormat("#.00");

    public final static DecimalFormat SCORE_FORMAT = new DecimalFormat("#.0");

    public final static DecimalFormat CENTS_FORMAT = new DecimalFormat("0");

    public static String CENTS_FORMAT(Object obj) {
        if (null == obj) {
            return "1";
        }
        try {
            Double dl = Double.parseDouble(String.valueOf(obj));
            String returnVal = CENTS_FORMAT.format(dl);
            if ("0".equals(returnVal)) {
                return "1";
            }
            return returnVal;
        } catch (Exception e) {
            return "1";
        }
    }

    public final static String ADMIN_USER = "adminUser";

    public final static String SUPER_ADMIN = "admin";

    public final static String PARAM = "param";

    public final static String PARAM_MAP = "paramMap";

    public final static String PARAM_LIST = "paramList";

    public final static String TYPE_JAVA = "1";

    public final static String TYPE_WEB = "1";

    public final static String TYPE_FRAME = "1";

    public final static String LIST_MAIN = "mainList";

    public final static String LIST_JAVA = "javaList";

    public final static String LIST_WEB = "webList";

    public final static String LIST_FRAME = "frameList";

    public final static String LIST_HOT = "hotList";

    public static final String SUCCESS = "SUCCESS";

    public static final String ERROR = "ERROR";

    public static final String EXCEL_PREFIX = ".xlsx";

    public static final String LOGIN_PWD = "1";

    public static final String LOGIN_SMS = "2";

    public static final String LOGIN_WX = "3";

    //1注册2忘记密码3绑定账号4登录
    public static final String SMS_REGISTER = "1";

    public static final String SMS_FORGOT_PWD = "2";

    public static final String SMS_BIND = "3";

    public static final String SMS_LOGIN = "4";

    public static final String PRODUCT_ONLINE = "1";

    public static final String PRODUCT_UNONLINE = "2";

    //1首页2菜单
    public static final String LOOP_INDEX = "1";

    public static final String LOOP_TYPE = "2";

    public static final String SEND_COST = "sendCost";

    public static final String SEND_RANGE = "sendRange";

    public static final String SPLIT = "###";

    public static final String VALID_ENABLE = "1";

    public static final String WE_JSAPI_PAY = "JSAPI";

    public static final String WE_NATIVE_PAY = "NATIVE";

    public static final String WE_MWEB_PAY = "MWEB";


    public static final String VALID_DISABLE = "2";
    public static final String ORDER_READY_PAY = "1";
    public static final String ORDER_2 = "2";
    public static final String ORDER_3 = "3";
    public static final String ORDER_READY_PULL = "4";
    public static final String ORDER_READY_EVAL = "5";
    public static final String ORDER_CANCEL = "6";
    public static final String ORDER_FINSH = "7";

    public static final String WX_PAY = "1";
    public static final String ALI_PAY = "2";
    public static final String CARD_PAY = "3";

    public static final String CLEAR_REGULAR_ORDER_NO_VERIFY = "1";
    public static final String CLEAR_REGULAR_ORDER_NO_VERIFY_PASS = "2";
    public static final String CLEAR_REGULAR_ORDER_NO_VERIFY_NO_PASS = "3";

    public static final String SALE_SERVICE_READY_CHECK = "1";
    public static final String SALE_SERVICE_READY_CHECK_PASS = "2";
    public static final String SALE_SERVICE_READY_CHECK_NO_PASS = "3";
    public static final String SALE_SERVICE_NO_BACK_PAY = "1";
    public static final String SALE_SERVICE_BACK_PAY = "2";


    //1待配送2配送中3配送完成4已取消
    public static final String SUB_ORDER_READY = "1";

    public static final String SUB_ORDER_CURRENT = "2";
    public static final String SUB_ORDER_FINSH = "3";
    public static final String SUB_ORDER_CANCEL = "4";

    public static final String SPLIT_COMMA = ",";

}