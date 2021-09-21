package com.tongtu.tongtu.api.user;


import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class RegisterController {



    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    RegisterController(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @PostMapping("/register")
    public ResultInfo register(@RequestBody RegisterForm registerForm){
        userRepository.save(registerForm.toUser());
        return new ResultInfo(0,"提交成功,等待用户验证。");
    }

    @GetMapping("/check/username")
    public ResultInfo checkName(String username){
        if(userRepository.existsByUsername(username))
            return new ResultInfo(1,"用户名已存在。");
        else
            return new ResultInfo(0,"用户名可以使用。");
    }

    @GetMapping("/check/email")
    public ResultInfo checkEmail(String email){
        if(userRepository.existsByEmail(email))
            return new ResultInfo(1,"邮箱已被使用。");
        else
            return new ResultInfo(0,"邮箱可以使用");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class RegisterForm{
        private String username;
        private String password;
        private String email;
        public User toUser(){
            return new User(username, passwordEncoder.encode(password),email);
        }
    }

}

