package com.wechat.official.account.wechatofficcialaccount.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/9/13 5:17
 *
 * 使用静态方式进行熟悉注入
 */
@Configuration
@ConfigurationProperties(prefix = "wx")
public class WaChatAppIdInfos {

    public static  String appId;

    public static  String appSecret;

    public static  String token;

    public static  String aesKey;

    public static  String mchId;

    public static String serversUrl;


    @Value("${wx.appId}")
    public static void setAppId(String appId) {
        WaChatAppIdInfos.appId = appId;
    }
    @Value("${wx.appSecret}")
    public static void setAppSecret(String appSecret) {
        WaChatAppIdInfos.appSecret = appSecret;
    }
    @Value("${wx.token}")
    public static void setToken(String token) {
        WaChatAppIdInfos.token = token;
    }
    @Value("${wx.aesKey}")
    public static void setAesKey(String aesKey) {
        WaChatAppIdInfos.aesKey = aesKey;
    }
    @Value("${wx.mchId}")
    public static void setMchId(String mchId) {
        WaChatAppIdInfos.mchId = mchId;
    }

    @Value("${wx.serversUrl}")
    public static void setServersUrl(String serversUrl) {
        WaChatAppIdInfos.serversUrl = serversUrl;
    }

}
