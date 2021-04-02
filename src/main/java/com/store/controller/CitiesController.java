package com.store.controller;

import org.apache.commons.lang.StringUtils;
import com.store.model.CitiesModel;
import com.store.service.CitiesService;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/m/cities")
@Controller
public class CitiesController extends BaseController {

    @Autowired
    private CitiesService citiesService;

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public String getData(@ModelAttribute CitiesModel model) {
        return citiesService.getData(model).toString();
    }

    @RequestMapping(value = "/getDataById", method = RequestMethod.GET)
    @ResponseBody
    public String getDataById(@ModelAttribute CitiesModel model) {
        return ResultData.toSuccessDataObj(citiesService.getModelById(model));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute CitiesModel model) {
        citiesService.update(model);
        return ResultData.toSuccessDataObj(model);
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @ResponseBody
    public String disable(@ModelAttribute CitiesModel model) {
        citiesService.disableOrEnable(model);
        return model.toString();
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(@ModelAttribute CitiesModel model, HttpServletResponse response) {
        String fileName = DateUtil.getTimeStr() + ConstantVariable.EXCEL_PREFIX;
        try {
        XSSFWorkbook wb = citiesService.export(model);
        this.setResponseHeader(response, fileName);
        OutputStream os = response.getOutputStream();
        wb.write(os);
        os.flush();
        os.close();
        } catch (Exception e) {
        e.printStackTrace();
        }
    }
}