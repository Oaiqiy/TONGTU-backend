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



@Component
@AllArgsConstructor
public class CallbackListener {
    private UserRepository userRepository;
    private FileInfoRepository fileInfoRepository;
    private RedisTemplate<String,String> redisTemplate;


    /**
     * message queue listener,which processes callback data, persist in database
     * @param callbackForm checked callback form
     */


    @RabbitListener(queues = "callback")
    @Transactional
    public void  uploadCallback(CallbackForm callbackForm){

        String files = callbackForm.getAuth()+":" + callbackForm.getDevice()+ ":files";
        long count = redisTemplate.opsForHash().delete(files,callbackForm.getMD5());
        if(count == 0)
            return;

        String temp = callbackForm.getAuth()+":temp";
        redisTemplate.opsForValue().increment(temp,-callbackForm.getSize());

        User user= userRepository.findUserByUsername(callbackForm.getAuth());


        user.uploadFile(callbackForm.getSize(), FileInfo.FileType.values()[callbackForm.getType()]);
        userRepository.save(user);


        fileInfoRepository.save(callbackForm.toFileInfo(user));

        //TODO: send message

    }
}
