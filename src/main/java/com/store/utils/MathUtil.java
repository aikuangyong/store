package com.store.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MathUtil {

    public static final DecimalFormat df = new DecimalFormat("#0.00");


    public static String discount(String rate, String price) {
        if ("100".equals(rate)) {
            return price;
        }
        return MathUtil.divide(MathUtil.multiply(rate, price), 100);
    }

    public static String centsToYuan(String price) {
        String pay_fee = MathUtil.divide(price, "100");
        return ConstantVariable.PRICE_FORMAT.format(Double.parseDouble(pay_fee));
    }

    public static String yuanToCents(String price) {
        String pay_fee = MathUtil.multiply(price, "100");
        return ConstantVariable.CENTS_FORMAT.format(Double.parseDouble(pay_fee));
    }

    public static String add(Object val1, Object val2) {
        BigDecimal big1 = new BigDecimal(String.valueOf(val1));
        BigDecimal big2 = new BigDecimal(String.valueOf(val2));
        return df.format(big1.add(big2));
    }

    public static String sub(Object val1, Object val2) {
        BigDecimal big1 = new BigDecimal(String.valueOf(val1));
        BigDecimal big2 = new BigDecimal(String.valueOf(val2));
        return df.format(big1.subtract(big2));
    }

    /**
     * 除法
     *
     * @param val1
     * @param val2
     * @return
     */
    public static String divide(Object val1, Object val2) {
        BigDecimal big1 = new BigDecimal(String.valueOf(val1));
        BigDecimal big2 = new BigDecimal(String.valueOf(val2));
        return df.format(big1.divide(big2));
    }

    /**
     * 乘法
     *
     * @param val1
     * @param val2
     * @return
     */
    public static String multiply(Object val1, Object val2) {
        BigDecimal big1 = new BigDecimal(String.valueOf(val1));
        BigDecimal big2 = new BigDecimal(String.valueOf(val2));
        return df.format(big1.multiply(big2));
    }
}