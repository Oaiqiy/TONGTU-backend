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
    private Long target;
    private String object;
    private int type;
    private Long size;
    private Long user;
    private Long device;
    private String description;
    private String MD5;


    public FileInfo toFileInfo(){
        String[] folderAndName = object.split("/");
        if(folderAndName.length==1)
            return new FileInfo(folderAndName[0],null,size, FileInfo.FileType.values()[type],new User(user),new Device(device),description);
        else
            return new FileInfo(folderAndName[1],folderAndName[0],size,FileInfo.FileType.values()[type],new User(user),new Device(device),description);
    }



}
