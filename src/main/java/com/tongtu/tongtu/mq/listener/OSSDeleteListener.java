package com.tongtu.tongtu.mq.listener;


import com.aliyun.oss.OSS;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class OSSDeleteListener {
    private final OSS oss;

    /**
     * listener to delete file in OSS.
     * @param deleteForm all attribute should be not null
     */
    @RabbitListener(queues = "delete")
    public void deleteFile(DeleteForm deleteForm){

        log.info("delete: " + deleteForm.getBucket() + " : " + deleteForm.getObject());

        if(deleteForm.getBucket()==null)
            return;

        oss.deleteObject(deleteForm.getBucket(),deleteForm.getObject());
    }
}
