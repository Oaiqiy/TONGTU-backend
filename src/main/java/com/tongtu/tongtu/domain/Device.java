package com.tongtu.tongtu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @Column(unique = true)
    private String uuid;

    private String name;
    private String alias;


    private String type;

    private String registrationId;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JsonIgnore
    private User user;
    private Date lastLoginAt;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Device device = (Device) o;
        return Objects.equals(id, device.id);
    }

    @Override
    public int hashCode() {
        return 1273429004;
    }

    public Device(Long id){
        this.id = id;
    }

    public Device(String uuid,String name,String type,User user,String registrationId){
        this.uuid = uuid;
        this.name = name;
        this.type = type;
        this.user = user;
        this.registrationId = registrationId;
    }




}
