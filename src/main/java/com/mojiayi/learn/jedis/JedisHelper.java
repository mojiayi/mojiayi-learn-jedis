package com.mojiayi.learn.jedis;

import com.mojiayi.learn.jedis.exception.IllegalKeyItemException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.JedisPool;

public class JedisHelper {
    private static final char DELIMITER = ':';

    protected String storeValueKey = "key-value";
    protected String storeKeyKey = "value-key";

    /**
     * 连接池对象
     */
    protected JedisPool jedisPool;

    /**
     * 对应的应用系统名
     * */
    protected String appName;


    /**
     * 对应的数据范围
     */
    protected String dataScope;

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

    public String createStringKey(String app, String jedisFrame,String userFor,String storeType,String key) {
        return createKey(app,jedisFrame,userFor,storeType,key);
    }

    private String createKey(String ... keyItems){
        keyItemsCheck(keyItems);
        return StringUtils.join(keyItems, DELIMITER);
    }

    private void keyItemsCheck(String ... keyItems) {
        if (ArrayUtils.isEmpty(keyItems)) {
            throw new IllegalKeyItemException("key组成项不能为空");
        }
        for (String item : keyItems) {
            if(StringUtils.isAnyBlank(item)){
                throw new IllegalKeyItemException("key组成项不能包含空白,keyItems=" + StringUtils.join(keyItems, DELIMITER));
            }
        }
    }
}
