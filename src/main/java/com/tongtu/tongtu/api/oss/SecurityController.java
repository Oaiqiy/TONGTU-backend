package com.tongtu.tongtu.api.oss;


import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.oss.OssUtils;
import com.tongtu.tongtu.security.jwt.TokenProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/oss")
@AllArgsConstructor
public class SecurityController {

    private OssUtils ossUtils;
    private TokenProcessor tokenProcessor;

    @GetMapping("/sts/{token}")
    public Map<String,String> securityToken(@PathVariable String token){
        Map<String,String> result = new HashMap<>();

        try{
            if(tokenProcessor.decodeToken(token)==null){
               throw new Exception();
            }
            AssumeRoleResponse.Credentials credentials = ossUtils.getOssToken().getCredentials();
            result.put("StatusCode","200");
            result.put("AccessKeyId",credentials.getAccessKeyId());
            result.put("AccessKeySecret",credentials.getAccessKeySecret());
            result.put("SecurityToken",credentials.getSecurityToken());
            result.put("Expiration",credentials.getExpiration());

            return result;

        }catch (Exception e){
            result.clear();
            result.put("StatusCode","500");
            result.put("ErrorCode","InvalidAccessKeyId.NotFound");
            result.put("ErrorMessage","Specified access key is not found.");
            return  result;
        }

    }
}
