package com.tongtu.tongtu.oss;


import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import org.springframework.stereotype.Component;


@Component
public class OssUtils {
    private final IAcsClient iAcsClient;

    OssUtils( IAcsClient iAcsClient){

        this.iAcsClient=iAcsClient;
    }

    public AssumeRoleResponse getOssToken() throws Exception{

        AssumeRoleRequest assumeRoleRequest = new AssumeRoleRequest();
        assumeRoleRequest.setRoleArn("acs:ram::1482221404522785:role/tongtu");
        assumeRoleRequest.setRoleSessionName("tongtu");
        assumeRoleRequest.setDurationSeconds( 43200L );

        return iAcsClient.getAcsResponse(assumeRoleRequest);

    }

}
