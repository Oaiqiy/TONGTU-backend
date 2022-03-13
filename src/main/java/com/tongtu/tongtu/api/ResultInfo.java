package com.tongtu.tongtu.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class ResultInfo<T> {
    private final Integer code;
    private final  String msg;
    private T data;
}
