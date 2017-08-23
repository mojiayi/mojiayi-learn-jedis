package com.mojiayi.learn.jedis;

import com.mojiayi.learn.jedis.exception.DataStoreGetException;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/23.
 */
public class MapJedisHelper extends JedisHelper {
    private final static String DATA_TYPE = "map";

    public boolean hset(String field, String value) {
        Jedis jedis = null;
        String hashKey = createKey(appName, DATA_TYPE, dataScope);
        try {
            jedis = jedisPool.getResource();
            jedis.hset(hashKey, field, value);
            return Boolean.TRUE;
        } catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
            throw new DataStoreGetException(String.format("failed to save map(%s:%s->%s)", hashKey, field, value), e);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public String hget(String field) {
        Jedis jedis = null;
        String hashKey = createKey(appName, DATA_TYPE, dataScope);
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(hashKey, field);
        } catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
            throw new DataStoreGetException(String.format("failed to get map(%s:%s)", hashKey, field), e);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public boolean hdel(String field) {
        Jedis jedis = null;
        String hashKey = createKey(appName, DATA_TYPE, dataScope);
        try {
            jedis = jedisPool.getResource();
            jedis.hdel(hashKey, field);
            return Boolean.TRUE;
        } catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
            throw new DataStoreGetException(String.format("failed to delete map(%s:%s)", hashKey, field), e);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public boolean hexists(String field) {
        Jedis jedis = null;
        String hashKey = createKey(appName, DATA_TYPE, dataScope);
        try {
            jedis = jedisPool.getResource();
            return jedis.hexists(hashKey, field);
        } catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
            throw new DataStoreGetException(String.format("failed to find map(%s:%s)", hashKey, field), e);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public List<String> searchFields(String fieldPrefix) {
        Jedis jedis = null;
        List<String> fields = new ArrayList<>();
        String hashKey = createKey(appName, DATA_TYPE, dataScope);
        try {
            jedis = jedisPool.getResource();
            Set<String> keySet = jedis.hkeys(hashKey);
            if (keySet != null && keySet.size() > 0) {
                for (String key : keySet) {
                    if (key.startsWith(fieldPrefix)) {
                        fields.add(key);
                    }
                }
            }
            return fields;
        } catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
            throw new DataStoreGetException(String.format("failed to search fields map(%s:%s)", hashKey, fieldPrefix), e);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }
}
