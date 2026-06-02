package com.peace.personalhealthmanagementplatform.common.auth;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.peace.personalhealthmanagementplatform.module.auth.vo.CaptchaVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CaptchaStore {

    private final ConcurrentHashMap<String, CaptchaEntry> store = new ConcurrentHashMap<>();

    @Value("${captcha.expire-seconds}")
    private int expireSeconds;

    public CaptchaVO generate() {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(130, 48, 4, 80);
        String key = UUID.randomUUID().toString().replace("-", "");
        long expireTime = System.currentTimeMillis() + expireSeconds * 1000L;
        store.put(key, new CaptchaEntry(captcha.getCode(), expireTime));
        String base64 = captcha.getImageBase64Data();
        return new CaptchaVO(key, base64);
    }

    public boolean verify(String key, String code) {
        if (key == null || code == null) {
            return false;
        }
        CaptchaEntry entry = store.remove(key);
        if (entry == null) {
            return false;
        }
        if (System.currentTimeMillis() > entry.expireTime()) {
            return false;
        }
        return entry.code().equalsIgnoreCase(code);
    }

    @Scheduled(fixedRate = 300000)
    public void cleanExpired() {
        long now = System.currentTimeMillis();
        Iterator<Map.Entry<String, CaptchaEntry>> it = store.entrySet().iterator();
        while (it.hasNext()) {
            if (now > it.next().getValue().expireTime()) {
                it.remove();
            }
        }
    }

    private record CaptchaEntry(String code, long expireTime) {}
}
