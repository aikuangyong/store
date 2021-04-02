package com.store.api;

import com.alibaba.fastjson.JSON;
import com.store.model.CustomerModel;
import com.store.model.ResultData;
import com.store.model.SmsinfoModel;
import com.store.model.UsertokenModel;
import com.store.service.BindinfoService;
import com.store.service.CustomerService;
import com.store.service.SmsinfoService;
import com.store.service.UsertokenService;
import com.store.utils.CodeUtil;
import com.store.utils.ConstantVariable;
import com.store.utils.EncryptUtils;
import com.store.utils.HttpRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Api(value = "登录注册模块", tags = "登录注册模块")
@RequestMapping("/api/login")
@RestController
public class ApiLogin {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SmsinfoService smsinfoService;

    @Autowired
    private UsertokenService usertokenService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "phoneno", defaultValue = "15829743830", value = "手机号码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "clienttype", defaultValue = "1", value = "传安卓/苹果/微信", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "logintype", defaultValue = "1", value = "1:密码;2:短信;3:微信", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "verifycode", defaultValue = "1", value = "验证码", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "password", defaultValue = "1", value = "密码", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "thridid", defaultValue = "1", value = "第三方登录传入的ID", required = false)})
    @ApiOperation(value = "登录", notes = "密码登录/第三方登录,密码和第三方登录传入的ID/短信验证码必须三传一")
    public String login(HttpServletRequest request, String phoneno, String clienttype, String logintype, @RequestParam(value = "verifycode", required = false) String verifycode, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "thridid", required = false) String thridid) {
        try {
            CustomerModel customerModel = new CustomerModel();
            customerModel.setPhoneno(phoneno);
            customerModel.setLogintype(logintype);
            customerModel.setPassword(password);
            customerModel.setThridid(thridid);
            customerModel.setVerifycode(verifycode);
            if (StringUtils.isBlank(phoneno)) {
                return ResultData.toErrorString("手机号不能为空");
            }
            String requestStr = customerService.login(customerModel);
            ResultData resultData = JSON.parseObject(requestStr, ResultData.class);
            if (resultData.getState().equals(ConstantVariable.SUCCESS)) {
                customerService.updateUserLogintType(clienttype, phoneno);
            }
            return requestStr;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/loginByThird", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "thridid", defaultValue = "1", value = "第三方登录传入的ID", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "bindtype", defaultValue = "wx", value = "第三方的类型:微信:wx;支付宝:zfb;微博:wb", required = false)})
    @ApiOperation(value = "第三方登录", notes = "密码登录/第三方登录,密码和第三方登录传入的ID/短信验证码必须三传一")
    public String loginByThird(HttpServletRequest request, String thridid, String bindtype) {
        try {
            if (StringUtils.isEmpty(thridid)) {
                return ResultData.toErrorString("信息为空");
            }
            String loginType = HttpRequest.getClientType(request);
            CustomerModel user = customerService.loginByThird(thridid, bindtype);
            if (StringUtils.isNotBlank(user.getPhoneno())) {
                customerService.updateUserLogintType(loginType, user.getPhoneno());
            }
            return ResultData.toSuccessString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/checkSms", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "phoneno", defaultValue = "18091795525", value = "新的手机号码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "verifycode", defaultValue = "1", value = "短信验证码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "smstype", defaultValue = "5", value = "短信类型", required = true)})
    @ApiOperation(value = "验证用户刚发送的短信验证码是否有效;返回的token值需要在下一步操作中用到，确定操作的一致性", notes = "")
    public String checkSms(String phoneno, String verifycode, String smstype) {
        String errorMsg = customerService.checkSms(phoneno, verifycode, smstype);
        if (StringUtils.isBlank(errorMsg)) {
            String token = EncryptUtils.md5(verifycode + System.currentTimeMillis() + CodeUtil.getRandomNumber(6));
            UsertokenModel tokenModel = new UsertokenModel();
            tokenModel.setUserid(phoneno);
            tokenModel.setToken(token);
            tokenModel.setLogintime(System.currentTimeMillis());
            tokenModel.setCreatetime(new Date());
            usertokenService.insert(tokenModel);
            return ResultData.toSuccessDataObj(tokenModel);
        }
        return errorMsg;
    }

    @RequestMapping(value = "/changeBindUser", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "phoneno", defaultValue = "18091795525", value = "新的手机号码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "token", defaultValue = "1", value = "在上一步验证短信时的token值", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "verifycode", defaultValue = "1", value = "短信验证码", required = true)})
    @ApiOperation(value = "更改用户绑定的手机号", notes = "")
    public String changeBindUser(String phoneno, String userid, String token, String verifycode) {
        return customerService.changeBindUser(userid, verifycode, phoneno, token);
    }

    @RequestMapping(value = "/bindUser", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "phoneno", defaultValue = "18091795525", value = "手机号码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "thirdid", defaultValue = "1", value = "第三方登录传入的ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "bindtype", defaultValue = "1", value = "第三方的类型:微信:wx;支付宝:zfb;微博:wb", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "verifycode", defaultValue = "1", value = "短信验证码", required = true)})
    @ApiOperation(value = "绑定用户", notes = "密码登录/第三方登录,密码和第三方登录传入的ID/短信验证码必须三传一")
    public String bindUser(HttpServletRequest request, String phoneno, String thirdid, String bindtype, String verifycode) {
        try {
            if (StringUtils.isBlank(phoneno)) {
                return ResultData.toErrorString("手机号不能为空");
            }
            if (StringUtils.isEmpty(thirdid)) {
                return ResultData.toErrorString("信息为空");
            }
            String loginType = HttpRequest.getClientType(request);
            String requestStr = customerService.bindUser(phoneno, verifycode, thirdid, bindtype);
            ResultData resultData = JSON.parseObject(requestStr, ResultData.class);
            if (resultData.getState().equals(ConstantVariable.SUCCESS)) {
                customerService.updateUserLogintType(loginType, phoneno);
            }
            return requestStr;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @ApiOperation(value = "注册", notes = "注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "phoneno", defaultValue = "15829743830", value = "手机号码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "password", defaultValue = "1", value = "密码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "verifycode", defaultValue = "1111", value = "验证码", required = true)})
    public String register(String phoneno, String password, String verifycode) {
        try {
            CustomerModel model = new CustomerModel();
            model.setPhoneno(phoneno);
            model.setPassword(password);
            model.setVerifycode(verifycode);
            return customerService.register(model);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "phoneno", defaultValue = "15829743830", value = "手机号码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "action", defaultValue = "1", value = "短信类型;1:注册2忘记密码3绑定账号4登录5解绑", required = true)})
    @ApiOperation(value = "发送短信", notes = "发送短信")
    public String sendSms(@RequestParam(value = "phoneno") String
                                  phoneNo, @RequestParam(value = "action") String smsType) {
        try {
            SmsinfoModel model = new SmsinfoModel();
            model.setPhoneno(phoneNo);
            //短信类型1注册2忘记密码3绑定账号
            model.setSmstype(smsType);
            return smsinfoService.sendSms(model);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @ApiAttr(apiName = "忘记密码")
    @RequestMapping(value = "/forgetPwd", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "phoneno", defaultValue = "15829743830", value = "手机号码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "password", defaultValue = "2", value = "新密码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "verifycode", defaultValue = "1111", value = "验证码", required = true)})
    @ApiOperation(value = "忘记密码", notes = "忘记密码")
    public String forgetPwd(@RequestParam(value = "phoneno") String
                                    phoneNo, @RequestParam(value = "verifycode") String verifycode, @RequestParam(value = "password") String
                                    password) {
        CustomerModel userModel = new CustomerModel();
        userModel.setVerifycode(verifycode);
        userModel.setPhoneno(phoneNo);
        userModel.setPassword(EncryptUtils.encrypt(password, password));
        return customerService.forgetPwd(userModel);
    }

}