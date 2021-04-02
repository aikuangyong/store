package com.store.controller;

import com.store.model.UserRegularOrderInfo;
import org.apache.commons.lang.StringUtils;
import com.store.model.RegularModel;
import com.store.service.RegularService;
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

@RequestMapping("/m/regular")
@Controller
public class RegularController extends BaseController {

    @Autowired
    private RegularService regularService;

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public String getData(@ModelAttribute RegularModel model) {
        return regularService.getData(model).toString();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute RegularModel model) {
        model.setCreatetime(DateUtil.getDate());
        model = regularService.insert(model);
        if(StringUtils.isEmpty(model.getRid())){
            return ResultData.toErrorString();
        }else{
            return ResultData.toSuccessDataObj(model);
        }
    }

    @RequestMapping(value = "/getDataById", method = RequestMethod.GET)
    @ResponseBody
    public String getDataById(@ModelAttribute RegularModel model) {
        return ResultData.toSuccessDataObj(regularService.getModelById(model));
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@ModelAttribute RegularModel model) {
        regularService.delete(model);
        return model.toString();
    }

    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public String batchDelete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        return regularService.batchDelete(idList);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute RegularModel model) {
        regularService.update(model);
        return ResultData.toSuccessDataObj(model);
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @ResponseBody
    public String disable(@ModelAttribute RegularModel model) {
        regularService.disableOrEnable(model);
        return model.toString();
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(@ModelAttribute RegularModel model, HttpServletResponse response) {
        String fileName = DateUtil.getTimeStr() + ConstantVariable.EXCEL_PREFIX;
        try {
        XSSFWorkbook wb = regularService.export(model);
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