package com.wechat.official.account.wechatofficcialaccount.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/9/13 5:17
 */

@Component
@ConfigurationProperties(prefix = "wx")
public class WaChatAppIdInfos {

    public static  String appId;

    public static String appSecret;

    public static String token;

    public static String aesKey;

    public static String mchId;

    public static String serviceUrl;

    @Value("${wx.appId}")
    public void setAppId(String appId) {
        this.appId = appId;
    }
    @Value("${wx.appSecret}")
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
    @Value("${wx.token}")
    public void setToken(String token) {
        this.token = token;
    }
    @Value("${wx.aesKey}")
    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }
    @Value("${wx.mchId}")
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }
    @Value("${wx.serviceUrl}")
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}
