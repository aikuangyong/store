package com.store.controller;

import com.store.model.ResultData;
import com.store.model.SysmenuModel;
import com.store.service.SysmenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/m/role")
public class RoleController {

    @Autowired
    private SysmenuService sysmenuService;

    @PostMapping("/saveMenu")
    @ResponseBody
    public String saveMenu(SysmenuModel menu){
        menu = sysmenuService.insert(menu);
        return ResultData.toSuccessString(menu);
    }

}