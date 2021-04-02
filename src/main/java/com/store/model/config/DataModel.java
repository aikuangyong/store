package com.store.model.config;

import com.alibaba.fastjson.JSON;
import com.store.utils.ConstantVariable;

public class DataModel<T>
{
    private String state;

    private String message;

    private String url;

    private T data;

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }


    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public static String returnSuccess()
    {
        DataModel<String> dataModel = new DataModel<String>();
        dataModel.setState(ConstantVariable.SUCCESS);
        dataModel.setData("");
        dataModel.setMessage("");
        return JSON.toJSONString(dataModel);
    }

    public static String returnSuccessForMessage(String msg)
    {
        DataModel<?> dataModel = new DataModel<String>();
        dataModel.setMessage(msg);
        dataModel.setState(ConstantVariable.SUCCESS);
        return JSON.toJSONString(dataModel);
    }

    public static String returnSuccess(Object t)
    {
        DataModel<Object> dataModel = new DataModel<Object>();
        dataModel.setData(t);
        dataModel.setMessage("");
        dataModel.setState(ConstantVariable.SUCCESS);
        return JSON.toJSONString(dataModel);
    }

    public String returnError()
    {
        DataModel<String> dataModel = new DataModel<String>();
        dataModel.setData(null);
        dataModel.setMessage("");
        dataModel.setState(ConstantVariable.ERROR);
        return JSON.toJSONString(dataModel);
    }

    public static String returnError(String msg)
    {
        DataModel<String> dataModel = new DataModel<String>();
        dataModel.setData(null);
        dataModel.setMessage(msg);
        dataModel.setState(ConstantVariable.ERROR);
        return JSON.toJSONString(dataModel);
    }

    @Override
    public String toString()
    {
        return JSON.toJSONString(this);
    }

}