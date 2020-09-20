package com.wechat.official.account.wechatofficcialaccount.dto;

import lombok.Data;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/9/13 22:03
 * 点击砍价需要传递的信息
 */
@Data
public class InitiateBargainingDto {

    //发起砍价人的openID
    private String faQiRenOpenId;

    //朋友的openId
    private String bangKanOpenId;

    //发起砍价的验证码
    private String authCode;

    //砍掉的金额
    private double price;

    //砍价时间
    private String createTime;

    //砍价次数
    private int num;
    //后台给出的提示信息 备注
    private String remark;
}
