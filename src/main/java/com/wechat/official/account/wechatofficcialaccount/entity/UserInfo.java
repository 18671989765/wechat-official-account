package com.wechat.official.account.wechatofficcialaccount.entity;

import lombok.Data;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/9/19 17:54
 */
@Data
public class UserInfo {
    private String language;
    private String openid;
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String  createtime;
    //访问次数
    private int numberOfVisits;
    private String  privilege;
}
