package com.tongtu.tongtu.api.user;


import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.security.VerificationEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class RegisterController {



    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationEmail verificationEmail;
    RegisterController(UserRepository userRepository,PasswordEncoder passwordEncoder,VerificationEmail verificationEmail){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.verificationEmail=verificationEmail;

    }

    @PostMapping("/register")
    public ResultInfo<String> register(@RequestBody RegisterForm registerForm){
        userRepository.save(registerForm.toUser(passwordEncoder));
        try {
            verificationEmail.registerMail(registerForm.getUsername(),registerForm.getEmail());
        }catch (Exception e){
            return new ResultInfo<>(1,"邮件发送失败！");
        }

        return new ResultInfo<>(0,"提交成功,等待用户验证。");
    }

    @GetMapping("/check/username")
    public ResultInfo<String> checkName(String username){
        if(userRepository.existsByUsername(username))
            return new ResultInfo<>(1,"用户名已存在。");
        else
            return new ResultInfo<>(0,"用户名可以使用。");
    }

    @GetMapping("/check/email")
    public ResultInfo<String> checkEmail(String email){
        if(userRepository.existsByEmail(email))
            return new ResultInfo<>(1,"邮箱已被使用。");
        else
            return new ResultInfo<>(0,"邮箱可以使用");
    }



}

