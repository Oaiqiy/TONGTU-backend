package com.tongtu.tongtu.mq;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
@ConfigurationPropertiesScan("com.tongtu.tongtu.task")
public class MessageQueueConfig {

    /**
     * rabbit消息转换器
     * @return message converter bean
     */

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
