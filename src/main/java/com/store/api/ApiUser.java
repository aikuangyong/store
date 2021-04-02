package com.store.api;

import com.store.model.CustomerModel;
import com.store.model.ResultData;
import com.store.model.UseraddressModel;
import com.store.model.config.ImageinfoModel;
import com.store.service.CustomerService;
import com.store.service.UseraddressService;
import com.store.utils.ConstantVariable;
import com.store.utils.StringUtils;
import io.swagger.annotations.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Api(value = "用户信息相关", tags = "用户信息相关;地址/修改昵称/修改密码")
public class ApiUser {


    @Autowired
    private UseraddressService useraddressService;

    @Autowired
    private CustomerService customerService;

    private static final Logger LOGGER = Logger.getLogger(ApiUser.class);

    @ResponseBody
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "按照id获取用户信息", notes = "修改密码")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户昵称", required = true)})
    public String getUserInfo(String userid) {
        try {
            CustomerModel userModel = new CustomerModel();
            userModel.setUid(userid);
            userModel = customerService.getModelById(userid);
            userModel.setPassword(null);
            return ResultData.toSuccessString(userModel);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/address/editPassword", method = RequestMethod.POST)
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户昵称", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "oldpwd", defaultValue = "1", value = "旧密码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "newpwd", defaultValue = "1", value = "新密码", required = true)})
    public String editPassword(String userid, String oldpwd, String newpwd) {
        try {
            String fileType = "usericon";
            CustomerModel userModel = new CustomerModel();
            userModel.setUid(userid);
            userModel.setPassword(oldpwd);
            return customerService.editPassword(userModel, newpwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultData.toErrorString();
    }

    @ResponseBody
    @RequestMapping(value = "/address/editUserByInfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户昵称/头像", notes = "修改用户昵称/头像")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "nickname", defaultValue = "1", value = "用户昵称", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户id", required = true)})
    public String editUserByInfo(String iconId,String iconHref, String nickname, String userid) {
        try {
            String fileType = "usericon";
            if (StringUtils.isBlank(userid)) {
                return ResultData.toErrorString("用户数据不能为空");
            }
            CustomerModel userModel = new CustomerModel();
            userModel.setNickname(nickname);
            userModel.setUid(userid);
            userModel.setIcon(iconId);
            userModel = customerService.editUser(userModel, iconHref);
            return ResultData.toSuccessDataObj(userModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultData.toErrorString();
    }

    @ResponseBody
    @RequestMapping(value = "/address/editUser", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户昵称/头像", notes = "修改用户昵称/头像")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "nickname", defaultValue = "1", value = "用户昵称", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户id", required = true)})
    public String editUser(@ApiParam(value = "用户头像") MultipartFile files, String nickname, String userid) {
        try {
            String fileType = "usericon";
            if (StringUtils.isBlank(userid)) {
                return ResultData.toErrorString("用户数据不能为空");
            }
            CustomerModel userModel = new CustomerModel();
            userModel.setNickname(nickname);
            userModel.setUid(userid);
            userModel = customerService.editUser(userModel, files);
            return ResultData.toSuccessDataObj(userModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultData.toErrorString();
    }

    @ResponseBody
    @RequestMapping(value = "/address/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增地址", notes = "按照用户ID新增地址")
    public String addressAdd(UseraddressModel addressModel) {
        addressModel.setCreatetime(new Date());
        try {
            addressModel.setValid(ConstantVariable.VALID_ENABLE);
            addressModel = useraddressService.add(addressModel);
            return ResultData.toSuccessString(addressModel);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultData.toErrorString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/address/find", method = RequestMethod.GET)
    @ApiOperation(value = "查找地址", notes = "按照用户ID查找地址")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "usertoken", defaultValue = "1", value = "用户鉴权信息", required = false)})
    public String addressFind(String userid, String usertoken) {
        if(StringUtils.isBlank(userid)){
            return ResultData.toErrorString("用户信息不能为空");
        }
        UseraddressModel model = new UseraddressModel();
        model.setUserid(userid);
        model.setValid(ConstantVariable.VALID_ENABLE);
        return useraddressService.getData(model).toString();
    }

    @RequestMapping(value = "/address/del", method = RequestMethod.POST)
    @ApiOperation(value = "删除地址", notes = "删除地址")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "addressid", defaultValue = "1", value = "地址ID", required = true)})
    public String addressDel(String addressid, String userid) {
        try {
            UseraddressModel model = new UseraddressModel();
            model.setUserid(userid);
            model.setAddressid(addressid);
            model.setValid(ConstantVariable.VALID_DISABLE);
            useraddressService.update(model);
            return ResultData.toSuccessString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultData.toErrorString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/address/setIsDefault", method = RequestMethod.POST)
    @ApiOperation(value = "设置默认地址", notes = "按照用户ID查找地址")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "addressid", defaultValue = "1", value = "地址ID", required = true)})
    public String setIsDefault(String addressid, String userid) {
        try {
            useraddressService.setIsDefault(userid, addressid);
            return ResultData.toSuccessString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultData.toErrorString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/address/addressEdit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑地址", notes = "编辑地址，不能设置是否默认")
    public String addressEdit(UseraddressModel addressModel) {
        try {
            addressModel.setIsdefault(null);
            useraddressService.update(addressModel);
            return ResultData.toSuccessString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultData.toErrorString();
        }
    }
}