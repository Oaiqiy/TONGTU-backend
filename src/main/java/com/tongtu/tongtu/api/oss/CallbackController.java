package com.tongtu.tongtu.api.oss;


import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.exception.CallbackException;
import com.tongtu.tongtu.security.jwt.TokenProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/oss/callback")
@Slf4j
@AllArgsConstructor
public class CallbackController {

    private final RabbitTemplate rabbitTemplate;
    private final TokenProcessor tokenProcessor;
    private final RedisTemplate<String,String> redisTemplate;

    /**
     * Aliyun OSS callback interface
     * @param callbackForm {@link CallbackForm}
     * @return  0 marks success, other codes mark failure
     */
    @PostMapping
    public ResultInfo<String> ossCallback(@RequestBody CallbackForm callbackForm){
        try {
            if(callbackForm.getAuth() == null)
                throw new CallbackException(1,"Missing verification");

            String username = tokenProcessor.decodeToken(callbackForm.getAuth());

            if(username == null)
                throw new CallbackException(2,"Missing verification");

            String size = (String) redisTemplate.opsForHash().get(username+":files",callbackForm.getMD5());

            if(size == null)
                throw new CallbackException(3,"No file info");

            callbackForm.setAuth(username);

            if(callbackForm.getTarget()!=null)
                rabbitTemplate.convertAndSend("notice",callbackForm);

            rabbitTemplate.convertAndSend("callback",callbackForm);

            return new ResultInfo<>(0, "success");

        } catch (CallbackException e) {
            rabbitTemplate.convertAndSend("delete",callbackForm.toDeleteForm());
            return e.toResultInfo();
        }

    }
}
