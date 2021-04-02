package com.store.service;

import com.store.config.shiro.CustomizedToken;
import com.store.config.shiro.LoginType;
import com.store.config.shiro.UserType;
import com.store.dao.SysadminMapper;
import com.store.model.CustomerModel;
import com.store.model.SysadminModel;
import com.store.model.ResultData;
import com.store.utils.CodeUtil;
import com.store.utils.ConstantVariable;
import com.store.utils.EncryptUtils;
import com.store.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

@Slf4j
@Service("sysadminService")
public class SysadminService {

    @Autowired
    private SysadminMapper sysadminMapper;

    @Autowired
    private ExcelUtil<SysadminModel> excelUtil;

    @PostConstruct
    private void initAdmin() {
        SysadminModel user = new SysadminModel();
        String loginno = "admin";
        user.setLoginno(loginno);
        if (this.getCount(user) == 0) {
            log.info("初始化超级管理员！");
            user.setPassword(EncryptUtils.encrypt(loginno, "admin123"));
            user.setUsername("管理员");
            user.setRole("7daa2898-9b6c-11e8-93d5-00ff57a662b1");
            user.setStore("c7858ed0-9b00-11e8-93d5-00ff57a662b1");
            user.setValid("1");
            user.setAdminid(EncryptUtils.md5(System.currentTimeMillis() + "" + CodeUtil.getRandomNumber(6)));
            user.setRegistertime(new Date());
            this.insert(user);
        }
    }

    public XSSFWorkbook export(SysadminModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<SysadminModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(SysadminModel model) {
        ResultData returnData = new ResultData();
        try {
            List<SysadminModel> dataList = this.getList(model);
            Iterator<SysadminModel> it = dataList.iterator();
            while (it.hasNext()) {
                SysadminModel adminModel = it.next();
                if (adminModel.getLoginno().equals(ConstantVariable.SUPER_ADMIN)) {
                    it.remove();
                    break;
                }
            }
            returnData.setDataList(dataList);
            int dataCount = this.getCount(model);
            dataCount = dataCount - 1;

            returnData.setPageCount(dataCount, model.getPageSize());
            returnData.setDataCount(dataCount);
            returnData.setState(ConstantVariable.SUCCESS);
            returnData.setPageNumber(model.getPageNumber());
        } catch (Exception e) {
            returnData.setState(ConstantVariable.ERROR);
            returnData.setMsg(e.getMessage());
        }
        return returnData;
    }

    public SysadminModel getOneModel(SysadminModel model) {
        List<SysadminModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<SysadminModel> getList(SysadminModel model) {
        return sysadminMapper.getList(model);
    }

    public int getCount(SysadminModel model) {
        return sysadminMapper.getCount(model);
    }

    public SysadminModel insert(SysadminModel model) {
        sysadminMapper.insert(model);
        return model;
    }

    public SysadminModel update(SysadminModel model) {
        sysadminMapper.update(model);
        return model;
    }

    public List<SysadminModel> batchUpdate(List<SysadminModel> modelList) {
        for (SysadminModel model : modelList) {
            sysadminMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(SysadminModel model) {
        sysadminMapper.disableOrEnable(model);
    }

    public void delete(SysadminModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(model.getAdminid());
        model.setIdList(idList);
        sysadminMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        SysadminModel model = new SysadminModel();
        model.setIdList(idList);
        sysadminMapper.delete(model);
        return ResultData.toSuccessString();
    }

    public String login(SysadminModel user) {
        // 当前用户
        Subject currentUser = SecurityUtils.getSubject();
        SysadminModel dbUser = this.getOneModel(new SysadminModel(user.getLoginno()));
        if (dbUser == null) {
            return ResultData.toErrorString("用户不存在");
        }
        String password = user.getPassword();

        // 登录
        CustomizedToken token = new CustomizedToken(user.getLoginno(), password, null, UserType.ADMIN, LoginType.PWD);
        currentUser.login(token);
        SysadminModel loginUser = (SysadminModel) currentUser.getPrincipal();
        if (loginUser == null) {
            return ResultData.toErrorString("登录异常");
        }
        loginUser.setUsertoken(ObjectUtils.toString(currentUser.getSession().getId()));
        currentUser.getSession().setTimeout(-1);
        return ResultData.toSuccessDataObj(loginUser, "登录成功");
    }
}