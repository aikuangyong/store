package com.store.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.store.utils.ConstantVariable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultData {

    private Object dataList;

    private Object dataObj;

    private int pageCount;

    private int dataCount;

    private int pageNumber;

    private boolean success;

    private String state;

    private String msg;

    private String detail;

    public ResultData(boolean success, String state, String msg) {
        this.success = success;
        this.state = state;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
    }

    public void setDataList(Object dataList) {
        this.dataList = dataList;
    }

    public static ResultData toResultData(boolean success, String state, String msg) {
        ResultData resultData = new ResultData();
        resultData.setSuccess(success);
        resultData.setState(state);
        resultData.setMsg(msg);
        return resultData;
    }

    public static String toErrorString() {
        ResultData resultData = new ResultData();
        resultData.setState(ConstantVariable.ERROR);
        resultData.setMsg("服务异常,请稍后再试");
        resultData.setSuccess(false);
        return resultData.toString();
    }

    public static String toErrorString(String msg) {
        ResultData resultData = new ResultData();
        resultData.setState(ConstantVariable.ERROR);
        resultData.setMsg(msg);
        resultData.setSuccess(false);
        return resultData.toString();
    }

    public static String toErrorString(Object obj, String msg) {
        ResultData resultData = new ResultData();
        resultData.setState(ConstantVariable.ERROR);
        resultData.setMsg(msg);
        resultData.setSuccess(false);
        resultData.setDataObj(obj);
        return resultData.toString();
    }

    public static String toSuccessString() {
        ResultData resultData = new ResultData();
        resultData.setState(ConstantVariable.SUCCESS);
        resultData.setSuccess(true);
        return resultData.toString();
    }

    public static String toSuccessString(Object obj) {
        ResultData resultData = new ResultData();
        resultData.setState(ConstantVariable.SUCCESS);
        resultData.setDataList(obj);
        if (obj instanceof Collection) {
            Collection listObj = (Collection) obj;
            resultData.setDataCount(listObj.size());
        }
        return resultData.toString();
    }

    public static String toSuccessDataObjString(Object obj) {
        ResultData resultData = new ResultData();
        resultData.setState(ConstantVariable.SUCCESS);
        resultData.setDataObj(obj);
        return resultData.toString();
    }


    public static String toSuccessString(Object obj, Object dataObj) {
        ResultData resultData = new ResultData();
        resultData.setState(ConstantVariable.SUCCESS);
        resultData.setDataList(obj);
        resultData.setDataObj(dataObj);
        if (obj instanceof Collection) {
            Collection listObj = (Collection) obj;
            resultData.setDataCount(listObj.size());
        }
        resultData.setSuccess(true);
        return resultData.toString();
    }

    public static String toSuccessDataObj(Object obj) {
        ResultData resultData = new ResultData();
        resultData.setState(ConstantVariable.SUCCESS);
        resultData.setDataObj(obj);
        resultData.setSuccess(true);
        return resultData.toString();
    }

    public static String toSuccessDataObj(Object obj, String msg) {
        ResultData resultData = new ResultData();
        resultData.setState(ConstantVariable.SUCCESS);
        resultData.setDataObj(obj);
        resultData.setMsg(msg);
        resultData.setSuccess(true);
        return resultData.toString();
    }

    public Object getDataList() {
        return dataList;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int dataCount, int pageSize) {
        this.pageCount = dataCount / pageSize;
        if (dataCount % pageSize != 0) {
            this.pageCount = this.pageCount + 1;
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}