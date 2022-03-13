package com.tongtu.tongtu.security.jwt;

import com.tongtu.tongtu.data.DeviceRepository;
import com.tongtu.tongtu.data.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * 用户认证filter
 */

@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

    private TokenProcessor tokenProcessor;
    private UserRepository userRepository;
    public AuthorizationFilter(TokenProcessor tokenProcessor, AuthenticationManager authenticationManager,UserRepository userRepository){
        super(authenticationManager);
        this.tokenProcessor=tokenProcessor;
        this.userRepository = userRepository;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        /**
         * 从请求的header中获取token
         */
        String token = httpServletRequest.getHeader("token");

        UsernamePasswordAuthenticationToken auth;

        String username = null;

        if(token!=null){

            username = tokenProcessor.decodeToken(token);
            if(username!=null){
                auth = new UsernamePasswordAuthenticationToken(username,token, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

            }
            else {
                auth = new UsernamePasswordAuthenticationToken(null,null,null);
            }
        }
        else {
            auth =new UsernamePasswordAuthenticationToken(null,null,null);
        }

        auth.setDetails(userRepository.findUserByUsername(username));

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
