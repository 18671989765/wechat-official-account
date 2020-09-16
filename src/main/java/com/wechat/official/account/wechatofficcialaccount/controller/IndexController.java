package com.wechat.official.account.wechatofficcialaccount.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wechat.official.account.wechatofficcialaccount.config.AccessTokenInfo;
import com.wechat.official.account.wechatofficcialaccount.config.WaChatAppIdInfos;
import com.wechat.official.account.wechatofficcialaccount.util.JS_Sign;
import com.wechat.official.account.wechatofficcialaccount.wx.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.wechat.official.account.wechatofficcialaccount.config.AccessTokenInfo.accessToken;

@Controller
public class IndexController {

    Logger log = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/index/{fromUserName}")
    public String index(@PathVariable("fromUserName")String fromUserName, Model map,HttpServletRequest request){
        //进来时，判断该用户是否有发起过砍价，如果发起过砍价，则将砍价列表返回
        //用户进入砍价页面，携带了openId 即开始砍价，使用openId记录发起砍价数据
        //发起砍价表，砍价表 如果两个表中都没有数据，则视为发起砍价，数据会存储在两个表
        //如果是帮忙砍价


        map.addAttribute("name","中华石杉");

        return "index";
    }

    /**
     * 读取request中传过来的字符串
     * 有些调用方不知道用什么方式调用，可能是【application/x-www-form-urlencoded】、【text/plain】、【application/json】
     * 该方法都能处理，但是如果是按【application/x-www-form-urlencoded】
     * @param request
     * @return
     * @throws IOException
     */
    private Map<String,Object> getDataFromRequest(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String type = request.getContentType();
        Map<String,Object> receiveMap = new HashMap<String,Object>();
        if("application/x-www-form-urlencoded".equals(type)){
            Enumeration<String> enu = request.getParameterNames();
            while (enu.hasMoreElements()) {
                String key = String.valueOf(enu.nextElement());
                String value = request.getParameter(key);
                receiveMap.put(key, value);
            }
        }else{	//else是text/plain、application/json这两种情况
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            try{
                reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null){
                    sb.append(line);
                }
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                try{
                    if (null != reader){
                        reader.close();
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            receiveMap = JSONObject.toJavaObject(JSON.parseObject(sb.toString()),Map.class);//把JSON字符串转为对象
        }
        return receiveMap;
    }

    /**
     * 获取分享
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/sign")
    @ResponseBody
    public Map<String, String> sign(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println("=================================="+request.getUserPrincipal());
        WaChatAppIdInfos waChatAppIdInfos = new WaChatAppIdInfos();
        response.setHeader("Access-Control-Allow-Origin", "*");
        HttpSession session = request.getSession();
        String url = request.getParameter("url");
        String accesstoken = (String) session.getAttribute(waChatAppIdInfos.getAppId()+"accesstoken_session");
        if(accesstoken == null || "".equals(accesstoken)){
            accesstoken = AccessTokenInfo.accessToken.getAccessToken();
            session.setAttribute(waChatAppIdInfos.getAppId()+"accesstoken_session",accessToken);
//            session.setMaxInactiveInterval(7200);
        }
        Map<String, String> js_data = JS_Sign.getJSSignMapResult( waChatAppIdInfos.getAppId(), waChatAppIdInfos.getAppSecret(), waChatAppIdInfos.getAesKey(),accesstoken,url, request);
        System.out.println("请求接口返回js_data:"+js_data.toString());
        return js_data;
    }



    @GetMapping(value = "/getUser", produces = "application/json; charset=utf-8")
    public String getUser(@RequestParam(name = "code",required = true) String code) {

        /**
         * 获取code后，请求以下链接获取access_token：
         *  https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE
         *  &grant_type=authorization_code
         */

        final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
/**
 * https请求方式: GET
 * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
 */
          final String GET_ACCESS_TOKEN_URL_BY_BACKSTAGE = "https://api.weixin.qq.com/cgi-bin/token";

        /**
         * https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
         */
         final String GET_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info";

        /**
         * https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
         */
        final String GET_SNS_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";


        WaChatAppIdInfos waChatAppIdInfos = new WaChatAppIdInfos();
        //TODO 1、通过code获取access_token
        Map<String, Object> params = new HashMap<>();
        params.put("appid",waChatAppIdInfos.getAppId());
        params.put("secret",waChatAppIdInfos.getAppSecret());
        params.put("code",code);
        params.put("grant_type","authorization_code");
        JSONObject jsonObject = JSONObject.parseObject(HttpClientUtil.doHttpsGet(GET_ACCESS_TOKEN_URL,params));


        log.info("网页授权token:  {}", jsonObject);
        /**
         * {
         *   "access_token":"ACCESS_TOKEN",
         *   "expires_in":7200,
         *   "refresh_token":"REFRESH_TOKEN",
         *   "openid":"OPENID",
         *   "scope":"SCOPE"
         * }
         */
        log.info("通过code获取access_token:{}",jsonObject);
        String accessToken = (String) jsonObject.get("access_token");
        if(StringUtils.isNotEmpty(accessToken)){
            params = new HashMap<>();
            params.put("access_token",accessToken);
            params.put("openid",jsonObject.get("openid"));
            params.put("lang","zh_CN");
            jsonObject = JSONObject.parseObject(HttpClientUtil.doHttpsGet(GET_SNS_USER_INFO_URL,params));
            log.info("用户信息:{}",jsonObject);
            return jsonObject.toJSONString();
        }
        return "";
    }

}
