package com.wechat.official.account.wechatofficcialaccount.service;

import com.wechat.official.account.wechatofficcialaccount.entity.UserInfo;


/**
 * @author Administrator
 * @version 1.0
 * @date 2020/9/19 20:50
 */

public interface UserInfoService {

    void addWeChatUserInfo(UserInfo userInfo);

    UserInfo queryWeChatUserInfoByOpenId(String openId);

    void updateWeChatNumberOfVisits(String openId);

}
