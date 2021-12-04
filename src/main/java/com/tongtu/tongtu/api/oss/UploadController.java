package com.tongtu.tongtu.api.oss;

import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/oss")
public class UploadController {
    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/upload")
    public ResultInfo<Map<String,String>> preUpload(Long size){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String,Object> message = new HashMap<>();
        message.put("name",name);
        message.put("size",size);
        rabbitTemplate.convertAndSend("upload",message);
        
        return null;
    }
}
