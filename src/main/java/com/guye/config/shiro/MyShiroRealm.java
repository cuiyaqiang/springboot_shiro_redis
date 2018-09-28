package com.guye.config.shiro;

import com.guye.model.Syspermission;
import com.guye.model.Sysrole;
import com.guye.model.UserInfo;
import com.guye.service.TestService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private TestService testService;

    /**
     * 授权用户权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = testService.findByUserName((String)principalCollection.getPrimaryPrincipal(),null);
//        UserInfoT userInfo  = (UserInfoT)principals.getPrimaryPrincipal();
        for(Sysrole role:userInfo.getSysroleList()){
            authorizationInfo.addRole(role.getRole());
            for(Syspermission p:role.getSyspermissionList()){
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
    }

    /**
     * 验证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号和密码.
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = token.getPassword().toString();
        System.out.println(username + " " + password);
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserInfo userInfo = testService.findByUserName(username,null);
        System.out.println("----->>userInfo="+userInfo);
        if(userInfo == null){
            throw new UnknownAccountException();//没找到帐号
        }
        if("1".equals(userInfo.getState())) {
            throw new LockedAccountException(); //帐号锁定
        }
        //设置用户session
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userInfo",userInfo);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo.getUsername(), //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }
}
