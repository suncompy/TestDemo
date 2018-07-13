/*
package com.khy.auth2server.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.hxp.oauth.server.dao.MybatisTokenDao;
import com.hxp.oauth.server.entity.MybatisOauth2AccessToken;
import com.hxp.oauth.server.entity.MybatisOauth2RefreshToken;

public class MybatisTokenStore implements TokenStore {
    private static final Log LOG = LogFactory.getLog(MybatisTokenStore.class);

    private MybatisTokenDao mybatisTokenDao;

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();


    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        OAuth2Authentication authentication = null;

        try {
            MybatisOauth2AccessToken at=mybatisTokenDao.readAccessToken( token.getValue());
            authentication = SerializationUtils.deserialize(at.getAuthentication());

        }
        catch (EmptyResultDataAccessException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Failed to find access token for token " + token);
            }
        }

        return authentication;
    }

    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String refreshToken = null;
        if (token.getRefreshToken() != null) {
            refreshToken = token.getRefreshToken().getValue();
        }
        MybatisOauth2AccessToken at=new MybatisOauth2AccessToken();
        at.setTokenId(token.getValue());
        at.setToken(SerializationUtils.serialize(token));
        at.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
        at.setAuthentication(SerializationUtils.serialize(authentication));
        at.setRefreshToken(refreshToken);
        mybatisTokenDao.storeAccessToken(at);
    }

    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken accessToken = null;

        try {
            accessToken = SerializationUtils.deserialize(mybatisTokenDao.readAccessToken(tokenValue).getToken());
        }
        catch (EmptyResultDataAccessException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Failed to find access token for token " + tokenValue);
            }
        }

        return accessToken;
    }

    public void removeAccessToken(String tokenValue) {
        mybatisTokenDao.removeAccessToken(tokenValue);

    }

    public OAuth2Authentication readAuthentication(ExpiringOAuth2RefreshToken token) {
        OAuth2Authentication authentication = null;

        try {
            authentication = SerializationUtils.deserialize(mybatisTokenDao.readRefreshToken(token.getValue()).getAuthentication());
        }
        catch (EmptyResultDataAccessException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Failed to find access token for token " + token);
            }
        }

        return authentication;
    }

    public void storeRefreshToken(ExpiringOAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        MybatisOauth2RefreshToken rt=new MybatisOauth2RefreshToken();
        rt.setTokenId(refreshToken.getValue());
        rt.setToken(SerializationUtils.serialize(refreshToken));
        rt.setAuthentication(SerializationUtils.serialize(authentication));
        mybatisTokenDao.storeRefreshToken(rt);
    }

    public ExpiringOAuth2RefreshToken readRefreshToken(String tokenValue) {
        ExpiringOAuth2RefreshToken refreshToken = null;

        try {
            refreshToken = SerializationUtils.deserialize(mybatisTokenDao.readRefreshToken(tokenValue).getToken());
        }
        catch (EmptyResultDataAccessException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Failed to find refresh token for token " + tokenValue);
            }
        }

        return refreshToken;
    }

    public void removeRefreshToken(String tokenValue) {
        mybatisTokenDao.removeRefreshToken(tokenValue);

    }

    public void removeAccessTokenUsingRefreshToken(String refreshToken) {
        mybatisTokenDao.removeAccessTokenUsingRefreshToken(refreshToken);

    }

    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken accessToken = null;

        try {
            String auth=authenticationKeyGenerator.extractKey(authentication);
            MybatisOauth2AccessToken at=mybatisTokenDao.getAccessToken(auth);
            if(null==at){
                return null;
            }else{
                accessToken = SerializationUtils.deserialize(at.getToken());
            }

        }
        catch (EmptyResultDataAccessException e) {
            if (LOG.isInfoEnabled()) {
                LOG.debug("Failed to find access token for authentication " + authentication);
            }
        }

        return accessToken;
    }

    public MybatisTokenDao getMybatisTokenDao() {
        return mybatisTokenDao;
    }

    public void setMybatisTokenDao(MybatisTokenDao mybatisTokenDao) {
        this.mybatisTokenDao = mybatisTokenDao;
    }



}

*/
