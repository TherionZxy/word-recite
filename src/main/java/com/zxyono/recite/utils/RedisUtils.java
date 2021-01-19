package com.zxyono.recite.utils;

import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisUtils {
    // 缓存从百度语音接口获取到的音频的Base64编码，缓存600秒
    public static void audioCache(RedisTemplate<String, String> redisTemplate, String word, String base64) {
        BoundValueOperations operations = redisTemplate.boundValueOps(word);
        operations.setIfAbsent(base64, 600, TimeUnit.SECONDS);
    }

    public static String getAudioCache(RedisTemplate<String, String> redisTemplate, String word) {
        BoundValueOperations operations = redisTemplate.boundValueOps(word);
        String cache = (String) operations.get();
        if (cache != null) {
            return cache;
        } else {
            return null;
        }
    }
}