package com.tongtu.tongtu.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
public class ConnectTest {
    @Autowired
    private OSS oss;

    @Autowired
    IAcsClient client;

    @Test
    public void connect(){
        String bucketName = "examplesbucket";
        ObjectListing objectListing = oss.listObjects(bucketName);
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " +
                    "(size = " + objectSummary.getSize() + ")");
        }

    }

    @Test
    public void sts(){
        AssumeRoleRequest request = new AssumeRoleRequest();

        request.setRoleArn("acs:ram::1482221404522785:role/oss");
        request.setRoleSessionName("tongtu");

        //发起请求，并得到响应。
        try {
            AssumeRoleResponse response = client.getAcsResponse(request);

            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }

    @Test
    public void ossCallback(){
        PutObjectRequest putObjectRequest = new PutObjectRequest("examplesbucket","examplesbucket/误差求根.pdf",new File("F:\\QQDownload\\误差求根.pdf"));
        Callback callback = new Callback();
        callback.setCallbackUrl("http://api.tongtu.xyz/oss/callback");
        callback.setCallbackHost("oss-cn-beijing.aliyuncs.com");
        callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);
        callback.setCallbackBody("{\"mimeType\":\"text\",\"size\":1024}");

        putObjectRequest.setCallback(callback);

        oss.putObject(putObjectRequest);


    }
}
