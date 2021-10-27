package com.tongtu.tongtu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bucket;
    private String object;
    private Long size;
    private FileType fileType;
    @ManyToOne
    private User user;



    public enum FileType{
        IMAGE,VIDEO,AUDIO,TEXT,OTHER
    }


}
