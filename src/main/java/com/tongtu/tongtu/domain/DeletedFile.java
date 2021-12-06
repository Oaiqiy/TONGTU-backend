package com.tongtu.tongtu.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor(force = true)
public class DeletedFile{

    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "deletedFile")
    private FileInfo fileInfo;
    private Date createdAt;

    @ManyToOne
    private Device deletingDevice;


    @PrePersist
    public void createdAt(){
        createdAt=new Date();
    }
}
