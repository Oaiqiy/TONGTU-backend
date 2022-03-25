package com.tongtu.tongtu.message;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "tongtu.push")
public class MessagePushProperties {
    private String appKey;
    private String masterSecret;
    private String url;
}
