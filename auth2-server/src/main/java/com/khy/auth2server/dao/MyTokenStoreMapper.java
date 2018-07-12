package com.khy.auth2server.dao;

public interface MyTokenStoreMapper {
    void storeAccessToken(MybatisOauth2AccessToken token);

    MybatisOauth2AccessToken readAccessToken(String tokenValue);

    void removeAccessToken(String tokenValue);

    void storeRefreshToken(MybatisOauth2RefreshToken token);

    MybatisOauth2RefreshToken readRefreshToken(String tokenValue);

    void removeRefreshToken(String tokenValue);

    void removeAccessTokenUsingRefreshToken(String refreshToken);

    MybatisOauth2AccessToken getAccessToken(String authentication);
}
