package com.tongtu.tongtu.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String name;
    private String alias;

    @ManyToOne(cascade = CascadeType.ALL)
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
