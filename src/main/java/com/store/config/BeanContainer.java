package com.store.config;

import com.store.utils.CustomDateWebBindingInitializer;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.MultipartConfigElement;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class BeanContainer {
    @Bean
    public FilterRegistrationBean someFilterRegistration1() {
        //新建过滤器注册类
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 添加我们写好的过滤器
        registration.setFilter(new AuthFilter());
        // 设置过滤器的URL模式
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("10240KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }

    @Autowired
    public void setWebBindingInitializer(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        requestMappingHandlerAdapter.setWebBindingInitializer(new CustomDateWebBindingInitializer());
    }

    @Bean
    public MapperScannerConfigurer getBeanMSC() {
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setBasePackage("com.store.dao");
        msc.setAnnotationClass(MapperScan.class);
        return msc;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        AuthFilter httpBasicFilter = new AuthFilter();
        registrationBean.setFilter(httpBasicFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/user/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

    /************* 跨域配置 *************/
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setMaxAge(3600L);
        List<String> urlArr = new ArrayList<String>();
        urlArr.add("*");
        corsConfiguration.setAllowedOrigins(urlArr);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
    /************* 跨域配置 *************/
}