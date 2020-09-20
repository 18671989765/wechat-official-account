package com.wechat.official.account.wechatofficcialaccount.controller;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.core.util.Base64Encoder;
import com.wechat.official.account.wechatofficcialaccount.config.WaChatAppIdInfos;
import com.wechat.official.account.wechatofficcialaccount.entity.UserInfo;
import com.wechat.official.account.wechatofficcialaccount.entity.WeixinBackInfo;
import com.wechat.official.account.wechatofficcialaccount.service.impl.UserInfoServiceImpl;
import com.wechat.official.account.wechatofficcialaccount.util.HttpClientUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/9/18 21:28
 */
@RestController
public class OAuthController {


    @Autowired
    private UserInfoServiceImpl userInfoService;


    /**
     * 重定向地址取出code 换网页授权 access_token ，一句access_token 取用户信息
     * @param fromUserName 发起人openId
     * @param request
     * @param response
     * @param code 认证授权的code
     */
    @GetMapping("/getAccessToken/{fromUserName}")
    public void   getAuthAccessToken(@PathVariable("fromUserName")String fromUserName, HttpServletRequest request, HttpServletResponse response, String code) throws IOException {
        StringBuffer urlToken = new StringBuffer("https://api.weixin.qq.com/sns/oauth2/access_token?");
        urlToken.append("appid=").append(WaChatAppIdInfos.appId).append("&secret=").append(WaChatAppIdInfos.appSecret).append("&code=")
                .append(code).append("&grant_type=authorization_code");

        String url = urlToken.toString();
        String tokenInfo = HttpClientUtil.get(url);
        WeixinBackInfo weixinBackInfo = JSONObject.parseObject(tokenInfo, WeixinBackInfo.class);
        System.out.println("网页授权返回的access_token:" + weixinBackInfo);

        UserInfo userInfo = this.getUserInfo(weixinBackInfo.getAccess_token(), weixinBackInfo.getOpenid());

        UserInfo userInfoExists = userInfoService.queryWeChatUserInfoByOpenId(userInfo.getOpenid());
        if (null == userInfoExists) {

            userInfo.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            System.out.println(userInfo);
            userInfoService.addWeChatUserInfo(userInfo);
        } else {
            userInfoService.updateWeChatNumberOfVisits(userInfo.getOpenid());
        }
        response.sendRedirect(WaChatAppIdInfos.serviceUrl+"/success?openId="+userInfo.getOpenid()+"&faQiRenOpenId="+fromUserName);
    }


    /**
     * 根据access_token 获取用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public UserInfo getUserInfo(String accessToken, String openId) {
        Map<String, String> data = new HashMap();
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            String response = EntityUtils.toString(httpEntity, "utf-8");
            return JSONObject.parseObject(response, UserInfo.class);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
