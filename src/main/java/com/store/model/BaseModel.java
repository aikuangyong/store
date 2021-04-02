package com.store.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseModel {

    @ApiModelProperty(notes = "排序字段")
    private String orderColumn;

    @ApiModelProperty(notes = "类型")
    private String orderType;

    @ApiModelProperty(hidden = true)
    private List<String> idList;

    public Map<String, Object> toMap(Object obj) {
        Map<String, Object> returnMap = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
            Object objVal = getValue(obj, methodName, field);
            returnMap.put(fieldName, objVal);
        }
        return returnMap;
    }

    private Object getValue(Object obj, String methodName, Field field) {
        Method m = null;
        try {
            m = obj.getClass().getMethod(methodName, field.getType().getClasses());
            return m.invoke(obj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return "";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "";
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return "";
        }
    }
}