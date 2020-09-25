package com.wechat.official.account.wechatofficcialaccount.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wechat.official.account.wechatofficcialaccount.config.AccessTokenInfo;
import com.wechat.official.account.wechatofficcialaccount.config.WaChatAppIdInfos;
import com.wechat.official.account.wechatofficcialaccount.entity.AccessToken;
import com.wechat.official.account.wechatofficcialaccount.service.impl.UserInfoServiceImpl;
import com.wechat.official.account.wechatofficcialaccount.util.JS_Sign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

    @Autowired
    private WaChatAppIdInfos waChatAppIdInfos;

    @Autowired
    private UserInfoServiceImpl userInfoService;

    @GetMapping("/index/{fromUserName}")
    public void index(@PathVariable("fromUserName") String fromUserName, Model map, HttpServletResponse response) throws IOException {
        //进来时，判断该用户是否有发起过砍价，如果发起过砍价，则将砍价列表返回
        //用户进入砍价页面，携带了openId 即开始砍价，使用openId记录发起砍价数据
        //发起砍价表，砍价表 如果两个表中都没有数据，则视为发起砍价，数据会存储在两个表
        //如果是帮忙砍价

        ////////
        //////// 每一次进来都进行授权，发起人始终携带，砍价人 每次都要进行授权
        //如何判断是发起砍价，还是帮砍价
        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2ab8b2eaa6f2db2&redirect_uri=" + WaChatAppIdInfos.serviceUrl + "/getAccessToken/" + fromUserName + "&response_type=code&scope=snsapi_userinfo&state=caonima#wechat_redirect");


//        UserInfo userInfoExists = userInfoService.queryWeChatUserInfoByOpenId(fromUserName);
//        if (null == userInfoExists) {
//            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2ab8b2eaa6f2db2&redirect_uri=" + WaChatAppIdInfos.serviceUrl + "/getAccessToken" + "&response_type=code&scope=snsapi_userinfo&state=caonima#wechat_redirect");
//        } else {
//            response.sendRedirect(WaChatAppIdInfos.serviceUrl + "/success?openId=" + fromUserName);
//        }

    }

    @GetMapping("/success")
    public ModelAndView success(String openId, String faQiRenOpenId) {
        if (openId.equals(faQiRenOpenId)) {
            System.out.println("发起人与砍价人是同一个人");
        }
        Map<String, String> map = new HashMap<>();
        map.put("bangKanOpenId", openId);
        map.put("faQiRenOpenId", faQiRenOpenId);
        map.put("serviceUrl", WaChatAppIdInfos.serviceUrl);
        return new ModelAndView("success", map);
    }


    @GetMapping("/message")
    public ModelAndView success() {
        Map map = new HashMap();

        return new ModelAndView("message", map);
    }

    @GetMapping("/code")
    public String code(){
        return "erweima";
    }


    /**
     * 读取request中传过来的字符串
     * 有些调用方不知道用什么方式调用，可能是【application/x-www-form-urlencoded】、【text/plain】、【application/json】
     * 该方法都能处理，但是如果是按【application/x-www-form-urlencoded】
     *
     * @param request
     * @return
     * @throws IOException
     */
    private Map<String, Object> getDataFromRequest(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String type = request.getContentType();
        Map<String, Object> receiveMap = new HashMap<String, Object>();
        if ("application/x-www-form-urlencoded".equals(type)) {
            Enumeration<String> enu = request.getParameterNames();
            while (enu.hasMoreElements()) {
                String key = String.valueOf(enu.nextElement());
                String value = request.getParameter(key);
                receiveMap.put(key, value);
            }
        } else {    //else是text/plain、application/json这两种情况
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != reader) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            receiveMap = JSONObject.toJavaObject(JSON.parseObject(sb.toString()), Map.class);//把JSON字符串转为对象
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
        System.out.println("==================================" + request.getUserPrincipal());

        response.setHeader("Access-Control-Allow-Origin", "*");
        HttpSession session = request.getSession();
        String url = request.getParameter("url");
        AccessToken accesstoken = (AccessToken) session.getAttribute(WaChatAppIdInfos.appId + "accesstoken_session");
        if (accesstoken == null || "".equals(accesstoken)) {
            accesstoken = AccessTokenInfo.accessToken;
            session.setAttribute(WaChatAppIdInfos.appId + "accesstoken_session", accessToken);
            session.setMaxInactiveInterval(7200);
        }
        Map<String, String> js_data = JS_Sign.getJSSignMapResult(WaChatAppIdInfos.appId, WaChatAppIdInfos.appSecret, WaChatAppIdInfos.aesKey, accesstoken.getAccessToken(), url, request);
        System.out.println("请求接口返回js_data:" + js_data.toString());
        return js_data;
    }


}
