package com.tongtu.tongtu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tongtu.tongtu.api.oss.CallbackController;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.File;
import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
@JsonIgnoreProperties(value = {"verified","enabled","authorities","accountNonLocked","accountNonExpired","credentialsNonExpired"})
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private  String username;

    @JsonIgnore
    private  String password;
    /**
     * valid email when user sign in
     * user can user email to find back password
     */
    @Email
    private  String email;
    private Date createdAt;
    private Boolean verified = false;

    /**
     * user's storage
     */

    private Long maxStorage = 1024 * 1024 * 2L;
    private Long usedStorage = 0L;

    private Long imageStorage = 0L;
    private Long videoStorage = 0L;
    private Long audioStorage = 0L;
    private Long textStorage= 0L;
    private Long otherStorage = 0L;

    private Long recycleStorage = 1024* 1024L;
    private Long usedRecycleStorage = 0L;



    @PrePersist
    void createdAt(){
        createdAt=new Date();
    }

    public void uploadFile(Long size, FileInfo.FileType fileType){
        usedStorage+=size;
        switch (fileType){
            case IMAGE:
                imageStorage+=size;
            case VIDEO:
                videoStorage+=size;
            case AUDIO:
                audioStorage+=size;
            case TEXT:
                textStorage+=size;
            case OTHER:
                otherStorage+=size;
        }
    }

    public void deleteFile(Long size, FileInfo.FileType fileType){
        usedStorage-=size;
        switch (fileType){
            case IMAGE:
                imageStorage-=size;
            case VIDEO:
                videoStorage-=size;
            case AUDIO:
                audioStorage-=size;
            case TEXT:
                textStorage-=size;
            case OTHER:
                otherStorage-=size;
        }
    }

    public void addRecycle(Long size){
        usedRecycleStorage += size;
    }

    public void deleteRecycle(Long size){
        usedRecycleStorage-=size;
    }

    public User(Long id){
        this.id = id;
    }

    public User(String name){this.username = name;}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return verified;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return 562048007;
    }
}