package com.store.config.shiro;

import com.store.model.CustomerModel;
import com.store.model.ResultData;
import com.store.model.SmsinfoModel;
import com.store.service.CustomerService;
import com.store.service.SmsinfoService;
import com.store.utils.ConstantVariable;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author licfe
 * 自定义权限匹配和账号密码匹配
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    CustomerService customerService;

    @Autowired
    SmsinfoService smsinfoService;

    @PostConstruct
    public void initCredentialsMatcher(){
        //告诉Shiro如何根据获取到的用户信息中的密码和盐值来校验密码
        //设置用于匹配密码的CredentialsMatcher
        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
        hashMatcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
        hashMatcher.setStoredCredentialsHexEncoded(false);
        hashMatcher.setHashIterations(1024);
        this.setCredentialsMatcher(hashMatcher);
    }

    /**
     * 权限配置
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        System.out.println("UserRealm.doGetAuthenticationInfo()");
        CustomizedToken customizedToken = (CustomizedToken) token;
        String username = customizedToken.getUsername();
        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null phone are not allowed by this realm.");
        }
        CustomerModel userDB = customerService.findUserByPhone(username);
        if (userDB == null) {
            throw new UnknownAccountException("No account found for [" + username + "]");
        }
        if(userDB.getValid() == 0){
            throw new DisabledAccountException("账号已禁用！");
        }
        if(customizedToken.getLoginType().equals(LoginType.SMS)){
            // 短信登录
            SmsinfoModel smsModel = new SmsinfoModel();
            smsModel.setPhoneno(username);
            smsModel.setVerifycode(new String(customizedToken.getVerifyCode()));
            smsModel.setSmstype(ConstantVariable.SMS_LOGIN);
            smsModel = smsinfoService.getLastSmsInfo(smsModel);
            if (null == smsModel) {
                throw new AuthenticationException("验证码输入有误！");
            }
            if (System.currentTimeMillis() / 1000 - smsModel.getSendtime().getTime() / 1000 > 1800) {
                throw new AuthenticationException("短信已过期，请重新发送！");
            }
            Md5Hash md5Hash = new Md5Hash(customizedToken.getVerifyCode(), smsModel.getPhoneno(),1024);
            // 这里直接模拟验证码当密码登录，也可以使用其他的方式，例如自定义Filter，添加专门用于验证码登录的Realm，或继承HashedCredentialsMatcher并重写doCredentialsMatch
            customizedToken.setPassword(customizedToken.getVerifyCode().toCharArray());
            userDB.setPassword(md5Hash.toBase64());
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDB, userDB.getPassword(), getName());
        info.setCredentialsSalt(ByteSource.Util.bytes(userDB.getPhoneno()));
        return info;
    }
}
