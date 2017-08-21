package com.mojiayi.learn.jedis.exception;

public class IllegalKeyItemException extends RuntimeException {
    public IllegalKeyItemException(){
        super();
    }

    public IllegalKeyItemException(String message) {
        super(message);
    }

    public IllegalKeyItemException(Throwable cause) {
        super(cause);
    }

    public IllegalKeyItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
