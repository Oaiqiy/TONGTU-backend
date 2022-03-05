package com.tongtu.tongtu.api.oss;

import com.tongtu.tongtu.domain.Device;
import com.tongtu.tongtu.domain.FileInfo;
import com.tongtu.tongtu.domain.User;
import com.tongtu.tongtu.mq.listener.DeleteForm;
import com.tongtu.tongtu.mq.listener.OSSDeleteListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.tomcat.util.security.MD5Encoder;

import java.nio.charset.StandardCharsets;

/**
 * callback form
 */
@Data
@AllArgsConstructor
public class CallbackForm {
    /** target device */
    private Long target;

    /** file object */
    private String object;

    /** {@link com.tongtu.tongtu.domain.FileInfo.FileType}*/
    private int type;

    private Long size;

    /**
     * client send token and be converted to username in {@link com.tongtu.tongtu.api.oss.CallbackController#ossCallback(CallbackForm)}
     */
    private String auth;

    /** device id*/
    private Long device;

    private String description;

    /** file's MD5*/
    private String MD5;


    public FileInfo toFileInfo(){

        String[] folderAndName = object.split("/");


        if(folderAndName.length==1)
            return new FileInfo(folderAndName[0],null,size, FileInfo.FileType.values()[type],new User(auth),new Device(device),description);
        else
            return new FileInfo(folderAndName[1],folderAndName[0],size,FileInfo.FileType.values()[type],new User(auth),new Device(device),description);
    }

    public DeleteForm toDeleteForm(){
        return new DeleteForm(MD5Encoder.encode(auth.getBytes(StandardCharsets.UTF_8)),object);
    }


}
