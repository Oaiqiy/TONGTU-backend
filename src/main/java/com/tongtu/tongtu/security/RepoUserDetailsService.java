package com.tongtu.tongtu.security;

import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RepoUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    RepoUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if(user!=null){
            return user;
        }
        throw new UsernameNotFoundException("'" + username + "' not found");

    }
}
