package com.store.model.pos;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Data
public class PosData<T> {

    public static final String ERROR = "0";
    public static final String SUCCESS = "1";
    private String message;
    private String code;
    private JSONObject result;
    private List<JSONObject> resultList;

    public T getResult(Class cls) {
        return JSON.parseObject(result.toJSONString(), (Type) cls);// null;//JSON.toJavaObject() ;//,PosCustomer.class);
        //return JSON.parseObject(result.toJSONString(),t.getClass());
    }

    public List getResultList(Class cls) {
        if(null == resultList){
            return new ArrayList();
        }
        return JSON.parseArray(resultList.toString(), cls);// null;//JSON.toJavaObject() ;//,PosCustomer.class);
        //return JSON.parseObject(result.toJSONString(),t.getClass());
    }

    public static final PosData error(String msg) {
        PosData posData = new PosData();
        posData.setCode(ERROR);
        posData.setMessage(msg);
        return posData;
    }

    public static final PosData toReturn(PosData posData) {
        if (ERROR.equals(posData.getCode())) {
            throw new NullPointerException(posData.getMessage());
        }
        return posData;
    }
}
