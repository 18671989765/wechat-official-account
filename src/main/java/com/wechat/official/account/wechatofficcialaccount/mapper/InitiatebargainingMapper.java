package com.wechat.official.account.wechatofficcialaccount.mapper;

import com.wechat.official.account.wechatofficcialaccount.dto.InitiateBargainingDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/9/20 15:52
 */
@Repository
public interface InitiatebargainingMapper {

    void addInitiatebargainingInfo(@Param("initDto") InitiateBargainingDto initiateBrgainingDto);

    InitiateBargainingDto queryInitiateBargainingByOpenId(@Param("faqirenOpenId")String faqirenOpenId,@Param("bangkanOpenId") String openId);
}
