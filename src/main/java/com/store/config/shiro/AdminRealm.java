package com.store.config.shiro;

import com.store.model.SysadminModel;
import com.store.model.SysroleModel;
import com.store.service.SysadminService;
import com.store.service.SysmenuroleService;
import com.store.service.SysroleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * @author licfe
 * 自定义权限匹配和账号密码匹配
 */
@Slf4j
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    SysadminService sysadminService;

    @Autowired
    SysroleService sysroleService;


    @Autowired
    SysmenuroleService sysmenuroleService;

    @PostConstruct
    public void initCredentialsMatcher() {
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
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("权限配置-->AdminRealm.doGetAuthorizationInfo()");
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        SysadminModel user = (SysadminModel) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        log.info("获取角色信息：" + user.getRoles());
        log.info("获取权限信息：" + user.getPerms());
        info.setRoles(user.getRoles());
        info.setStringPermissions(user.getPerms());
        return info;
    }

    /**
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        log.info("AdminRealm.doGetAuthenticationInfo()");
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String loginNo = upToken.getUsername();
        char[] password = upToken.getPassword();
        //upToken.setPassword(new String("FMrK/Em0C0XCOd7FhSm2Ug==").toCharArray());
        // Null username is invalid
        if (loginNo == null) {
            throw new AccountException("Null usernames are not allowed by this adminRealm.");
        }
        SysadminModel userDB = sysadminService.getOneModel(new SysadminModel(loginNo));
        if (userDB == null) {
            throw new UnknownAccountException("No account found for admin [" + loginNo + "]");
        }
        if ("0".equals(userDB.getValid())) {
            throw new DisabledAccountException("账号已禁用！");
        }
        // 查询用户的角色和权限存到SimpleAuthenticationInfo中，这样在其它地方
        // SecurityUtils.getSubject().getPrincipal()就能拿出用户的所有信息，包括角色和权限
        SysroleModel role = sysroleService.getModelById(new SysroleModel(userDB.getRole()));
        Set<String> perms = sysmenuroleService.getPermsByRoleId(userDB.getRole());
        userDB.getRoles().add(role.getRolename());
        userDB.getPerms().addAll(perms);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDB, userDB.getPassword(), getName());
        // 盐值加密
        info.setCredentialsSalt(ByteSource.Util.bytes(userDB.getLoginno()));
        return info;
    }
}
