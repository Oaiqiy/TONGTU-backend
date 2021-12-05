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
    private String name;
    private Date uploadAt;
    private FileType fileType;
    private Long size;
    private String folder;
    private String description;
    private Boolean deleted = false;

    @OneToOne
    @PrimaryKeyJoinColumn
    private DeletedFile deletedFile;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Device device;

    @PrePersist
    public void createdAt(){
        uploadAt = new Date();
    }

    public FileInfo(String folder, Long size, FileType fileType, User user, Device device,String description) {
        this.folder = folder;
        this.size = size;
        this.fileType = fileType;
        this.user = user;
        this.device = device;
        this.description = description;
    }



    public enum FileType{
        IMAGE,VIDEO,AUDIO,TEXT,OTHER
    }




}
