package com.tongtu.tongtu.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RepositoryTest{
    @Autowired
    UserRepository userRepository;
    @Test
    public void repoTest() throws Exception{
        User user = new User("123","324","324@fdsa.com");
        userRepository.save(user);

        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(objectMapper.writeValueAsString(new ResultInfo<String>(0, "asdf", "fasfda")));
    }
}
