package com.tongtu.tongtu;

import com.tongtu.tongtu.api.user.MailController;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

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


    @EventListener(ApplicationReadyEvent.class)
    public void test() {
        System.out.println("7777");

        //System.out.println(MvcUriComponentsBuilder.fromMethodName(MailController.class, "mailCheck", "fasdf").toUriString());

    }
}
