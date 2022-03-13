package com.tongtu.tongtu.exception;

import com.tongtu.tongtu.api.ResultInfo;

public class CallbackException extends Exception{
    private Integer code;
    public CallbackException(Integer code, String message){
        super(message);
        this.code = code;
    }

    public ResultInfo<String> toResultInfo(){
        return new ResultInfo<>(code,getMessage());
    }
}
