package com.sardine.common;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ApplicationContext {
    // key: ip, value: 剩余接收心跳的次数：初始值6，每30秒减1。减到0时认为节点挂了
    public Map<String, Integer> hearBeatInfo = new ConcurrentHashMap<>();
}
