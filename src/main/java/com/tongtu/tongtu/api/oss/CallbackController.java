package com.tongtu.tongtu.api.oss;

import com.tongtu.tongtu.api.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oss/callback")
@Slf4j
public class CallbackController {


    @PostMapping
    public ResultInfo<Map<String,String>> ossCallback(@RequestBody CallbackForm callbackForm){

        Map<String,String> test= new HashMap<>();
        callbackForm.getId();

        test.put("last","还没写出来");


        return new ResultInfo<Map<String,String>>(0,"success",test);
    }
}
