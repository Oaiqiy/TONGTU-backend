package com.tongtu.tongtu.api.user;

import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.data.DeviceRepository;
import com.tongtu.tongtu.domain.User;
import com.tongtu.tongtu.security.jwt.TokenProcessor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
   private TokenProcessor tokenProcessor;
   private DeviceRepository deviceRepository;

    /**
     * 重新获取token，并更新设备信息,当设备已经被删除时，不再获取token
     * @param id 设备id
     * @return 成功返回token，失败返回错误信息
     */
    @GetMapping("/token/{id}")
    public ResultInfo<String> refreshToken(@PathVariable Long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        if(deviceRepository.findDeviceByIdAndUser_Id(id,user.getId())==null){
            return new ResultInfo<>(1,"deleted device","Please login again");
        }
        deviceRepository.updateLastLoginAt(id,new Date());
        return new ResultInfo<>(0,"success",tokenProcessor.createToken(user.getUsername()));
    }


    /**
     * 获取用户信息
     * @return 返回用户信息
     */
    @GetMapping
    public ResultInfo<User> userInfo(){
        return new ResultInfo<>(0,"success",(User) SecurityContextHolder.getContext().getAuthentication().getDetails());
    }

}
