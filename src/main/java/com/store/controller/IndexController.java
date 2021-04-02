package com.store.controller;

import com.baidu.ueditor.ActionEnter;
import com.store.model.AdminUserModel;
import com.store.model.ResultData;
import com.store.model.SmsinfoModel;
import com.store.model.SysadminModel;
import com.store.model.config.ApplicationEntity;
import com.store.model.config.DataModel;
import com.store.service.SmsinfoService;
import com.store.service.SysadminService;
import com.store.utils.CodeUtil;
import com.store.utils.ConstantVariable;
import com.store.utils.GenerateFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javafx.application.Application;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.Index;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

@Api(value = "后台管理首页", tags = "后台管理首页")
@RestController
public class IndexController {

    private static final Logger LOGGER = Logger.getLogger(IndexController.class);
    @Autowired
    private CodeUtil codeUtil;

    @Autowired
    public GenerateFile genCode;

    @Autowired
    private SysadminService sysadminService;

    @Autowired
    private SmsinfoService smsinfoService;

    @Autowired
    private ApplicationEntity entity;

    @ApiOperation("跳转首页")
    @GetMapping("/index")
    public String index(Model model) {
        return "/";
    }

    @ApiOperation("未登录时跳转")
    @GetMapping("/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void unauthenticatedException(HttpServletRequest request) {
        LOGGER.error("shiro-401-error");
    }

    @ApiOperation("无权限时跳转")
    @GetMapping("/403")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void unauthorizedException(HttpServletRequest request) {
        LOGGER.error("shiro-403-error");
    }


    @GetMapping("/validate")
    @ResponseBody
    public String validate() {
        return ResultData.toSuccessString();
    }

    @ApiOperation("管理员登录")
    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "loginno", defaultValue = "admin", value = "登录名", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "password", defaultValue = "admin", value = "密码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "verifyCode", defaultValue = "1234", value = "验证码", required = true)})
    public String login(HttpServletRequest request, HttpServletResponse response, SysadminModel model, String seq) {
        if (StringUtils.isEmpty(model.getPassword()) && StringUtils.isEmpty(model.getUsername()) && StringUtils.isEmpty(model.getVerifyCode())) {
            return ResultData.toErrorString("请输入管理员名称，密码和验证码！");
        }
        HttpSession session = request.getSession();
        SmsinfoModel sms = new SmsinfoModel();
        sms.setVerifycode(model.getVerifyCode());
        sms.setPhoneno(seq);
        sms = smsinfoService.getModelById(sms);
//        if (null == sms) {
//            return ResultData.toErrorString("验证码输入错误！");
//        } else {
        String returnStr = sysadminService.login(model);
        if (returnStr.indexOf("SUCCESS") == -1) {
            smsinfoService.delete(sms);
        }
        model.setPassword("");
        request.getSession().setAttribute(ConstantVariable.ADMIN_USER, sysadminService.getOneModel(model));
        return returnStr;
//        }
    }

    @RequestMapping(value = "/admin/user", method = RequestMethod.GET)
    @ResponseBody
    public String getUser(HttpServletRequest request) {
        SysadminModel model = (SysadminModel) request.getSession().getAttribute(ConstantVariable.ADMIN_USER);
        return ResultData.toSuccessDataObj(model);
    }

    @ApiIgnore
    @RequestMapping("/getUeditorConfig")
    @ResponseBody
    public String getUeditorConfig(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "text/html");
        String rootPath = entity.getUeConfig();
        LOGGER.info("rootPath:" + rootPath);
        return new ActionEnter(request, rootPath).exec();
    }

    @ApiIgnore
    @RequestMapping("/generateCode")
    @ResponseBody
    public String generateCode() {
        String tableName = "regularHistory";
        try {
            genCode.generate(tableName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "SUCCESS";
    }

    @ApiIgnore
    @RequestMapping("/verify")
    public void generateVerifyCode(HttpServletRequest request, HttpServletResponse response, String seq) {
        OutputStream os = null;
        HttpSession session = request.getSession();
        try {
            os = response.getOutputStream();
            String verifyCode = codeUtil.outputVerifyImage(120, 40, os, 4);
            SmsinfoModel model = new SmsinfoModel();
            model.setPhoneno(seq);
            model.setVerifycode(verifyCode);
            smsinfoService.insert(model);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @ApiIgnore
    @RequestMapping(value = "/auth_user", method = RequestMethod.POST)
    @ResponseBody
    public String loginAuth(HttpServletRequest request, @ModelAttribute AdminUserModel adminModel) {
        HttpSession session = request.getSession();
        String verifyCode = String.valueOf(session.getAttribute(ConstantVariable.VERIFY_CODE));
        if (verifyCode.equalsIgnoreCase(adminModel.getVerifyCode())) {
            request.getSession().setAttribute(ConstantVariable.ADMIN_USER, "1");
            return DataModel.returnSuccess();
        } else {
            return DataModel.returnError("验证码错误");
        }
    }
}