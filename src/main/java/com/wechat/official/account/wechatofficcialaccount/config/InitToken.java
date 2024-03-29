package com.wechat.official.account.wechatofficcialaccount.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wechat.official.account.wechatofficcialaccount.entity.AccessToken;
import com.wechat.official.account.wechatofficcialaccount.wx.util.NetWorkHelper;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;

/**
 * 项目启动后初始化access_token
 */
@Component
public class InitToken implements ApplicationListener<ContextRefreshedEvent>{



    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("=================系统启动完成后初始化数据====================");
        if (contextRefreshedEvent.getApplicationContext().getParent()==null) {
            try {
                this.init();
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }


    public void init() throws ServletException {

        final String appId = "wxc2ab8b2eaa6f2db2";
        final String appSecret = "2f87a5fc7902c45434f1dd97f01cfa34";

        //开启一个新的线程
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("=================开始初始化====================");
                        //获取accessToken
                        AccessTokenInfo.accessToken = getAccessToken(appId, appSecret);
                        System.out.println(AccessTokenInfo.accessToken.getAccessToken());
                        //获取成功
                        if (AccessTokenInfo.accessToken != null) {
                            //获取到access_token 休眠7000秒,大约2个小时左右
                            Thread.sleep(7000 * 1000);
                            //Thread.sleep(10 * 1000);//10秒钟获取一次
                        } else {
                            //获取失败
                            Thread.sleep(1000 * 3); //获取的access_token为空 休眠3秒
                        }
                    } catch (Exception e) {
                        System.out.println("发生异常：" + e.getMessage());
                        e.printStackTrace();
                        try {
                            Thread.sleep(1000 * 10); //发生异常休眠1秒
                        } catch (Exception e1) {

                        }
                    }
                }

            }
        }).start();
    }

    /**
     * 获取access_token
     *
     * @return AccessToken
     */
    private AccessToken getAccessToken(String appId, String appSecret) {
        NetWorkHelper netHelper = new NetWorkHelper();
        /**
         * 接口地址为https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET，其中grant_type固定写为client_credential即可。
         */
        String Url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appId, appSecret);
        //此请求为https的get请求，返回的数据格式为{"access_token":"ACCESS_TOKEN","expires_in":7200}
        String result = netHelper.getHttpsResponse(Url, "");
        System.out.println("获取到的access_token="+result);
        //使用FastJson将Json字符串解析成Json对象
        JSONObject json = JSON.parseObject(result);
        AccessToken token = new AccessToken();
        token.setAccessToken(json.getString("access_token"));
        token.setExpiresin(json.getInteger("expires_in"));
        return token;
    }




}
