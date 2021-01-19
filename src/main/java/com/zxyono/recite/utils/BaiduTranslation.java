package com.zxyono.recite.utils;

import com.zxyono.recite.entity.wrapper.BaiduResult;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

/**
 * 百度通用翻译接口工具类，返回翻译结果
 */
public class BaiduTranslation {
    private final static String SALT = "zxyf720";
    private final static String URL = "http://api.fanyi.baidu.com/api/trans/vip/translate?";
    private final static String FROM = "en";
    private final static String TO = "zh";
    private final static String APPID = "20200725000526261";
    private final static String SECRET = "xjTB47CdNmrEcXwQ47l0";

    public static BaiduResult remoteTranslateByBaidu(String word) {
        RestTemplate restTemplate = new RestTemplate();
        String sign = APPID + word + SALT + SECRET;
        sign = DigestUtils.md5DigestAsHex(sign.getBytes());

        String url = URL + "q=" + word + "&from=" + FROM + "&to=" + TO + "&appid=" +APPID + "&salt=" + SALT + "&sign=" + sign;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, BaiduResult.class).getBody();
    }

    public static void main(String[] args) {
        System.out.println(remoteTranslateByBaidu("wonderful"));
    }
}