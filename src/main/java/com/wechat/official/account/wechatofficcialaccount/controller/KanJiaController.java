package com.wechat.official.account.wechatofficcialaccount.controller;

import com.wechat.official.account.wechatofficcialaccount.dto.initiateBargainingDto;
import com.wechat.official.account.wechatofficcialaccount.wx.util.BargainCommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
    /**
     * 发起砍价
     * @param  initiateBargainingDto
     * @return
     */
    @PostMapping("/initiateBargainingBegin")
    public initiateBargainingDto initiateBargainingBegin(@ModelAttribute("initiateBargainingDto") @RequestBody initiateBargainingDto initiateBargainingDto){
        log.info("发起了一个砍价请求：",initiateBargainingDto);
        //校验验证码是否正确
        //砍价次数 从数据库中查
        int count = 10;
        //砍价金额 从数据库中查
        BigDecimal price = BigDecimal.valueOf(10L);
        BigDecimal finalPrice =BigDecimal.ZERO;
        List<BigDecimal> list = BargainCommonUtils.getTempBargainList(count, price, finalPrice);
        initiateBargainingDto.setPrice(list.get(0).doubleValue());
        return initiateBargainingDto;
    }

}
