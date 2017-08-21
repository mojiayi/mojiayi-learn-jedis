package com.mojiayi.learn.jedis.exception;

/**
 * Created by Administrator on 2017/8/21.
 */
public class DataStoreGetException extends RuntimeException {
    public DataStoreGetException(){
        super();
    }

    public DataStoreGetException(String message) {
        super(message);
    }

    public DataStoreGetException(Throwable cause) {
        super(cause);
    }

    public DataStoreGetException(String message, Throwable cause) {
        super(message, cause);
    }
}
