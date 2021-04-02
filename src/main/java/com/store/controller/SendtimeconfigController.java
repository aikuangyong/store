package com.store.controller;

import com.alibaba.fastjson.JSON;
import com.store.model.SendConfig;
import com.store.service.SendConfigService;
import org.apache.commons.lang.StringUtils;
import com.store.model.SendtimeconfigModel;
import com.store.service.SendtimeconfigService;
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

@RequestMapping("/m/sendtimeconfig")
@Controller
public class SendtimeconfigController extends BaseController {

    @Autowired
    private SendtimeconfigService sendtimeconfigService;

    @Autowired
    private SendConfigService sendConfigService;

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public String getData(@ModelAttribute SendtimeconfigModel model) {
        return sendtimeconfigService.getData(model).toString();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String add(String parameter) {
        SendConfig sendConfig = JSON.parseObject(parameter, SendConfig.class);
        sendConfigService.saveSendConfig(sendConfig);
        return ResultData.toSuccessDataObj(null);
    }

    @RequestMapping(value = "/getDataById", method = RequestMethod.GET)
    @ResponseBody
    public String getDataById(@ModelAttribute SendtimeconfigModel model) {
        return ResultData.toSuccessDataObj(sendtimeconfigService.getModelById(model));
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@ModelAttribute SendtimeconfigModel model) {
        sendtimeconfigService.delete(model);
        return model.toString();
    }

    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public String batchDelete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        return sendtimeconfigService.batchDelete(idList);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute SendtimeconfigModel model) {
        sendtimeconfigService.update(model);
        return ResultData.toSuccessDataObj(model);
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @ResponseBody
    public String disable(@ModelAttribute SendtimeconfigModel model) {
        sendtimeconfigService.disableOrEnable(model);
        return model.toString();
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(@ModelAttribute SendtimeconfigModel model, HttpServletResponse response) {
        String fileName = DateUtil.getTimeStr() + ConstantVariable.EXCEL_PREFIX;
        try {
            XSSFWorkbook wb = sendtimeconfigService.export(model);
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