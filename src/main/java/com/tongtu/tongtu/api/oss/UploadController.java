package com.tongtu.tongtu.api.oss;

import com.tongtu.tongtu.api.ResultInfo;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/oss")
public class UploadController {
    private final RabbitTemplate rabbitTemplate;

    /**
     * upload control (token)
     * @param size the size of uploading file
     * @param MD5 the file's MD5 code
     * @return if code equals 0,success else error
     */

    @GetMapping("/upload")
    public ResultInfo<Map<String,String>> preUpload(Long size,String MD5){


        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String,String> message = new HashMap<>();
        
        message.put("name",name);
        message.put("size",size.toString());
        message.put("file",MD5);

        Boolean result = (Boolean) rabbitTemplate.convertSendAndReceive("upload","upload",message);


        if(Boolean.TRUE.equals(result)){
            return new ResultInfo<>(0,"upload!");
        }else {
            return new ResultInfo<>(1,"error!");
        }
    }
}
