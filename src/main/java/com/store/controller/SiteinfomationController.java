package com.store.controller;

import org.apache.commons.lang.StringUtils;
import com.store.model.SiteinfomationModel;
import com.store.service.SiteinfomationService;
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

@RequestMapping("/m/siteinfomation")
@Controller
public class SiteinfomationController extends BaseController {

    @Autowired
    private SiteinfomationService siteinfomationService;

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public String getData(@ModelAttribute SiteinfomationModel model) {
        return siteinfomationService.getData(model).toString();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute SiteinfomationModel model) {
        model = siteinfomationService.insert(model);
        if(StringUtils.isEmpty(model.getId())){
            return ResultData.toErrorString();
        }else{
            return ResultData.toSuccessDataObj(model);
        }
    }

    @RequestMapping(value = "/getDataById", method = RequestMethod.GET)
    @ResponseBody
    public String getDataById(@ModelAttribute SiteinfomationModel model) {
        return ResultData.toSuccessDataObj(siteinfomationService.getModelById(model));
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@ModelAttribute SiteinfomationModel model) {
        siteinfomationService.delete(model);
        return model.toString();
    }

    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public String batchDelete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        return siteinfomationService.batchDelete(idList);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute SiteinfomationModel model) {
        siteinfomationService.update(model);
        return ResultData.toSuccessDataObj(model);
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @ResponseBody
    public String disable(@ModelAttribute SiteinfomationModel model) {
        siteinfomationService.disableOrEnable(model);
        return model.toString();
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(@ModelAttribute SiteinfomationModel model, HttpServletResponse response) {
        String fileName = DateUtil.getTimeStr() + ConstantVariable.EXCEL_PREFIX;
        try {
        XSSFWorkbook wb = siteinfomationService.export(model);
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