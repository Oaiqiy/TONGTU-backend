package com.tongtu.tongtu.mq.listener;

import com.tongtu.tongtu.api.oss.CallbackForm;
import com.tongtu.tongtu.data.DeviceRepository;
import com.tongtu.tongtu.data.FileInfoRepository;
import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.domain.Device;
import com.tongtu.tongtu.domain.FileInfo;
import com.tongtu.tongtu.domain.User;
import com.tongtu.tongtu.message.MessagePushProperties;
import com.tongtu.tongtu.message.form.PushForm;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Component
@AllArgsConstructor
public class CallbackListener {
    private UserRepository userRepository;
    private FileInfoRepository fileInfoRepository;
    private RedisTemplate<String,String> redisTemplate;
    private RestTemplate restTemplate;
    private MessagePushProperties messagePushProperties;
    private DeviceRepository deviceRepository;



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

        String username = callbackForm.getAuth();
        long size = callbackForm.getSize();
        redisTemplate.opsForValue().increment(username+":used",size);
        redisTemplate.opsForValue().increment(username+":temp",-size);

        User user= userRepository.findUserByUsername(username);
        user.uploadFile(size, FileInfo.FileType.values()[callbackForm.getType()]);
        userRepository.save(user);
        FileInfo file = callbackForm.toFileInfo(user);
        fileInfoRepository.save(file);


        Long target = callbackForm.getTarget();
        if(target == null)
            return;


        Optional<Device> deviceOptional = deviceRepository.findById(target);
        if(deviceOptional.isEmpty())
            return;
        Device device = deviceOptional.get();
        String registrationId = device.getRegistrationId();
        if(registrationId == null)
            return;

        PushForm pushForm = new PushForm("all",registrationId,"您收到一个文件："+ file.getName());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(messagePushProperties.getAppKey()+':'+messagePushProperties.getMasterSecret());
        HttpEntity<PushForm> httpEntity = new HttpEntity<>(pushForm,httpHeaders);

        restTemplate.postForObject(messagePushProperties.getUrl(),httpEntity, String.class);


    }
}
