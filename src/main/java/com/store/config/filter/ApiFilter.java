package com.store.config.filter;

import com.alibaba.fastjson.JSON;
import com.store.model.ResultData;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/api/**", filterName = "authFilter")
public class ApiFilter implements Filter {

    private static final Logger logger = Logger.getLogger(ApiFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        logger.info("ApiFilter-Parameter:" + servletRequest.getRealPath("/"));
        logger.info("ApiFilter-Parameter:" + JSON.toJSONString(servletRequest.getParameterMap()));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}