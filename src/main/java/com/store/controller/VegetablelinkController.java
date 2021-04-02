package com.store.controller;

import org.apache.commons.lang.StringUtils;
import com.store.model.VegetablelinkModel;
import com.store.service.VegetablelinkService;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RequestMapping("/m/vegetablelink")
@Controller
public class VegetablelinkController extends BaseController {

    @Autowired
    private VegetablelinkService vegetablelinkService;

//    @InitBinder
//    public void initBinder(WebDataBinder binder, WebRequest request) {
//        //转换日期
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
//    }

    @RequestMapping(value = "/getAddMenuList", method = RequestMethod.POST)
    @ResponseBody
    public String getAddMenuList(@ModelAttribute VegetablelinkModel model) {
        return vegetablelinkService.getAddMenuList(model).toString();
    }

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public String getData(@ModelAttribute VegetablelinkModel model) {
        return vegetablelinkService.getData(model).toString();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute VegetablelinkModel model) {
        model.setCreatetime(DateUtil.getDate());
        model = vegetablelinkService.insert(model);
        if (StringUtils.isEmpty(model.getVlid())) {
            return ResultData.toErrorString();
        } else {
            return ResultData.toSuccessDataObj(model);
        }
    }

    @RequestMapping(value = "/getDataById", method = RequestMethod.GET)
    @ResponseBody
    public String getDataById(@ModelAttribute VegetablelinkModel model) {
        return ResultData.toSuccessDataObj(vegetablelinkService.getModelById(model));
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@ModelAttribute VegetablelinkModel model) {
        vegetablelinkService.delete(model);
        return model.toString();
    }

    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public String batchDelete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        return vegetablelinkService.batchDelete(idList);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute VegetablelinkModel model) {
        vegetablelinkService.update(model);
        return ResultData.toSuccessDataObj(model);
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @ResponseBody
    public String disable(@ModelAttribute VegetablelinkModel model) {
        vegetablelinkService.disableOrEnable(model);
        return model.toString();
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(@ModelAttribute VegetablelinkModel model, HttpServletResponse response) {
        String fileName = DateUtil.getTimeStr() + ConstantVariable.EXCEL_PREFIX;
        try {
            XSSFWorkbook wb = vegetablelinkService.export(model);
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