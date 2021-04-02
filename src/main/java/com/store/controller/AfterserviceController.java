package com.store.controller;

import com.store.api.model.order.OrderInfoVO;
import com.store.model.OrderinfoModel;
import org.apache.commons.lang.StringUtils;
import com.store.model.AfterserviceModel;
import com.store.service.AfterserviceService;
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

@RequestMapping("/m/afterservice")
@Controller
public class AfterserviceController extends BaseController {

    @Autowired
    private AfterserviceService afterserviceService;

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public String getData(@ModelAttribute AfterserviceModel model) {
        return afterserviceService.getData(model).toString();
    }

    @RequestMapping(value = "/getServiceDetail", method = RequestMethod.POST)
    @ResponseBody
    public String getOrderDetail(String orderno) {
        try {
            return ResultData.toSuccessDataObj(afterserviceService.getDetailData(orderno));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute AfterserviceModel model) {
        model = afterserviceService.insert(model);
        if (StringUtils.isEmpty(model.getServiceid())) {
            return ResultData.toErrorString();
        } else {
            return ResultData.toSuccessDataObj(model);
        }
    }

    @RequestMapping(value = "/getDataById", method = RequestMethod.GET)
    @ResponseBody
    public String getDataById(@ModelAttribute AfterserviceModel model) {
        return ResultData.toSuccessDataObj(afterserviceService.getModelById(model));
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@ModelAttribute AfterserviceModel model) {
        afterserviceService.delete(model);
        return model.toString();
    }

    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public String batchDelete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        return afterserviceService.batchDelete(idList);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute AfterserviceModel model) {
        afterserviceService.update(model);
        return ResultData.toSuccessDataObj(model);
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @ResponseBody
    public String disable(@ModelAttribute AfterserviceModel model) {
        afterserviceService.disableOrEnable(model);
        return model.toString();
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(@ModelAttribute AfterserviceModel model, HttpServletResponse response) {
        String fileName = DateUtil.getTimeStr() + ConstantVariable.EXCEL_PREFIX;
        try {
            XSSFWorkbook wb = afterserviceService.export(model);
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