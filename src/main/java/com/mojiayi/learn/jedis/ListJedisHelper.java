package com.mojiayi.learn.jedis;

import com.mojiayi.learn.jedis.exception.DataStoreGetException;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */
public class ListJedisHelper extends JedisHelper {
    private final static String DATA_TYPE = "list";

    public boolean lpush(String key, String value) {
        Jedis jedis = null;
        String actualKey = createKey(appName, DATA_TYPE, dataScope, key);
        try {
            jedis = jedisPool.getResource();
            jedis.lpush(actualKey, value);
            return Boolean.TRUE;
        } catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
            throw new DataStoreGetException(String.format("failed to lpush list(%s:%s)", actualKey, value), e);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public List<String> getAll(String key) {
        return lrange(key, 0, -1);
    }

    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = null;
        String actualKey = createKey(appName, DATA_TYPE, dataScope, key);
        try {
            jedis = jedisPool.getResource();
            return jedis.lrange(actualKey, start, end);
        } catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
            throw new DataStoreGetException(String.format("failed to lrange list(%s:%d,%d)", actualKey, start, end), e);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public boolean clearAll(String key) {
        return ltrim(key, 1, 0);
    }

    public boolean ltrim(String key, int start, int end) {
        Jedis jedis = null;
        String actualKey = createKey(appName, DATA_TYPE, dataScope, key);
        try {
            jedis = jedisPool.getResource();
            jedis.ltrim(actualKey, start, end);
            return Boolean.TRUE;
        } catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
            throw new DataStoreGetException(String.format("failed to ltrim list(%s:%d,%d)", actualKey, start, end), e);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }
}
