package com.mojiayi.learn.jedis;

import com.mojiayi.learn.jedis.exception.DataStoreGetException;
import redis.clients.jedis.Jedis;

public class StringJedisHelper extends JedisHelper {
    private final static String DATA_TYPE = "string";

    /**
     * store key-value pair,default expiry is zero
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, String value) {
        return set(key, value, 0);
    }

    /**
     * store key-value pair,set expiry as specified value
     * @param key
     * @param value
     * @param expiry
     * @return
     */
    public boolean set(String key, String value, int expiry) {
        Jedis jedis = null;
        try {
            String actualKey = createKey(appName, DATA_TYPE, dataScope, key);
            jedis = jedisPool.getResource();
            jedis.set(actualKey, value);
            jedis.expire(actualKey, expiry);
            return true;
        } catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
            throw new DataStoreGetException(String.format("failed to save %s->%s", key, value), e);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * get data of specified key
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = null;
        try {
            String actualKey = createKey(appName, DATA_TYPE, dataScope, key);
            jedis = jedisPool.getResource();
            return jedis.get(actualKey);
        } catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
            throw new DataStoreGetException(String.format("failed to get value,key=%s", key), e);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * set expiry of key
     * @param key
     * @param seconds
     * @return
     */
    public boolean expire(String key, int seconds) {
        if (seconds > 0) {
            throw new DataStoreGetException(String.format("expiry must larger than zero,seconds=%d", seconds));
        }
        Jedis jedis = null;
        String actualKey = createKey(appName, DATA_TYPE, dataScope, key);
        try {
            jedis = jedisPool.getResource();
            jedis.expire(actualKey, seconds);
            return true;
        } catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
            throw new DataStoreGetException(String.format("failed to expiry key=%s", key), e);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * delete data of specified key
     * @param key
     * @return
     */
    public int del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String actualKey = createKey(appName, DATA_TYPE, dataScope, key);
            return jedis.del(actualKey).intValue();
        } catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
            throw new DataStoreGetException(String.format("failed to delete key=%s", key), e);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }
}
