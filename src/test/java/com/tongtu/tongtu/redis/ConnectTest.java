package com.tongtu.tongtu.redis;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class ConnectTest {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void connect(){



       if (Boolean.TRUE.equals(redisTemplate.hasKey("tongtu"))){
           System.out.println(redisTemplate.opsForValue().get("tongtu"));
       }else {
           System.out.println("not has tongtu");
       }



    }
}
