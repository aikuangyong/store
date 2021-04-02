package com.store.controller;

import com.store.model.ReviewregularModel;
import com.store.model.SysadminModel;
import org.apache.commons.lang.StringUtils;
import com.store.model.RegularorderdetailModel;
import com.store.service.RegularorderdetailService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/m/regularorderdetail")
@Controller
public class RegularorderdetailController extends BaseController {

    @Autowired
    private RegularorderdetailService regularorderdetailService;

    @RequestMapping(value = "/getDetailData", method = RequestMethod.GET)
    @ResponseBody
    public String getDetailData(String orderid) {
        try {
            RegularorderdetailModel detailModel = new RegularorderdetailModel();
            detailModel.setOrderid(orderid);
            detailModel = regularorderdetailService.getDetailData(detailModel);
            return ResultData.toSuccessDataObj(detailModel);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/addRevierRegular", method = RequestMethod.POST)
    @ResponseBody
    public String addRevierRegular(@ModelAttribute RegularorderdetailModel model, HttpServletRequest request) {
        try {

            SysadminModel sysUser = (SysadminModel) request.getSession().getAttribute(ConstantVariable.ADMIN_USER);
            String username = "";
            String store = "";
            if (sysUser == null) {
                username = "测试用户";
                store = "-1";
            } else {
                username = StringUtils.defaultIfBlank(sysUser.getUsername(), "测试用户");
                store = StringUtils.defaultIfBlank(sysUser.getStore(), "-1");
            }
            ReviewregularModel reviewModel = regularorderdetailService.addRevierRegular(model, username, store);
            return ResultData.toSuccessDataObj(reviewModel);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public String getData(@ModelAttribute RegularorderdetailModel model) {
        return regularorderdetailService.getData(model).toString();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute RegularorderdetailModel model) {
        model = regularorderdetailService.insert(model);
        if (StringUtils.isEmpty(model.getDetailid())) {
            return ResultData.toErrorString();
        } else {
            return ResultData.toSuccessDataObj(model);
        }
    }

    @RequestMapping(value = "/getDataById", method = RequestMethod.GET)
    @ResponseBody
    public String getDataById(@ModelAttribute RegularorderdetailModel model) {
        return ResultData.toSuccessDataObj(regularorderdetailService.getModelById(model));
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@ModelAttribute RegularorderdetailModel model) {
        regularorderdetailService.delete(model);
        return model.toString();
    }

    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public String batchDelete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        return regularorderdetailService.batchDelete(idList);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute RegularorderdetailModel model) {
        regularorderdetailService.update(model);
        return ResultData.toSuccessDataObj(model);
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @ResponseBody
    public String disable(@ModelAttribute RegularorderdetailModel model) {
        regularorderdetailService.disableOrEnable(model);
        return model.toString();
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(@ModelAttribute RegularorderdetailModel model, HttpServletResponse response) {
        String fileName = DateUtil.getTimeStr() + ConstantVariable.EXCEL_PREFIX;
        try {
            XSSFWorkbook wb = regularorderdetailService.export(model);
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