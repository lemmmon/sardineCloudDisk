package com.sardine.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class SardineConfig {
    @Value("${sardine.cache}")
    private String cachePath;
}
