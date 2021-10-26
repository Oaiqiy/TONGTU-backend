package com.tongtu.tongtu.api.oss;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CallbackForm {

    private String mimeType;
    private Long size;
    private Long id;
    private String bucket;
    private String object;


}
