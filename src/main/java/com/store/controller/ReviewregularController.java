package com.store.controller;

import com.store.service.RegularorderdetailService;
import org.apache.commons.lang.StringUtils;
import com.store.model.ReviewregularModel;
import com.store.service.ReviewregularService;
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

@RequestMapping("/m/reviewregular")
@Controller
public class ReviewregularController extends BaseController {

    @Autowired
    private ReviewregularService reviewregularService;

    @Autowired
    private RegularorderdetailService regularorderdetailService;

    @RequestMapping(value = "/clearRegularOrder", method = RequestMethod.POST)
    @ResponseBody
    public String clearRegularOrder(String rrid, String orderno, String status) {
        try {
            regularorderdetailService.clearRegularOrder(rrid, orderno, status);
            return ResultData.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public String getData(@ModelAttribute ReviewregularModel model) {
        return reviewregularService.getData(model).toString();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute ReviewregularModel model) {
        model.setCreatetime(DateUtil.getDate());
        model = reviewregularService.insert(model);
        if (StringUtils.isEmpty(model.getRrid())) {
            return ResultData.toErrorString();
        } else {
            return ResultData.toSuccessDataObj(model);
        }
    }

    @RequestMapping(value = "/getDataById", method = RequestMethod.GET)
    @ResponseBody
    public String getDataById(@ModelAttribute ReviewregularModel model) {
        return ResultData.toSuccessDataObj(reviewregularService.getModelById(model));
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@ModelAttribute ReviewregularModel model) {
        reviewregularService.delete(model);
        return model.toString();
    }

    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public String batchDelete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        return reviewregularService.batchDelete(idList);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute ReviewregularModel model) {
        reviewregularService.update(model);
        return ResultData.toSuccessDataObj(model);
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @ResponseBody
    public String disable(@ModelAttribute ReviewregularModel model) {
        reviewregularService.disableOrEnable(model);
        return model.toString();
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(@ModelAttribute ReviewregularModel model, HttpServletResponse response) {
        String fileName = DateUtil.getTimeStr() + ConstantVariable.EXCEL_PREFIX;
        try {
            XSSFWorkbook wb = reviewregularService.export(model);
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