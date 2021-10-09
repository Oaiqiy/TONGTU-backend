package com.tongtu.tongtu.api.user;

import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserRepository userRepository;


    @GetMapping
    public ResultInfo<User> userInfo(){
        User user =  userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user!=null)
            return new ResultInfo<>(0,"success",user);
        else
            return new ResultInfo<>(1,"failure");
    }

}
