package com.store.api.pos.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PosReturnModel {

    private static final String ERROR = "error";
    private static final String SUCCESS = "success";

    private Object result;
    private String id;
    private Map<String, String> error;

    public static PosReturnModel returnSuccess(String id, Object obj) {
        PosReturnModel returnModel = new PosReturnModel();
        returnModel.setId(id);
        returnModel.setResult(obj);
        returnModel.setError(null);
        return returnModel;
    }

    public static PosReturnModel returnError(String id, String code, String message) {
        PosReturnModel returnModel = new PosReturnModel();
        returnModel.setId(id);
        returnModel.setError(getError(code, message));
        return returnModel;
    }

    public static Map<String, String> getError(String code, String message) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("code", code);
        errorMap.put("message", message);
        return errorMap;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
    }
}