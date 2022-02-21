package com.tongtu.tongtu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor(force = true)
@Table(name = "file_info")
public class FileInfo implements Serializable {
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

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private DeletedFile deletedFile;


    @ManyToOne(targetEntity = User.class)
    @JsonIgnore
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Device device;

    @PrePersist
    public void createdAt(){
        uploadAt = new Date();
    }

    public FileInfo(String name,String folder, Long size, FileType fileType, User user, Device device,String description) {
        this.name = name;
        this.folder = folder;
        this.size = size;
        this.fileType = fileType;
        this.user = user;
        this.device = device;
        this.description = description;
    }
    public FileInfo(Long id){
        this.id = id;
    }

    public enum FileType{
        IMAGE,VIDEO,AUDIO,TEXT,OTHER
    }




}
