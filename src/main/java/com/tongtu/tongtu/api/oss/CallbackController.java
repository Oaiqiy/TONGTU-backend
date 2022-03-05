package com.tongtu.tongtu.api.oss;


import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.security.jwt.TokenProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/oss/callback")
@Slf4j
@AllArgsConstructor
public class CallbackController {

    private final RabbitTemplate rabbitTemplate;
    private final TokenProcessor tokenProcessor;
    private final RedisTemplate<String,String> redisTemplate;

    @PostMapping
    public ResultInfo<CallbackForm> ossCallback(@RequestBody CallbackForm callbackForm){

        if(callbackForm.getToken() == null){

            //TODO: delete file in oss
            return new ResultInfo<>(1,"Missing verification information");
        }

        String username = tokenProcessor.decodeToken(callbackForm.getToken());
        if(username == null){
            //TODO: delete file in oss
            return new ResultInfo<>(2,"Unknown user");
        }

        String size = (String) redisTemplate.opsForHash().get(username+":files",callbackForm.getMD5());
        if(size == null){

        }


        rabbitTemplate.convertAndSend("callback",callbackForm);

        return new ResultInfo<>(0, "success",callbackForm);
    }
}
