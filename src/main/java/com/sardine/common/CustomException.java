package com.sardine.common;

public class CustomException extends Exception {
    private long code;

    public long getCode() {
        return this.code;
    }

    public CustomException(String message) {
        super(message);
        this.code = 1;
    }

    public CustomException(long code, String message) {
        super(message);
        this.code = code;
    }
}
