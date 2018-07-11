package com.khy.auth2server.config;

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

@Service
public class MyClientDetailsService implements ClientDetailsService {

    private ClientDetailsService clientDetailsService;

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        BaseClientDetails details = new BaseClientDetails();
        ClientDetail client = (ClientDetail) this.baseDao.getUniqueResultByHql("from ClientDetail where clientName=? and deleted=0", clientId);
        if (client != null) {
            //设置客户端id
            details.setClientId(client.getClientName());
            //客户端私钥
            details.setClientSecret(client.getClientSecret());
            //受保护资源id
            List<String> resourceList = new ArrayList<>();
            resourceList.add(client.getResource().getResourceKey());
            details.setResourceIds(resourceList);
            //oauth2保护模式，本项目默认全部是客户端认证模式
            List<String> authorizedGrantTypes = new ArrayList<>();
            authorizedGrantTypes.add("client_credentials");
            details.setAuthorizedGrantTypes(authorizedGrantTypes);
            //客户端传入的参数
            List<String> scopList = new ArrayList<>();
            scopList.add("view");
            scopList.add(client.getScope());
            details.setScope(scopList);
            //设置此客户端持有的用户组
            Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
            if (bap.util.StringUtil.isNotEmpty(client.getAuthoritites())) {
                String[] authArray = client.getAuthoritites().split(",");
                for (String auth : authArray) {
                    auths.add(new SimpleGrantedAuthority(auth));
                }
            }
            details.setAuthorities(auths);
            //设置token有效时间，单位是秒，(如果不设置，框架内部默认是12小时，本平台设置默认2小时)
            details.setAccessTokenValiditySeconds(Integer.parseInt(ConfigItem.TokenValiditySeconds.getVal()));
        }
        return details;

//        return null;
    }
}
