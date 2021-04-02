package com.store.api.open;

import com.store.model.*;
import com.store.service.SysmenuService;
import com.store.service.SysmenuroleService;
import com.store.utils.ConstantVariable;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lvshuncai
 * @version 1.0
 * @decription 免登录接口
 * @date 2018/8/26 0026
 */
@Controller
@RequestMapping("/api/public")
@Api(value = "免登录公开接口", tags = "免登录公开接口")
public class PublicController {

    @Autowired
    private SysmenuService sysmenuService;


    @ResponseBody
    @GetMapping("/getMenu")
    public String getMenu(HttpServletRequest request) {
        SysadminModel sysadmin = (SysadminModel) request.getSession().getAttribute(ConstantVariable.ADMIN_USER);

        if (null != sysadmin) {
            if ("admin".equals(sysadmin.getLoginno())) {
                SysmenuModel menu = new SysmenuModel();
                menu.noPage();
                List<SysmenuModel> dataList = sysmenuService.getList(menu);
                return ResultData.toSuccessString(dataList);
            }
            SysmenuModel roleModel = new SysmenuModel();
            roleModel.setRoleid(sysadmin.getRole());
            List<SysmenuModel> menuList = sysmenuService.getMenuByRole(roleModel);
            return ResultData.toSuccessString(menuList);
        } else {
            SysmenuModel menu = new SysmenuModel();
            menu.noPage();
            List<SysmenuModel> dataList = sysmenuService.getList(menu);
            return ResultData.toSuccessString(dataList);
        }
    }
}
