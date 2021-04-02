package com.store.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author lvshuncai
 * @version 1.0
 * @decription 自定义Authenticator
 * @date 2018/8/26 0026
 */
public class CustomizedModularRealmAuthenticator extends ModularRealmAuthenticator {

    @PostConstruct
    private void init(){
        //一个Realm认证成功则认证成功
        AtLeastOneSuccessfulStrategy atLeastOneSuccessfulStrategy = new AtLeastOneSuccessfulStrategy();
        this.setAuthenticationStrategy(atLeastOneSuccessfulStrategy);
    }

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();
        // 强制转换回自定义的CustomizedToken
        CustomizedToken customizedToken = (CustomizedToken) authenticationToken;
        // 登录类型
        UserType userType = customizedToken.getUserType();
        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 登录类型对应的所有Realm
        Collection<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            if (realm.getName().contains(userType.toString())){
                typeRealms.add(realm);
            }
        }

        // 判断是单Realm还是多Realm
        if (typeRealms.size() == 1){
            return doSingleRealmAuthentication(typeRealms.iterator().next(), customizedToken);
        }else{
            return doMultiRealmAuthentication(typeRealms, customizedToken);
        }
    }

}
