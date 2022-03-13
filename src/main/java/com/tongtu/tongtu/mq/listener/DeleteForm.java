package com.tongtu.tongtu.mq.listener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteForm {
    @NotNull
    private String bucket;
    @NotNull
    private String object;
}
