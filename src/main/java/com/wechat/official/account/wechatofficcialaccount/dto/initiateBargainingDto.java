package com.wechat.official.account.wechatofficcialaccount.dto;

import lombok.Data;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/9/13 22:03
 * 点击砍价需要传递的信息
 */
@Data
public class initiateBargainingDto {

    //发起砍价人的openID
    private String openId;

    //朋友的openId
    private String frendsOpenId;

    //发起砍价的验证码
    private String authCode;

}
