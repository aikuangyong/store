package com.store.controller;

import org.apache.commons.lang.StringUtils;
import com.store.model.SysroleModel;
import com.store.service.SysroleService;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.DateUtil;
import com.store.utils.exception.RequiredException;
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

@RequestMapping("/m/sysrole")
@Controller
public class SysroleController extends BaseController {

    @Autowired
    private SysroleService sysroleService;

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public String getData(@ModelAttribute SysroleModel model) {
        return sysroleService.getData(model).toString();
    }

    @RequestMapping(value = "/getMenuData", method = RequestMethod.POST)
    @ResponseBody
    public String getMenuData(@ModelAttribute SysroleModel model) {
        return sysroleService.getData(model).toString();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute SysroleModel model) {
        model.setRegistertime(DateUtil.getDate());
        try {
            model = sysroleService.save(model);
        } catch (RequiredException e) {
            return ResultData.toErrorString(e.getMessage());
        }
        if (StringUtils.isEmpty(model.getRoleid())) {
            return ResultData.toErrorString();
        } else {
            return ResultData.toSuccessDataObj(model);
        }
    }

    @RequestMapping(value = "/getDataById", method = RequestMethod.GET)
    @ResponseBody
    public String getDataById(@ModelAttribute SysroleModel model) {
        return ResultData.toSuccessDataObj(sysroleService.getModelById(model));
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@ModelAttribute SysroleModel model) {
        sysroleService.delete(model);
        return model.toString();
    }

    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public String batchDelete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        return sysroleService.batchDelete(idList);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute SysroleModel model) {
        sysroleService.update(model);
        return ResultData.toSuccessDataObj(model);
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @ResponseBody
    public String disable(@ModelAttribute SysroleModel model) {
        sysroleService.disableOrEnable(model);
        return model.toString();
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(@ModelAttribute SysroleModel model, HttpServletResponse response) {
        String fileName = DateUtil.getTimeStr() + ConstantVariable.EXCEL_PREFIX;
        try {
            XSSFWorkbook wb = sysroleService.export(model);
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