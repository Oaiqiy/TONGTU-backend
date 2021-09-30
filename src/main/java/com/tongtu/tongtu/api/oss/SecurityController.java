package com.tongtu.tongtu.api.oss;


import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.oss.OssUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/oss")
public class SecurityController {

    private OssUtils ossUtils;

    SecurityController(OssUtils ossUtils){

        this.ossUtils = ossUtils;
    }

    @GetMapping("/sts")
    public ResultInfo<AssumeRoleResponse.Credentials> securityToken(){
        try{
            return new ResultInfo<>(0,"success", ossUtils.getOssToken().getCredentials());
        }catch (Exception e){
            return new ResultInfo<>(1,"failure");
        }

    }
}
