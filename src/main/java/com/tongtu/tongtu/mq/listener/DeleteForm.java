package com.tongtu.tongtu.mq.listener;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;


@AllArgsConstructor
@Data
public class DeleteForm {
    @NotNull
    private final String bucket;
    @NotNull
    private final String object;
}
