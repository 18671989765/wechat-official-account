package com.wechat.official.account.wechatofficcialaccount.service.impl;

import com.wechat.official.account.wechatofficcialaccount.dto.InitiateBargainingDto;

import com.wechat.official.account.wechatofficcialaccount.mapper.InitiatebargainingMapper;
import com.wechat.official.account.wechatofficcialaccount.service.initiatebargainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/9/20 15:49
 */
@Service
public class InitiatebargainingServiceImpl implements initiatebargainingService {


    @Autowired
    private InitiatebargainingMapper initiatebargainingMapper;

    @Override
    public void addInitiatebargainingInfo(InitiateBargainingDto initiateBrgainingDto) {
        initiatebargainingMapper.addInitiatebargainingInfo(initiateBrgainingDto);
    }
}
