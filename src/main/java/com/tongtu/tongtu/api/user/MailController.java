package com.tongtu.tongtu.api.user;

import com.aliyun.oss.OSS;
import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.domain.User;
import com.tongtu.tongtu.security.jwt.TokenProcessor;
import lombok.AllArgsConstructor;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/user", name = "user")
@AllArgsConstructor
public class MailController {
    private final TokenProcessor tokenProcessor;
    private final UserRepository userRepository;
    private final OSS oss;


    @GetMapping("/check")
    public String mailCheck(String token){
        String username;
        try{
            username = tokenProcessor.decodeRegisterToken(token);
        }catch (Exception e){
            return "验证失败";
        }
        if(username==null)
            return "验证失败";


        User user = userRepository.findUserByUsername(username);
        user.setVerified(true);
        userRepository.save(user);
        oss.createBucket(DigestUtils.md5DigestAsHex(user.getUsername().getBytes(StandardCharsets.UTF_8)));

        return "验证成功";
    }
}
