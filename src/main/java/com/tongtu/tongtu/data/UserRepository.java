package com.tongtu.tongtu.data;

import com.tongtu.tongtu.domain.User;
import org.springframework.data.repository.CrudRepository;
public interface UserRepository extends CrudRepository<User, Long> {
    public User findUserByUsername(String username);
}