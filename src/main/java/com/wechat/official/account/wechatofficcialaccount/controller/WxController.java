package com.wechat.official.account.wechatofficcialaccount.controller;

import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxConfig;
import com.soecode.wxtools.api.WxService;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.util.crypto.SHA1;
import com.soecode.wxtools.util.xml.XStreamTransformer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/wx")
public class WxController {

    private IService iService = new WxService();


    @GetMapping
    public String check(String signature, String timestamp, String nonce, String echostr) {
        if (iService.checkSignature(signature, timestamp, nonce, echostr)) {
            return echostr;
        }
        return echostr;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String gen = SHA1.gen(WxConfig.getInstance().getToken(), "123456789", "123456789");
        System.out.println(gen);
    }


    @PostMapping
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
         PrintWriter out = response.getWriter();
        try {
            // 微信服务器推送过来的是XML格式。
            WxXmlMessage wx = XStreamTransformer.fromXml(WxXmlMessage.class, request.getInputStream());
            System.out.println("消息：\n " + wx.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }


}
