package com.tongtu.tongtu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
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



    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }


    @PrePersist
    void createdAt(){
        createdAt=new Date();
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