package com.zxyono.recite.utils;

import com.zxyono.recite.entity.wrapper.BaiduResult;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

public class BaiduTranslation {
    public final static String SALT = "zxyf720";

    public static BaiduResult remoteTranslateByBaidu(String word) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api.fanyi.baidu.com/api/trans/vip/translate?";
        String from = "en";
        String to = "zh";
        String appid = "20200725000526261";
        String secret = "xjTB47CdNmrEcXwQ47l0";

        String sign = appid + word + SALT + secret;
        sign = DigestUtils.md5DigestAsHex(sign.getBytes());

        url = url + "q=" + word + "&from=" + from + "&to=" + to + "&appid=" +appid + "&salt=" + SALT + "&sign=" + sign;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, BaiduResult.class).getBody();
    }

    public static void main(String[] args) {
        System.out.println(remoteTranslateByBaidu("wonderful"));
    }
}