package com.store.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.store.model.*;
import com.store.model.config.ImageinfoModel;
import com.store.model.pos.PosCustomer;
import com.store.model.pos.PosData;
import com.store.model.pos.PosUser;
import com.store.utils.*;
import org.apache.commons.lang.StringUtils;
import com.store.model.config.ImageinfoModel;
import org.apache.commons.lang.StringUtils;
import com.store.config.shiro.CustomizedToken;
import com.store.config.shiro.LoginType;
import com.store.config.shiro.UserType;
import com.store.dao.CustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.xml.transform.Result;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("customerService")
public class CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ExcelUtil<CustomerModel> excelUtil;

    @Autowired
    private SmsinfoService smsinfoService;

    @Autowired
    private UsertokenService usertokenService;

    @Autowired
    private ImageinfoService imageinfoService;

    @Autowired
    private BindinfoService bindinfoService;

    @Autowired
    private PosUserService posService;

    @Transactional
    public String changeBindUser(String userid, String verifycode, String phoneno, String token) {
        CustomerModel queryModel = new CustomerModel();
        queryModel.setPhoneno(phoneno);
        List<CustomerModel> userList = customerMapper.getList(queryModel);
        if (userList.size() != 0) {
            return ResultData.toErrorString("当前手机号码已存在,不能重复绑定");
        }
        CustomerModel existsUser = getModelById(userid);
        if (null == userList && userList.size() == 0) {
            return ResultData.toErrorString("当前手机号码已存在,不能重复绑定");
        }
        String oldphoneno = existsUser.getPhoneno();
        UsertokenModel tokenModel = new UsertokenModel();
        tokenModel.setToken(token);
        tokenModel = usertokenService.getModelById(tokenModel);
        if (null == tokenModel) {
            return ResultData.toErrorString("旧手机验证有误，请重新验证");
        }
        if (!tokenModel.getUserid().equals(oldphoneno)) {
            return ResultData.toErrorString("只能解绑自己的当前手机号码");
        }
//        String errorMsg = checkSms(phoneno, verifycode, ConstantVariable.SMS_BIND);
//        if (StringUtils.isNotBlank(errorMsg)) {
//            return ResultData.toErrorString(errorMsg);
//        }
        if (phoneno.equals(oldphoneno)) {
            return ResultData.toErrorString("相同号码不能重复绑定");
        }
        CustomerModel editModel = new CustomerModel();
        editModel.setUid(userid);
        editModel.setPhoneno(phoneno);
        editModel.setOldphone(oldphoneno);
        customerMapper.changeBindUser(editModel);
        customerMapper.changeBindThird(editModel);
        return ResultData.toSuccessString();
    }

    public String editPassword(CustomerModel userModel, String newpwd) throws Exception {
        CustomerModel queryModel = getModelById(userModel.getUid());
        if (null == queryModel) {
            return ResultData.toErrorString();
        }
        if (queryModel.getPassword().equals(EncryptUtils.encrypt(userModel.getPassword(), userModel.getPassword()))) {
            queryModel.setPassword(EncryptUtils.encrypt(userModel.getPassword(), newpwd));
            update(queryModel);
            return ResultData.toSuccessString("密码修改成功");
        }
        return ResultData.toErrorString("密码输入错误");
    }

    public List<PosCustomer> getCardList(String userid) {
        CustomerModel customerModel = getModelById(userid);
        PosUser posUser = new PosUser();
        posUser.setMobile(customerModel.getPhoneno());
        PosData<PosCustomer> posData = posService.getMemberInfo(posUser);
        return posData.getResultList(PosCustomer.class);
    }

    public List<PosCustomer> getCardListByPhone(String phone) {
        PosUser posUser = new PosUser();
        posUser.setMobile(phone);
        PosData<PosCustomer> posData = posService.getMemberInfo(posUser);
        return posData.getResultList(PosCustomer.class);
    }

    public CustomerModel editUser(CustomerModel userModel, MultipartFile iconFile) throws Exception {
        PosUser posUser = new PosUser();
        if (null != iconFile) {
            ImageinfoModel imageinfoModel = imageinfoService.uploadImage(iconFile, "usericon");
            userModel.setIcon(imageinfoModel.getImggrp());
            posUser.setLogo(imageinfoModel.getSrc());
        }
        update(userModel);
        return getModelById(userModel.getUid());
    }

    public CustomerModel editUser(CustomerModel userModel, String iconHref) throws Exception {
        PosUser posUser = new PosUser();
        posUser.setLogo(iconHref);
        update(userModel);
        return getModelById(userModel.getUid());
    }

    public String checkSms(String phoneNo, String verifyCode, String smsType) {
        SmsinfoModel smsinfoModel = new SmsinfoModel();
        smsinfoModel.setSmstype(smsType);
        smsinfoModel.setPhoneno(phoneNo);
        smsinfoModel = smsinfoService.getLastSmsInfo(smsinfoModel);
        if (null == smsinfoModel) {
            return ResultData.toErrorString("请发送短信");
        }

        if (System.currentTimeMillis() / 1000 - smsinfoModel.getSendtime().getTime() / 1000 > 1800) {
            return ResultData.toErrorString("短信已过期，请重新发送");
        }

        if (!smsinfoModel.getVerifycode().equals(verifyCode)) {
            return ResultData.toErrorString("验证码填写错误");
        }
        return "";
    }

    @Transactional
    public String bindUser(String phoneno, String verifycode, String thirdid, String bindtype) {
        String errorMsg = checkSms(phoneno, verifycode, ConstantVariable.SMS_BIND);
        if (!StringUtils.isEmpty(errorMsg)) {
            return errorMsg;
        }
        BindinfoModel bindinfoModel = new BindinfoModel();
        bindinfoModel.setBindtype(bindtype);
        bindinfoModel.setThirdid(thirdid);
        List<BindinfoModel> bindList = bindinfoService.getList(bindinfoModel);
        //如果该用户的openid不能存在,则新建一个
        if (bindList.size() == 0) {
            bindinfoModel.setPhoneno(phoneno);
            bindinfoService.insert(bindinfoModel);
            bindList = bindinfoService.getList(bindinfoModel);
        }
        bindinfoModel = bindList.get(0);

        CustomerModel userModel = new CustomerModel();
        userModel.setPhoneno(bindinfoModel.getPhoneno());
        List<CustomerModel> userList = customerMapper.getList(userModel);
        //如果该号码没有对应的数据,则新建一个
        if (null == userList || userList.size() == 0) {
            userModel.setPhoneno(phoneno);
            userList = customerMapper.getList(userModel);
            if (null == userList || userList.size() == 0) {
                adduser(phoneno, "");
                userList = customerMapper.getList(userModel);
                bindinfoModel.setPhoneno(phoneno);
                bindinfoService.update(bindinfoModel);
                return ResultData.toSuccessString(userList.get(0));
            }
//            adduser(phoneno, "");
//            userList = customerMapper.getList(userModel);
//            bindinfoModel = new BindinfoModel();
//            bindinfoModel.setThirdid(thirdid);
//            bindinfoModel = bindinfoService.getModelById(bindinfoModel);
//            if (null == bindinfoModel) {
//                bindinfoModel = new BindinfoModel();
//                bindinfoModel.setPhoneno(phoneno);
//                bindinfoModel.setBindtype(bindtype);
//                bindinfoModel.setThirdid(thirdid);
//                bindinfoService.insert(bindinfoModel);
//            }
        }
        if (!phoneno.equals(bindinfoModel.getPhoneno())) {
            bindinfoModel.setPhoneno(phoneno);
            bindinfoService.update(bindinfoModel);
            CustomerModel customerModel = userList.get(0);
            customerModel.setPhoneno(phoneno);
            updatePhone(customerModel);
        }
        //如果该号码有对应的数据,则直接返回数据
        return ResultData.toSuccessString(userList.get(0));
    }

    public String forgetPwd(CustomerModel userModel) {
        SmsinfoModel sms = new SmsinfoModel();
        sms.setPhoneno(userModel.getPhoneno());
        sms.setSmstype(ConstantVariable.SMS_FORGOT_PWD);
        sms = smsinfoService.getLastSmsInfo(sms);
        if (null == sms) {
            return ResultData.toErrorString("请发送短信");
        }

        if (System.currentTimeMillis() / 1000 - sms.getSendtime().getTime() / 1000 > 1800) {
            return ResultData.toErrorString("短信已过期，请重新发送");
        }

        if (!sms.getVerifycode().equals(userModel.getVerifycode())) {
            return ResultData.toErrorString("验证码填写错误");
        }
        customerMapper.update(userModel);
        CustomerModel customerModel = new CustomerModel();
        customerModel.setPhoneno(userModel.getPhoneno());
        List<CustomerModel> userList = customerMapper.getList(customerModel);
        if (null == userList || userList.size() == 0) {
            return ResultData.toErrorString("查无可用的用户");
        } else {
            return ResultData.toSuccessDataObj(userList.get(0), "密码修改成功");
        }
    }

    public String loginBySms(String phoneNo, String verifycode) {
        SmsinfoModel smsModel = new SmsinfoModel();
        smsModel.setPhoneno(phoneNo);
        smsModel.setVerifycode(verifycode);
        smsModel.setSmstype(ConstantVariable.SMS_LOGIN);
        smsModel = smsinfoService.getLastSmsInfo(smsModel);
        if (null == smsModel) {
            return ResultData.toErrorString("验证码输入有误");
        }
        if (System.currentTimeMillis() / 1000 - smsModel.getSendtime().getTime() / 1000 > 1800) {
            return ResultData.toErrorString("短信已过期，请重新发送");
        }
        CustomerModel loginUser = new CustomerModel();
        loginUser.setPhoneno(phoneNo);
        loginUser.setPassword("");
        List<CustomerModel> loginUserList = this.getList(loginUser);
        if (null == loginUserList || loginUserList.size() == 0) {
            adduser(phoneNo, "");
            loginUserList = this.getList(loginUser);
        }
        return ResultData.toSuccessDataObj(loginUserList.get(0), "登录成功");
    }

    @Transactional
    public CustomerModel loginByThird(String thirdid, String bindtype) {
        BindinfoModel bindinfoModel = new BindinfoModel();
        bindinfoModel.setThirdid(thirdid);
        bindinfoModel.setBindtype(bindtype);
        bindinfoModel = bindinfoService.getModelById(bindinfoModel);
        if (null == bindinfoModel) {
            String tempPhoneNo = CodeUtil.getRandomNumber(16);
            bindinfoModel = new BindinfoModel();
            bindinfoModel.setThirdid(thirdid);
            bindinfoModel.setBindtype(bindtype);
            bindinfoModel.setPhoneno(tempPhoneNo);
            bindinfoService.insert(bindinfoModel);
        }
        String phoneNo = bindinfoModel.getPhoneno();
        if (StringUtils.isBlank(phoneNo) || phoneNo.length() == 16) {
            CustomerModel nullUser = new CustomerModel();
            nullUser.setPhoneno("");
            return nullUser;
        }
        CustomerModel customerModel = new CustomerModel();
        customerModel.setPhoneno(phoneNo);
        List<CustomerModel> userList = customerMapper.getList(customerModel);
        if (null == userList && userList.size() == 0) {
            throw new NullPointerException("登录失败，请尝试使用手机号登录");
        }
        return userList.get(0);
//        phoneNo = bindinfoModel.getPhoneno();
//        CustomerModel customerModel = new CustomerModel();
//        customerModel.setPhoneno(phoneNo);
//        List<CustomerModel> userList = customerMapper.getList(customerModel);
//        if (null == userList && userList.size() == 0) {
//            throw new NullPointerException("登录失败，请尝试使用手机号登录");
//        }
//        CustomerModel userModel = userList.get(0);
//        if (userModel.getPhoneno().length() == 16) {
//            userModel.setPhoneno("");
//        }
//        return userModel;
    }

    public String login(CustomerModel user) {
        String phoneNo = user.getPhoneno();
        String verifycode = user.getVerifycode();

        CustomerModel checkUser = new CustomerModel();
        checkUser.setPhoneno(phoneNo);
        List<CustomerModel> userList = this.getList(checkUser);
        if (null == userList || userList.size() == 0) {
            if (ConstantVariable.LOGIN_SMS.equals(user.getLogintype())) {
                return loginBySms(phoneNo, verifycode);
            } else {
                return ResultData.toErrorString("用户不存在");
            }
        }

        CustomerModel userModel = userList.get(0);
        if (ConstantVariable.LOGIN_PWD.equals(user.getLogintype())) {
            String sourcePwd = userModel.getPassword();
            String inputPwd = EncryptUtils.encrypt(user.getPassword(), user.getPassword());
            if (sourcePwd.equals(inputPwd)) {
                userModel.setUsertoken(usertokenService.getUserToken(user.getUid()).getToken());
                userModel.setPassword(null);
                return ResultData.toSuccessDataObj(userModel, "登录成功");
            } else {
                return ResultData.toErrorString("登录失败，密码错误");
            }
        } else if (ConstantVariable.LOGIN_SMS.equals(user.getLogintype())) {
            return loginBySms(phoneNo, verifycode);
        } else if (ConstantVariable.LOGIN_WX.equals(user.getLogintype())) {
            SmsinfoModel smsModel = new SmsinfoModel();
            smsModel.setPhoneno(phoneNo);
            smsModel.setVerifycode(verifycode);
            smsModel.setSmstype(ConstantVariable.SMS_LOGIN);
            smsModel = smsinfoService.getLastSmsInfo(smsModel);
            if (null == smsModel) {
                return ResultData.toErrorString("验证码输入有误");
            }
            if (System.currentTimeMillis() / 1000 - smsModel.getSendtime().getTime() / 1000 > 1800) {
                return ResultData.toErrorString("短信已过期，请重新发送");
            }
            BindinfoModel bindinfoModel = new BindinfoModel();
            bindinfoModel.setPhoneno(phoneNo);
            bindinfoModel.setThirdid(user.getThridid());
            bindinfoModel = bindinfoService.getModelById(bindinfoModel);
            if (null == bindinfoModel) {
                return ResultData.toErrorString("登录成功");
            }
            CustomerModel loginUser = new CustomerModel();
            loginUser.setPhoneno(phoneNo);
            loginUser.setUsertoken(usertokenService.getUserToken(user.getUid()).getToken());
            loginUser.setPassword("");
            List<CustomerModel> loginUserList = this.getList(loginUser);
            return ResultData.toSuccessDataObj(loginUserList.get(0), "登录成功");
        }
        return ResultData.toErrorString("登录异常");
    }

    public void updateUserLogintType(String loginType, String phoneno) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setLogintype(loginType);
        customerModel.setPhoneno(phoneno);
        update(customerModel);
    }

    public String register(CustomerModel user) {
        String phoneNo = user.getPhoneno();
        String verifycode = user.getVerifycode();

        CustomerModel checkUser = new CustomerModel();
        checkUser.setPhoneno(phoneNo);
        if (this.getList(checkUser).size() > 0) {
            return ResultData.toErrorString("用户已存在");
        }

        SmsinfoModel smsModel = new SmsinfoModel();
        smsModel.setPhoneno(phoneNo);
        smsModel.setVerifycode(verifycode);
        smsModel = smsinfoService.getLastSmsInfo(smsModel);
        if (null == smsModel) {
            return ResultData.toErrorString("请重新发送短信");
        }

        if (System.currentTimeMillis() / 1000 - smsModel.getSendtime().getTime() / 1000 > 1800) {
            return ResultData.toErrorString("短信已过期，请重新发送");
        }

        if (null != smsModel) {
            String pwd = user.getPassword();
            adduser(user.getPhoneno(), pwd);
            return ResultData.toSuccessString("注册成功");
        } else {
            return ResultData.toErrorString("验证码输入有误");
        }
    }

    private void adduser(String phoneno, String pwd) {
        CustomerModel user = new CustomerModel();
        user.setPhoneno(phoneno);
        user.setPassword(EncryptUtils.encrypt(pwd, pwd));
        user.setVerifycode("");
        user.setUid(EncryptUtils.md5(System.currentTimeMillis() + "" + CodeUtil.getRandomNumber(6)));
        user.setUserid("MEM" + CodeUtil.getRandomNumber(8));
        user.setRegistertime(new Date());
        user.setNickname("会员" + CodeUtil.getRandomNumber(6));
        user.setIcon("defaul_usericon");
        customerMapper.insert(user);
    }

    public XSSFWorkbook export(CustomerModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<CustomerModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public CustomerModel getModelById(String uid) {
        CustomerModel model = new CustomerModel();
        model.setUid(uid);
        List<CustomerModel> customerList = this.getList(model);
        if (null == customerList || customerList.size() == 0) {
            throw new NullPointerException("Customer Info Not Found");
        }
        return customerList.get(0);
    }

    public ResultData getData(CustomerModel model) {
        ResultData returnData = new ResultData();
        try {
            returnData.setDataList(this.getList(model));
            int dataCount = this.getCount(model);
            returnData.setPageCount(dataCount, model.getPageSize());
            returnData.setDataCount(dataCount);
            returnData.setState(ConstantVariable.SUCCESS);
            returnData.setPageNumber(model.getPageNumber());
        } catch (Exception e) {
            e.printStackTrace();
            returnData.setState(ConstantVariable.ERROR);
            returnData.setMsg(e.getMessage());
        }
        return returnData;
    }

    public List<CustomerModel> getList(CustomerModel model) {
        List<CustomerModel> dataList = customerMapper.getList(model);
        for (CustomerModel userModel : dataList) {
            if(StringUtils.isNotBlank(userModel.getPhoneno())){
                List<PosCustomer> cardList = getCardListByPhone(userModel.getPhoneno());
                if (CollectionUtils.isEmpty(cardList)) {
                    userModel.setCardCount("0张");
                } else {
                    userModel.setCardCount(cardList.size() + "张");
                }
            }else{
                userModel.setCardCount("0张");
            }
        }
        return dataList;
    }

    public int getCount(CustomerModel model) {
        return customerMapper.getCount(model);
    }

    public CustomerModel insert(CustomerModel model) {
        customerMapper.insert(model);
        return model;
    }

    public CustomerModel update(CustomerModel model) {
        customerMapper.update(model);
        return model;
    }

    public CustomerModel updatePhone(CustomerModel model) {
        customerMapper.updatePhone(model);
        return model;
    }

    public List<CustomerModel> batchUpdate(List<CustomerModel> modelList) {
        for (CustomerModel model : modelList) {
            customerMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(CustomerModel model) {
        customerMapper.disableOrEnable(model);
    }

    public void delete(CustomerModel model) {
        customerMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        CustomerModel model = new CustomerModel();
        model.setIdList(idList);
        customerMapper.delete(model);
        return ResultData.toSuccessString();
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param phone
     * @return
     */
    public CustomerModel findUserByPhone(String phone) {
        CustomerModel query = new CustomerModel();
        query.setPhoneno(phone);
        List<CustomerModel> users = this.getList(query);
        if (CollectionUtils.isNotEmpty(users)) {
            return users.get(0);
        }
        return null;
    }
}