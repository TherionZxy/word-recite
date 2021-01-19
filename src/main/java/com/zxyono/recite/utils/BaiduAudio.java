package com.zxyono.recite.utils;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * 百度文字转语音接口
 */
@Configuration
public class BaiduAudio {
    private final static String APPID = "23562432";
    private final static String APPKEY = "KnI5slyt2jr1EQpBnILQnndi";
    private final static String SECRET = "0lNfZ5xC8Gn3tci0dEDkO3EUDDuzyVf9";
    // 中语速
    private final static int SPD = 5;
    // 中语调
    private final static int PIT = 5;
    // 中音量
    private final static int VOL = 5;
    // 文件格式(wav)
    private final static int AUE = 6;

    @Bean
    public AipSpeech aipSpeech() {
        AipSpeech aipSpeech = new AipSpeech(APPID, APPKEY, SECRET);
        aipSpeech.setConnectionTimeoutInMillis(2000);
        aipSpeech.setSocketTimeoutInMillis(60000);
        return aipSpeech;
    }

    public static TtsResponse getAudio(AipSpeech aipSpeech, String msg, int per) {
        HashMap<String, Object> options = new HashMap<>();
        options.put("spd", SPD);
        options.put("pit", PIT);
        options.put("per", per);
        options.put("vol", VOL);
        options.put("aue", AUE);
        TtsResponse res = aipSpeech.synthesis(msg, "zh", 1, options);
        return res;
    }

}