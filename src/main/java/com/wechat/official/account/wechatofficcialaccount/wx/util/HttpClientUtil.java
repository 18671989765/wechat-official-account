package com.wechat.official.account.wechatofficcialaccount.wx.util;

import com.wechat.official.account.wechatofficcialaccount.util.MyX509TrustManager;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/9/13 18:14
 */
@Slf4j
public class HttpClientUtil {
    public static final String FH_WH = "?";
    public static final int NUM200 = 200;

    /**
     * 20190621
     *
     * @param url
     * @param params
     * @return
     */
    public static String doHttpsGet(String url, Map<String, Object> params) {
        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        HttpsURLConnection con = null;
        // 尝试发送请求
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{(TrustManager) new MyX509TrustManager()},
                    new SecureRandom());
            // 构建请求参数
            StringBuffer sb = new StringBuffer();
            if (params != null) {
                for (Map.Entry<String, Object> e : params.entrySet()) {
                    sb.append(e.getKey());
                    sb.append("=");
                    sb.append(URLEncoder.encode(String.valueOf(e.getValue()), "UTF-8"));
                    sb.append("&");
                }

            }
            if (url.indexOf(FH_WH) > 0) {
                url += "&";
            } else {
                url += "?";
            }

            url += sb.substring(0, sb.length() - 1).toString();
            log.info("请求url:{}", url);
            con = (HttpsURLConnection) new URL(url).openConnection();
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setConnectTimeout(10 * 60 * 1000);
            con.setReadTimeout(10 * 60 * 1000);
            con.setSSLSocketFactory(sc.getSocketFactory());
            con.setHostnameVerifier(new TrustAnyHostnameVerifier());

            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (con.getResponseCode() == NUM200) {
                InputStream in = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
                String temp;
                while ((temp = br.readLine()) != null) {
                    buffer.append(temp);
                }
                in.close();
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
                con = null;
            }
        }
        return buffer.toString();
    }

    static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            // 直接Pass
            return true;
        }
    }
}
