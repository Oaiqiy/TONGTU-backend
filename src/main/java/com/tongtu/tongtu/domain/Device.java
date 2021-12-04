package com.tongtu.tongtu.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
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
    private String uuid;
    private String name;
    private String type;

    @ManyToOne(cascade = CascadeType.DETACH)
    private User user;


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


}
