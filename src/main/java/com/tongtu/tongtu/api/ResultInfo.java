package com.tongtu.tongtu.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultInfo<T> {
    private Integer code;
    private String msg;
    private T data;
}
