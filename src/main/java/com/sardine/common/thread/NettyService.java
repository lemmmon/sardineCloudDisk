package com.sardine.common.thread;

import com.sardine.netty.KpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class NettyService {

    @Autowired
    private KpServer kpServer;

    @Bean
    public void run() throws Exception {
        Thread thread = new Thread(() -> kpServer.start());
        thread.start();
    }
}
