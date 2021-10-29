package com.tongtu.tongtu.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor(force = true)
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bucket;
    private String object;
    private Long size;
    private FileType fileType;
    private Date createdAt;
    private Boolean deleted = false;

    @ManyToOne(cascade = CascadeType.DETACH,targetEntity = User.class)
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Device device;

    @PrePersist
    public void createdAt(){
        createdAt = new Date();
    }

    public FileInfo(String bucket, String object, Long size, FileType fileType, User user, Device device) {
        this.bucket = bucket;
        this.object = object;
        this.size = size;
        this.fileType = fileType;
        this.user = user;
        this.device = device;
    }



    public enum FileType{
        IMAGE,VIDEO,AUDIO,TEXT,OTHER
    }


}
