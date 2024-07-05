package com.example.eshop.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TokenBlacklistService {

    private static final String BLACKLIST_PREFIX = "blacklist:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void blacklistToken(String token, Date expirationDate) {
        long currentTime = System.currentTimeMillis();
        long expirationTime = expirationDate.getTime();

        if (expirationTime > currentTime) {
            long durationInMillis = expirationTime - currentTime;
            long durationInSeconds = TimeUnit.MILLISECONDS.toSeconds(durationInMillis);
            redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "blacklisted", durationInSeconds, TimeUnit.SECONDS);
        }

    }

    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.hasKey(BLACKLIST_PREFIX + token);
    }

}

