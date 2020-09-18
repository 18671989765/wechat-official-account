package com.wechat.official.account.wechatofficcialaccount.util;

import javax.net.ssl.TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 整数管理 用于https请求
 * @author Administrator
 * @version 1.0
 * @date 2020/9/13 5:37
 */
public class MyX509TrustManager implements TrustManager {

    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
