package com.khy.auth2server.entity;

import com.qiyego.wsgjp.enums.WsgjpAuthCodeEnum;

import java.time.LocalDateTime;

public class WsgjpAuthCode {
    private Long id;

    private Long customerId;

    private String authToken;

    private String appKey;

    private String profileId;

    private String employeeId;

    private String expiresIn;

    private String refreshToken;

    private String reExpiresIn;

    private String timestamp;

    private WsgjpAuthCodeEnum dataStatus;

    private LocalDateTime dataPackageUpdateTime;

    private LocalDateTime tokenUpdateTime;

    private LocalDateTime userAuthUpdateTime;

    private String companyName;

    private String erpCompanyName;

    private String erpUsername;

    private String erpPassword;

    public String getErpCompanyName() {
        return erpCompanyName;
    }

    public void setErpCompanyName(String erpCompanyName) {
        this.erpCompanyName = erpCompanyName;
    }

    public String getErpUsername() {
        return erpUsername;
    }

    public void setErpUsername(String erpUsername) {
        this.erpUsername = erpUsername;
    }

    public String getErpPassword() {
        return erpPassword;
    }

    public void setErpPassword(String erpPassword) {
        this.erpPassword = erpPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken == null ? null : authToken.trim();
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId == null ? null : profileId.trim();
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId == null ? null : employeeId.trim();
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn == null ? null : expiresIn.trim();
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken == null ? null : refreshToken.trim();
    }

    public String getReExpiresIn() {
        return reExpiresIn;
    }

    public void setReExpiresIn(String reExpiresIn) {
        this.reExpiresIn = reExpiresIn == null ? null : reExpiresIn.trim();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp == null ? null : timestamp.trim();
    }

    public WsgjpAuthCodeEnum getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(WsgjpAuthCodeEnum dataStatus) {
        this.dataStatus = dataStatus;
    }

    public LocalDateTime getDataPackageUpdateTime() {
        return dataPackageUpdateTime;
    }

    public void setDataPackageUpdateTime(LocalDateTime dataPackageUpdateTime) {
        this.dataPackageUpdateTime = dataPackageUpdateTime;
    }

    public LocalDateTime getTokenUpdateTime() {
        return tokenUpdateTime;
    }

    public void setTokenUpdateTime(LocalDateTime tokenUpdateTime) {
        this.tokenUpdateTime = tokenUpdateTime;
    }

    public LocalDateTime getUserAuthUpdateTime() {
        return userAuthUpdateTime;
    }

    public void setUserAuthUpdateTime(LocalDateTime userAuthUpdateTime) {
        this.userAuthUpdateTime = userAuthUpdateTime;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}