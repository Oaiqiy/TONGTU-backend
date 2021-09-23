package com.tongtu.tongtu.api.user;

import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.domain.User;
import com.tongtu.tongtu.security.jwt.TokenProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class MailController {
    private final TokenProcessor tokenProcessor;
    private final UserRepository userRepository;
    MailController(TokenProcessor tokenProcessor,UserRepository userRepository){
        this.tokenProcessor=tokenProcessor;
        this.userRepository=userRepository;
    }
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
        return "验证成功";
    }
}
