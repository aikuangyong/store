package com.store.controller;

import com.store.model.SysadminModel;
import com.store.service.SysadminService;
import com.store.model.ResultData;
import com.store.utils.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RequestMapping("/m/sysadmin")
@Controller
public class SysadminController extends BaseController {

    @Autowired
    private SysadminService sysadminService;

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public String getData(@ModelAttribute SysadminModel model) {
        return sysadminService.getData(model).toString();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute SysadminModel model) {
        try {
            model.setRegistertime(new Date());
            model.setPassword(EncryptUtils.encrypt(model.getLoginno(), model.getPassword()));
            model.setAdminid(EncryptUtils.md5(System.currentTimeMillis() + CodeUtil.getRandomNumber(6)));
            model = sysadminService.insert(model);
            return ResultData.toSuccessDataObj(model.toString());
        } catch (Exception e) {
            return ResultData.toErrorString("输入信息有误");
        }
    }

    @RequestMapping(value = "/getDataById", method = RequestMethod.GET)
    @ResponseBody
    public String getDataById(@ModelAttribute SysadminModel model) {
        return ResultData.toSuccessDataObj(sysadminService.getOneModel(model));
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@ModelAttribute SysadminModel model) {
        sysadminService.delete(model);
        return model.toString();
    }

    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public String batchDelete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        return sysadminService.batchDelete(idList);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute SysadminModel model) {
        if (StringUtils.isNotBlank(model.getPassword())) {
            model.setPassword(EncryptUtils.encrypt(model.getLoginno(), model.getPassword()));
        } else {
            model.setPassword(null);
        }
        sysadminService.update(model);
        return ResultData.toSuccessString(model);
    }

    @RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
    @ResponseBody
    public String modifyPwd(HttpServletRequest request, String oldPwd, String newPwd) {
        SysadminModel adminModel = (SysadminModel) request.getSession().getAttribute(ConstantVariable.ADMIN_USER);
        String oldPassword = EncryptUtils.encrypt(adminModel.getLoginno(), oldPwd);
        if (!adminModel.getPassword().equals(oldPassword)) {
            return ResultData.toErrorString("旧密码输入不一致");
        }
        SysadminModel updateModel = new SysadminModel();
        updateModel.setPassword(EncryptUtils.encrypt(adminModel.getLoginno(), newPwd));
        updateModel.setAdminid(adminModel.getAdminid());
        sysadminService.update(updateModel);
        return ResultData.toSuccessString();
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @ResponseBody
    public String disable(@ModelAttribute SysadminModel model) {
        sysadminService.disableOrEnable(model);
        return model.toString();
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(@ModelAttribute SysadminModel model, HttpServletResponse response) {
        String fileName = DateUtil.getTimeStr() + ConstantVariable.EXCEL_PREFIX;
        try {
            XSSFWorkbook wb = sysadminService.export(model);
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