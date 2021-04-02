package com.store.controller;

import org.apache.commons.lang.StringUtils;
import com.store.model.SmsinfoModel;
import com.store.service.SmsinfoService;
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

@RequestMapping("/m/smsinfo")
@Controller
public class SmsinfoController extends BaseController {

    @Autowired
    private SmsinfoService smsinfoService;

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public String getData(@ModelAttribute SmsinfoModel model) {
        return smsinfoService.getData(model).toString();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute SmsinfoModel model) {
        model = smsinfoService.insert(model);
        if(StringUtils.isEmpty(model.getSmsid())){
            return ResultData.toErrorString();
        }else{
            return ResultData.toSuccessDataObj(model);
        }
    }

    @RequestMapping(value = "/getDataById", method = RequestMethod.GET)
    @ResponseBody
    public String getDataById(@ModelAttribute SmsinfoModel model) {
        return ResultData.toSuccessDataObj(smsinfoService.getModelById(model));
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@ModelAttribute SmsinfoModel model) {
        smsinfoService.delete(model);
        return model.toString();
    }

    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public String batchDelete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        return smsinfoService.batchDelete(idList);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute SmsinfoModel model) {
        smsinfoService.update(model);
        return ResultData.toSuccessDataObj(model);
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @ResponseBody
    public String disable(@ModelAttribute SmsinfoModel model) {
        smsinfoService.disableOrEnable(model);
        return model.toString();
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(@ModelAttribute SmsinfoModel model, HttpServletResponse response) {
        String fileName = DateUtil.getTimeStr() + ConstantVariable.EXCEL_PREFIX;
        try {
        XSSFWorkbook wb = smsinfoService.export(model);
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