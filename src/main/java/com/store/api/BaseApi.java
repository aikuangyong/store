package com.store.api;

import com.store.model.config.ApplicationEntity;
import com.store.model.config.FieldModel;
import com.store.utils.GenerateFile;
import com.store.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/baseApi")
public class BaseApi {

    @Autowired
    private GenerateFile generateFile;

    @Autowired
    private ApplicationEntity applicationEntity;

    @RequestMapping("/generateApi")
    @ResponseBody
    public String generateApiTest() {
        List<Map<String, Object>> mainList = new ArrayList<>();
        try {
            String classSuffix = "com.store.api.";
            String[] classStr = new String[]{"LoginApi"};
            for (String strCls : classStr) {
                Object cls = Class.forName(classSuffix + strCls).newInstance();
                Method[] methods = cls.getClass().getDeclaredMethods();
                Annotation[] clsAnnos = cls.getClass().getDeclaredAnnotations();
                String controlUrl = "";
                for (Annotation anno : clsAnnos) {
                    if (anno.annotationType().equals(RequestMapping.class)) {
                        RequestMapping rqm = ((RequestMapping) anno);
                        controlUrl = rqm.value()[0];
                    }
                }
                for (Method method : methods) {
                    Map<String, Object> detailMap = new HashMap<>();
                    String apiName = "";
                    List<FieldModel> fieldList = new ArrayList<>();
                    Annotation[] annos = method.getDeclaredAnnotations();
                    String htmlName = strCls + "_" + method.getName();
                    for (Annotation anno : annos) {
                        if (anno.annotationType().equals(Fields.class)) {
                            FieldAttr[] fieldAttrs = ((Fields) anno).value();
                            for (FieldAttr fieldAttr : fieldAttrs) {
                                FieldModel fieldModel = new FieldModel();
                                fieldModel.setFieldName(fieldAttr.fieldName());
                                fieldModel.setDefaultValue(fieldAttr.defaultValue());
                                fieldModel.setIsRequired(fieldAttr.isRequired());
                                fieldModel.setFieldContent(fieldAttr.fieldContent());
                                fieldList.add(fieldModel);
                            }
                            detailMap.put("fieldList", fieldList);
                        } else if (anno.annotationType().equals(ApiAttr.class)) {
                            ApiAttr apiAttr = ((ApiAttr) anno);
                            apiName = apiAttr.apiName();
                            detailMap.put("apiName", apiName);
                        } else if (anno.annotationType().equals(RequestMapping.class)) {
                            RequestMapping requestMapping = ((RequestMapping) anno);
                            String url = requestMapping.value()[0];
                            detailMap.put("requestUrl", applicationEntity.getOnDomain() + controlUrl + url);
                            detailMap.put("method", requestMapping.method().length == 0 ? requestMapping.method() : "post");
                        }
                    }
                    if (apiName != "") {
                        generateFile.outFile(applicationEntity.getApiFloder(), detailMap, htmlName, "apiDetail", "html");
                        Map<String, Object> mainObj = new HashMap<>();
                        mainObj.put("fileName", htmlName + "apiDetail.html");
                        mainObj.put("fileShowName", apiName);
                        mainList.add(mainObj);
                    }
                }
            }
            Map<String, Object> param = new HashMap<>();
            param.put("mainList", mainList);
            generateFile.outFile(applicationEntity.getApiFloder(), param, "", "apiIndex", "html");
        } catch (Exception e) {
            return e.getMessage();
        }
        return "SUCCESS";
    }

}


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Fields {
    FieldAttr[] value();
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface FieldAttr {
    String fieldName();

    String fieldContent();

    String defaultValue();

    String isRequired() default "是";
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface ApiAttr {
    String apiName();

    String requestUrl() default "";
}