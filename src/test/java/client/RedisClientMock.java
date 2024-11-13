package client;

import com.mouts.orders_manegement_api.client.RedisClient;

import java.util.HashMap;
import java.util.Map;

public class RedisClientMock implements RedisClient {

    private final Map<String, String> recordsMap = new HashMap<>();
    @Override
    public void setex(String key, Integer ttl, String value) {
        recordsMap.put(key, value);
    }

    @Override
    public String get(String key) {
        return recordsMap.get(key);
    }

    public Map<String, String> getRecordsMap(){
        return recordsMap;
    }

}
