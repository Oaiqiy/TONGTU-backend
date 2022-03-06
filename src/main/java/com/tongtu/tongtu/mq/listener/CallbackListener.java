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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

@Component
@AllArgsConstructor
public class CallbackListener {
    private UserRepository userRepository;
    private FileInfoRepository fileInfoRepository;
    private RedisTemplate<String,String> redisTemplate;
    private EntityManager entityManager;


    @RabbitListener(queues = "callback")
    @Transactional
    public void  uploadCallback(CallbackForm callbackForm){


        User user= userRepository.findUserByUsername(callbackForm.getAuth());


        user.uploadFile(callbackForm.getSize(), FileInfo.FileType.OTHER);
        userRepository.save(user);

        String files = callbackForm.getAuth()+":" + callbackForm.getDevice()+ ":files";
        String temp = callbackForm.getAuth()+":temp";

        redisTemplate.opsForHash().delete(files,callbackForm.getMD5());
        redisTemplate.opsForValue().increment(temp,-callbackForm.getSize());

        fileInfoRepository.save(callbackForm.toFileInfo());

    }
}
