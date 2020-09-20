package com.wechat.official.account.wechatofficcialaccount.controller;

import com.wechat.official.account.wechatofficcialaccount.dto.InitiateBargainingDto;
import com.wechat.official.account.wechatofficcialaccount.entity.UserInfo;
import com.wechat.official.account.wechatofficcialaccount.mapper.InitiatebargainingMapper;
import com.wechat.official.account.wechatofficcialaccount.service.impl.InitiatebargainingServiceImpl;
import com.wechat.official.account.wechatofficcialaccount.service.impl.UserInfoServiceImpl;
import com.wechat.official.account.wechatofficcialaccount.util.SimpleFormatterUtil;
import com.wechat.official.account.wechatofficcialaccount.wx.util.BargainCommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/9/16 23:50
 */
@RestController
public class KanJiaController {

    Logger log = LoggerFactory.getLogger(KanJiaController.class);

    @Autowired
    private UserInfoServiceImpl userInfoService;


    @Autowired
    private InitiatebargainingServiceImpl initiatebargainingService;
    /**
     * 发起砍价
     * @param  initiateBargainingDto
     * @return
     */
    @PostMapping("/initiateBargainingBegin")
    public InitiateBargainingDto initiateBargainingBegin(@ModelAttribute("initiateBargainingDto") @RequestBody InitiateBargainingDto initiateBargainingDto){
        log.info("发起了一个砍价请求：",initiateBargainingDto);
        UserInfo userInfoExists = userInfoService.queryWeChatUserInfoByOpenId(initiateBargainingDto.getBangKanOpenId());
        if(null != userInfoExists){
            log.info("已经砍过价，不能再次砍价，可以发送给朋友帮忙砍价");
        }else{
            int count = 10;
            //砍价金额 从数据库中查
            BigDecimal price = BigDecimal.valueOf(10L);
            BigDecimal finalPrice =BigDecimal.ZERO;
            List<BigDecimal> list = BargainCommonUtils.getTempBargainList(count, price, finalPrice);
            double kanDiaoPrice = list.get(0).doubleValue();
            initiateBargainingDto.setPrice(kanDiaoPrice);
            initiateBargainingDto.setCreateTime(SimpleFormatterUtil.simpleFormatter());
            initiatebargainingService.addInitiatebargainingInfo(initiateBargainingDto);
            log.info("砍价成功！");
            log.info("砍价成功！,{}",initiateBargainingDto);

        }

        return initiateBargainingDto;
    }

}
