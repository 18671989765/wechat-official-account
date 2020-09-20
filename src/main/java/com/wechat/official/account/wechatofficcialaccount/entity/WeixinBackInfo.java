package com.wechat.official.account.wechatofficcialaccount.entity;

import lombok.Data;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/9/19 17:54
 */
@Data
public class WeixinBackInfo {
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String errcode;
    private String errmsg;

}
