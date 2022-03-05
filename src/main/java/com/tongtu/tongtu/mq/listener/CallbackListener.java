package com.tongtu.tongtu.mq.listener;

import com.tongtu.tongtu.api.oss.CallbackForm;
import com.tongtu.tongtu.data.FileInfoRepository;
import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.domain.FileInfo;
import com.tongtu.tongtu.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CallbackListener {
    private UserRepository userRepository;
    private FileInfoRepository fileInfoRepository;
    private RedisTemplate<String,String> redisTemplate;

    @RabbitListener(queues = "callback")
    public void uploadCallback(CallbackForm callbackForm){

        User user= userRepository.findUserByUsername(callbackForm.getAuth());
        user.uploadFile(callbackForm.getSize(), FileInfo.FileType.OTHER);
        userRepository.save(user);
        fileInfoRepository.save(callbackForm.toFileInfo());
        redisTemplate.opsForList().remove(user.getId().toString(),1,callbackForm.getMD5());

    }
}
