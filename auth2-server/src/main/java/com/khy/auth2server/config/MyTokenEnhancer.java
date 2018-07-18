
package com.khy.auth2server.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;


/**
 * 此处可以设置一些基础设施，以便能够在访问令牌中添加一些自定义声明。
 * 框架提供的标准声明都很好，但大多数情况下我们需要在令牌中使用一些额外的信息来在客户端使用。
 * 我们将定义一个TokenEnhancer来定制我们的Access Token与这些额外的声明。
 *
 * 此类执行了之后才会执行tokenStore 储存access_token
 *
 * 在下面的例子中，我们将添加一个额外的字段“组织”到我们的访问令牌 - MyTokenEnhancer：
 */

public class MyTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();

        Map map = new HashMap();
        additionalInfo.put("is_error", "false");
        additionalInfo.put("code", "200");
        additionalInfo.put("timestamp", String.valueOf(new Date().getTime()));
        additionalInfo.put("message", "success");
        additionalInfo.put("client_id", authentication.getOAuth2Request().getClientId());
        //additionalInfo.put("organization", authentication.getName() + randomAlphabetic(4));
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
