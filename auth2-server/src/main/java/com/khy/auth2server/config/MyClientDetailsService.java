package com.khy.auth2server.config;

import com.khy.auth2server.dao.OauthClientDetailsMapper;
import com.khy.auth2server.entity.OauthClientDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 配置客户端详情服务
 */
@Service
public class MyClientDetailsService implements ClientDetailsService {

    @Autowired
    private OauthClientDetailsMapper oauthClientDetailsMapper;

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        BaseClientDetails details = new BaseClientDetails();
        OauthClientDetails client = oauthClientDetailsMapper.selectByPrimaryKey(s);
        if (client != null) {
            //（必须的）用来标识客户的Id。
            details.setClientId(client.getClientId());
            //（必须的）客户端安全码(客户端私钥);在实际应用中的另一个名称叫appSecret,与client_secret是同一个概念.
            details.setClientSecret(client.getClientSecret());
            //受保护资源id
            List<String> resourceList = new ArrayList<>();
            resourceList.add(client.getResourceIds());
            resourceList.add(ResourceId.DEMO_RESOURCE_ID);
            //客户端所能访问的资源id集合,多个资源时用逗号(,)分隔,如: "unity-resource,mobile-resource".
            details.setResourceIds(resourceList);
            //该client允许的授权类型,默认为空;,oauth2保护模式;客户端认证模式:client_credentials
            List<String> authorizedGrantTypes = new ArrayList<>();
            authorizedGrantTypes.add("client_credentials");
            authorizedGrantTypes.add("refresh_token");
            authorizedGrantTypes.add("authorization_code");
            authorizedGrantTypes.add("password");
            details.setAuthorizedGrantTypes(authorizedGrantTypes);
            //客户端传入的参数
            List<String> scopList = new ArrayList<>();
            scopList.add("view");
            scopList.add("read");
            scopList.add(client.getScope());
            //用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
            details.setScope(scopList);
            //设置此客户端持有的用户组
            Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
            if (StringUtils.isNotEmpty(client.getAuthorities())) {
                String[] authArray = client.getAuthorities().split(",");
                for (String auth : authArray) {
                    auths.add(new SimpleGrantedAuthority(auth));
                }
            }
            //指定客户端所拥有的Spring Security的权限值,可选, 若有多个权限值,用逗号(,)分隔, 如: "ROLE_UNITY,ROLE_USER".
            details.setAuthorities(auths);
            //客户端的重定向URI,可为空
            //details.setRegisteredRedirectUri();
            //设置token有效时间，单位是秒，(如果不设置，框架内部默认是12小时，本平台设置默认2小时)
            details.setAccessTokenValiditySeconds(600);
        }/*else {
            details.setClientSecret("{bcrypt}");
        }*/
        return details;
    }
}
