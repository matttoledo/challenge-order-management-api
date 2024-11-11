package com.mouts.orders_manegement_api.client;

public interface RedisClient {
    void setex(String key, Integer ttl, String value);

    String get(String key);
}
