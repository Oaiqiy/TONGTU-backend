package com.tongtu.tongtu.data;

import com.tongtu.tongtu.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Test
    public void repoTest(){
        User user = new User();
        user.setUsername("adsfsa");
        userRepository.save(user);
    }
}