package com.tongtu.tongtu.api.oss;

import com.tongtu.tongtu.domain.Device;
import com.tongtu.tongtu.domain.FileInfo;
import com.tongtu.tongtu.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * callback form
 */
@Data
@AllArgsConstructor
public class CallbackForm {

    private int type;
    private Long size;
    private Long user;
    private Long device;
    private String folder;
    private String description;
    private String MD5;


    public FileInfo toFileInfo(){
        return new FileInfo(folder,size, FileInfo.FileType.values()[type],new User(user),new Device(device),description);
    }



}
