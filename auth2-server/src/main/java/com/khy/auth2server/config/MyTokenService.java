package com.khy.auth2server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import java.util.Date;
import java.util.UUID;

/**
 * 管理令牌（Managing Token）
 */
public class MyTokenService implements AuthorizationServerTokenServices {

    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 30; // system default 30 days.

    private int accessTokenValiditySeconds = 60 * 60 * 12; // systemn default 12 hours.


    @Autowired
    private MyClientDetailsService myClientDetailsService;

    @Autowired
    private MyTokenEnhancer myTokenEnhancer;

    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        //Create an access token from the value provided.
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(UUID.randomUUID().toString());
        int validitySeconds = getAccessTokenValiditySeconds(authentication.getOAuth2Request());
        //The instant the token expires.
        if (validitySeconds > 0) {
            token.setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
        }
        //The refresh token associated with the access token, if any.
//        token.setRefreshToken(refreshToken);
        //The scope of the token.
        token.setScope(authentication.getOAuth2Request().getScope());

        return myTokenEnhancer != null ? myTokenEnhancer.enhance(token, authentication) : token;
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String s, TokenRequest tokenRequest) throws AuthenticationException {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        return null;
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication oAuth2Authentication) {
        return null;
    }

    /**
     * The access token validity period in seconds
     *
     * @param clientAuth the current authorization request
     * @return the access token validity period in seconds
     */
    protected int getAccessTokenValiditySeconds(OAuth2Request clientAuth) {
        if (myClientDetailsService != null) {
            ClientDetails client = myClientDetailsService.loadClientByClientId(clientAuth.getClientId());
            Integer validity = client.getAccessTokenValiditySeconds();
            if (validity != null) {
                return validity;
            }
        }
        return accessTokenValiditySeconds;
    }

}
