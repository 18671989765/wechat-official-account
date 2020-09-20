package com.wechat.official.account.wechatofficcialaccount.service.impl;

import com.wechat.official.account.wechatofficcialaccount.mapper.UserInfoMapper;
import com.wechat.official.account.wechatofficcialaccount.entity.UserInfo;
import com.wechat.official.account.wechatofficcialaccount.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/9/19 20:51
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoDao;

    @Override
    public void addWeChatUserInfo(UserInfo userInfo) {
        userInfoDao.addWeChatUserInfo(userInfo);
    }

    @Override
    public UserInfo queryWeChatUserInfoByOpenId(String openId) {
       return  userInfoDao.queryWeChatUserInfoByOpenId(openId);
    }

    @Override
    public void updateWeChatNumberOfVisits(String openId) {
        userInfoDao.updateWeChatNumberOfVisits(openId);
    }
}
