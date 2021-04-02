package com.store.api.pos;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/pos/index")
@Api(value = "公共数据", tags = "订单相关")
public class ApiPosIndex {

}