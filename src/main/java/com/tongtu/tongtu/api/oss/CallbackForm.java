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

        FileInfo.FileType fileType;

        switch (mimeType.charAt(1)){
            case 'm':
                fileType = FileInfo.FileType.IMAGE;
                break;
            case 'i':
                fileType = FileInfo.FileType.VIDEO;
                break;
            case 'e':
                fileType = FileInfo.FileType.TEXT;
                break;
            case 'u':
                fileType= FileInfo.FileType.AUDIO;
                break;
            case 'p':
                fileType = applicationType();
                break;
            default:
                fileType = FileInfo.FileType.OTHER;

        }


        return new FileInfo(bucket,object,size, fileType,new User(user),new Device(device));

    }

    private FileInfo.FileType applicationType(){
        String type = mimeType.substring(11);
        if(type.contains("ms"))
            return FileInfo.FileType.TEXT;
        else if(type.equals("pdf"))
            return FileInfo.FileType.TEXT;
        else
            return FileInfo.FileType.OTHER;
    }


}
