package com.wechat.official.account.wechatofficcialaccount.mapper;

import com.wechat.official.account.wechatofficcialaccount.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/9/19 20:51
 */
@Repository
public interface UserInfoMapper {

    void addWeChatUserInfo(@Param("userInfo") UserInfo userInfo);

    UserInfo queryWeChatUserInfoByOpenId(@Param("openId")String openId);

   void  updateWeChatNumberOfVisits(@Param("openId")String openId);
}
