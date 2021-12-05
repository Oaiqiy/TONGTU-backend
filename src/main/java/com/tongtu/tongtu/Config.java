package com.tongtu.tongtu;

import com.tongtu.tongtu.api.user.MailController;
import com.tongtu.tongtu.data.UserRepository;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;


@Configuration
public class Config {

    /**
     * rabbit消息转换器
     * @return
     */

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }



}
