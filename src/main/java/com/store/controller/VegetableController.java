package com.store.controller;

import com.store.service.PosUserService;
import org.apache.commons.lang.StringUtils;
import com.store.model.VegetableModel;
import com.store.service.VegetableService;
import com.store.model.ResultData;
import com.store.utils.ConstantVariable;
import com.store.utils.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RequestMapping("/m/vegetable")
@Controller
public class VegetableController extends BaseController {

    @Autowired
    private VegetableService vegetableService;

    @Autowired
    private PosUserService posUserService;

//    @org.springframework.web.bind.annotation.InitBinder
//    public void InitBinder(/*HttpServletRequest request, */ServletRequestDataBinder binder) {
//        // 不要删除下行注释!!! 将来"yyyy-MM-dd"将配置到properties文件中
//        // SimpleDateFormat dateFormat = new
//        // SimpleDateFormat(getText("date.format", request.getLocale()));
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//    }

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public String getData(@ModelAttribute VegetableModel model) {
        return vegetableService.getData(model).toString();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute VegetableModel model) {
        model.setValid("1");
        model.setIsIndex("1");
        model.setCreatetime(DateUtil.getDate());
//        Object obj = posUserService.getStockRealtime(model.getPosno());
//        if (null == obj) {
//            return ResultData.toErrorString("在POS系统没有找到对应的商品");
//        }
        model = vegetableService.insert(model);
        if (StringUtils.isEmpty(model.getVid())) {
            return ResultData.toErrorString();
        } else {
            return ResultData.toSuccessDataObj(model);
        }
    }

    @RequestMapping(value = "/getDataById", method = RequestMethod.GET)
    @ResponseBody
    public String getDataById(@ModelAttribute VegetableModel model) {
        return ResultData.toSuccessDataObj(vegetableService.getModelById(model));
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@ModelAttribute VegetableModel model) {
        vegetableService.delete(model);
        return model.toString();
    }

    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public String batchDelete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        return vegetableService.batchDelete(idList);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute VegetableModel model) {
        Object obj = posUserService.getStockRealtime(model.getPosno());
        if (null == obj) {
            vegetableService.update(model);
            return ResultData.toSuccessDataObj("在POS系统没有找到对应的商品");
        }
        vegetableService.update(model);
        return ResultData.toSuccessDataObj(model);
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @ResponseBody
    public String disable(@ModelAttribute VegetableModel model) {
        vegetableService.disableOrEnable(model);
        return model.toString();
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(@ModelAttribute VegetableModel model, HttpServletResponse response) {
        String fileName = DateUtil.getTimeStr() + ConstantVariable.EXCEL_PREFIX;
        try {
            XSSFWorkbook wb = vegetableService.export(model);
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