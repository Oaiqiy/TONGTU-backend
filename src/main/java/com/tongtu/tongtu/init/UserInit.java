package com.tongtu.tongtu.init;

import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserInit {
    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                User mhl = new User("mhl",passwordEncoder.encode("mhl"),"mhl@tongtu.xyz");
                userRepository.save(mhl);
                mhl=userRepository.findUserByUsername("mhl");
                mhl.setVerified(true);

                userRepository.save(mhl);

            }
        };
    }
}
