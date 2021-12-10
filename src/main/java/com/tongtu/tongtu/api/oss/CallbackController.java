package com.tongtu.tongtu.api.oss;

import com.tongtu.tongtu.api.ResultInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/oss/callback")
@Slf4j
@AllArgsConstructor
public class CallbackController {

    private RabbitTemplate rabbitTemplate;

    @PostMapping
    public ResultInfo<CallbackForm> ossCallback(@RequestBody CallbackForm callbackForm){

        rabbitTemplate.convertAndSend("callback",callbackForm);

        return new ResultInfo<>(0, "success",callbackForm);
    }
}
