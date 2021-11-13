package com.tongtu.tongtu.api.oss;

import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.data.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/oss")
public class UploadController {
    private final UserRepository userRepository;

    @GetMapping("/pre")
    public ResultInfo<Map<String,String>> preUpload(Long size){
        return null;
    }
}
