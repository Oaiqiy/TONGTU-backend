package com.tongtu.tongtu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor(force = true)
public class DeletedFile{

    @Id
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "deletedFile")
    private FileInfo fileInfo;
    private Date createdAt;


    @PrePersist
    public void createdAt(){
        createdAt=new Date();
    }
}
