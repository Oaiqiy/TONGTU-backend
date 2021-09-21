package com.tongtu.tongtu.data;

import com.tongtu.tongtu.domain.User;
import org.springframework.data.repository.CrudRepository;
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}