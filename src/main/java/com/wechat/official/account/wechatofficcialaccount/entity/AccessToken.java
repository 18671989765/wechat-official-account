package com.wechat.official.account.wechatofficcialaccount.entity;

import lombok.Data;

/**
 * AccessToken的数据模型
 * Created by xdp on 2016/1/25.
 */
@Data
public class AccessToken {

    //获取到的凭证
    private String accessToken;
    //凭证有效时间，单位：秒
    private int expiresin;

}
