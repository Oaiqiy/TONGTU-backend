package com.tongtu.tongtu.api.oss;

import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.domain.FileInfo;
import com.tongtu.tongtu.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oss/callback")
@Slf4j
@AllArgsConstructor
public class CallbackController {
    private UserRepository userRepository;

    @PostMapping
    public ResultInfo<Map<String,String>> ossCallback(@RequestBody CallbackForm callbackForm){

        Map<String,String> test= new HashMap<>();


        User user= userRepository.findUserById(callbackForm.getId());
        user.uploadFile(callbackForm.getSize(), FileInfo.FileType.OTHER);
        userRepository.save(user);

        test.put("usedStorage",user.getUsedStorage().toString());
        return new ResultInfo<>(0, "success", test);
    }
}
