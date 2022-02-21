package com.tongtu.tongtu.oss;

import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class PreUpload {
    private UserRepository userRepository;
    private RedisTemplate<String,String> redisTemplate;

    @RabbitListener(queues = "upload")
    @SendTo("upload")
    public Boolean preUpload(@NotNull Map<String,String> message){

        String username = message.get("name");
        if(username==null){
            return false;
        }

        User user = userRepository.findUserByUsername(username);
        if(user == null)
            return false;

        Long usedStorage = user.getUsedStorage();
        String id = user.getId().toString();
        System.out.println(usedStorage);
        System.out.println(message.get("size"));

        if(usedStorage+Long.parseLong(message.get("size"))<user.getMaxStorage()){
            return true;
        }else {
            return false;
        }

//
//        if(!redisTemplate.hasKey(id)){
//            redisTemplate.opsForList().leftPush(id,id+"db");
//            redisTemplate.opsForValue().set(id+"db",usedStorage.toString());
//        }else{
//            for(long i=0;i<redisTemplate.opsForList().size(id);i++){
//                usedStorage += Long.parseLong(redisTemplate.opsForValue().get(redisTemplate.opsForList().index(id,i)));
//            }
//        }
//
//        if(usedStorage+ Long.parseLong(message.get("size"))<=user.getMaxStorage()){
//            redisTemplate.opsForList().leftPush(id,message.get("file"));
//            return true;
//        }else {
//            return false;
//        }


    }
}
