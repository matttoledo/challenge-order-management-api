package com.mouts.orders_manegement_api.client;

import io.lettuce.core.api.sync.RedisCommands;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RedisClientImpl implements RedisClient {

    @Autowired
    @Qualifier("gift-card-write")
    private RedisCommands<String, String> redisCommandsWrite;

    @Autowired
    @Qualifier("gift-card-read")
    private RedisCommands<String, String> redisCommandsRead;

    @Override
    @Async
    public void setex(String key, Integer ttl, String value) {
        redisCommandsWrite.setex(key, ttl, value);
    }

    @Override
    public String get(String key) {
        return redisCommandsRead.get(key);
    }
}
