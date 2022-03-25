package com.tongtu.tongtu.message;

import com.tongtu.tongtu.message.form.PushForm;
import io.netty.handler.codec.base64.Base64Encoder;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SpringBootTest
public class PushTest {
    @Autowired
    private MessagePushProperties messagePushProperties;
    @Autowired
    private RestTemplate restTemplate;


    @Test
    public void push(){
        PushForm pushForm = new PushForm("all","100d855909b4e18ac03","您收到一个文件：");
        HttpHeaders httpHeaders = new HttpHeaders();
        System.out.println(Base64.getEncoder().encodeToString((messagePushProperties.getMasterSecret()).getBytes(StandardCharsets.UTF_8)));
        httpHeaders.setBasicAuth(Base64.getEncoder().encodeToString((messagePushProperties.getAppKey()+':'+messagePushProperties.getMasterSecret()).getBytes(StandardCharsets.UTF_8)));
        HttpEntity<PushForm> httpEntity = new HttpEntity<>(pushForm,httpHeaders);

        //restTemplate.postForObject(messagePushProperties.getUrl(),httpEntity, String.class);

    }
}
