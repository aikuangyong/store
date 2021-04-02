package com.store.config.shiro;

import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author licfe
 * Shiro的相关配置
 */
@Configuration
public class ShiroConfig {

    /**
     * 这里统一做鉴权，即判断哪些请求路径需要用户登录，哪些请求路径不需要用户登录。
     * 这里只做鉴权，不做权限控制，因为权限用注解来做。
     *
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
        // 注销过滤器
        chain.addPathDefinition("/logout", "logout");
        // 哪些请求可以匿名访问
        // 用户登录与注册相关的请求可以匿名
        chain.addPathDefinition("/admin/login", "anon");
        chain.addPathDefinition("/getUeditorConfig", "anon");
        chain.addPathDefinition("/api/login/**", "anon");
        chain.addPathDefinition("/api/**", "anon");
        chain.addPathDefinition("/verify", "anon");
        // 静态文件可以匿名
        chain.addPathDefinition("/static/**", "anon");
        // 模板文件可以匿名
        chain.addPathDefinition("/templates/**", "anon");
        // UE可以匿名
        chain.addPathDefinition("/ueditor/**", "anon");
        // Swagger可以匿名
        chain.addPathDefinition("/swagger-ui.html/**", "anon");
        chain.addPathDefinition("/v2/api-docs", "anon");
        chain.addPathDefinition("/swagger-resources/**", "anon");
        chain.addPathDefinition
                ("/webjars/**", "anon");
        // Shiro的静态角色权限的Demo
        chain.addPathDefinition("/shiro/demo/hello", "anon");
        // 不需要登录的开放API
        chain.addPathDefinition("/api/public/**", "anon");
        chain.addPathDefinition("/m/upload/**", "anon");
        chain.addPathDefinition("/m/*/export", "anon");

        //除了以上的请求外，其它请求都需要登录
//        chain.addPathDefinition("/**", "authc");
        chain.addPathDefinition("/**", "anon");
        return chain;
    }

    /**
     * 注入自定义的管理员realm，告诉shiro如何获取用户信息来做登录或权限控制
     *
     * @return
     */
    @Bean
    public Realm adminRealm() {
        return new AdminRealm();
    }

    /**
     * 注入自定义的普通用户realm，告诉shiro如何获取用户信息来做登录或权限控制
     *
     * @return
     */
    @Bean
    public Realm userRealm() {
        return new UserRealm();
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @return
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 自定义sessionManager，用于前后端分离
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        return mySessionManager;
    }

    /**
     * 自定义Realm，用于多类型登录
     *
     * @return
     */
    @Bean
    public ModularRealmAuthenticator authenticator() {
        CustomizedModularRealmAuthenticator realmAuthenticator = new CustomizedModularRealmAuthenticator();
        return realmAuthenticator;
    }

}
