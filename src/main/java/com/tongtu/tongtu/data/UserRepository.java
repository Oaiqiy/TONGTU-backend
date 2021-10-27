package com.tongtu.tongtu.data;

import com.tongtu.tongtu.domain.Device;
import com.tongtu.tongtu.domain.User;
import org.hibernate.mapping.Collection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserById(Long id);

    User findUserByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
