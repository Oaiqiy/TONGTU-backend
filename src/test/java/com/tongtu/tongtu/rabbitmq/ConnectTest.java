package com.tongtu.tongtu.rabbitmq;

import com.tongtu.tongtu.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;

@SpringBootTest
public class ConnectTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;



    @Test
    public void connect() throws Exception{


        MessageConverter converter = rabbitTemplate.getMessageConverter();
        MessageProperties properties = new MessageProperties();

        User horace = new User("horace","passwordEncoder.encode)","1070236799@qq.com");

       // Message message = converter.toMessage("666",properties);
      //  rabbitTemplate.send("test",message);

        rabbitTemplate.convertAndSend("test",horace);



        var t = rabbitTemplate.receiveAndConvert("test", new ParameterizedTypeReference<User>() {
        });
        System.out.println(t);

    }
}
