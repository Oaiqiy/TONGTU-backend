package com.tongtu.tongtu.api.oss;

import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.domain.User;
import io.netty.util.collection.LongObjectHashMap;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/oss")
public class UploadController {
    private final RedisTemplate<String,String> redisTemplate;
    private final EntityManager entityManager;
    private final static RedisScript<Long> script = RedisScript.of(new ClassPathResource("lua/preupload.lua"),Long.class);

    /**
     * upload control (token)
     * @param size the size of uploading file
     * @param MD5 the file's MD5 code
     * @param id device id
     * @return if code equals 0,success. else error
     */


    @GetMapping("/upload")
    @Transactional
    public ResultInfo<Object> preUpload(Long size,String MD5,Long id){
        if(size <= 0)
            return new ResultInfo<>(3,"empty file");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();




        List<String> keys = new ArrayList<>(2);
        String username = user.getUsername();
        keys.add(username+":temp");
        keys.add(username+":" + id + ":files");

        Long code = redisTemplate.execute(script,keys,size.toString(),MD5,user.getUsedStorage().toString(),user.getMaxStorage().toString());



        if(code == null)
            return new ResultInfo<>(10,"unknown error");
        else if(code == 0)
            return new ResultInfo<>(0,"success");
        else if(code == 1)
            return new ResultInfo<>(1,"file too large");
        else if(code == 2)
            return new ResultInfo<>(2,"repeated file");
        else
            return new ResultInfo<>(10,"unknown error");


    }


}
