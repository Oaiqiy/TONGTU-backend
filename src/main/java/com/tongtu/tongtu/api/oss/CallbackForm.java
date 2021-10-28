package com.tongtu.tongtu.api.oss;

import com.tongtu.tongtu.domain.Device;
import com.tongtu.tongtu.domain.FileInfo;
import com.tongtu.tongtu.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CallbackForm {

    private String mimeType;
    private Long size;
    private Long user;
    private Long device;
    private String bucket;
    private String object;

    public FileInfo toFileInfo(){
        //TODO: get file type

        return new FileInfo(bucket,object,size, FileInfo.FileType.OTHER,new User(user),new Device(device));

    }


}
