package com.sardine.common;

public enum HealthStatus {
    // 正常
    NORMAL(0),

    // 不正常
    ABNORMAL(1);

    int code;

    HealthStatus(int code) {
        this.code = code;
    }

    public int getValue() {
        return this.code;
    }
}
