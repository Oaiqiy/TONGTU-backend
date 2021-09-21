package com.tongtu.tongtu.security.jwt;

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
 * 验证token的filter，验证用户的token并给请求赋予权限
 */

@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

    TokenProcessor tokenProcessor;
    public AuthorizationFilter(TokenProcessor tokenProcessor, AuthenticationManager authenticationManager){
        super(authenticationManager);
        this.tokenProcessor=tokenProcessor;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        /**
         * 从请求的header中获取token
         */
        String token = httpServletRequest.getHeader("token");

        UsernamePasswordAuthenticationToken auth;

        if(token!=null){

            String username = tokenProcessor.decodeToken(token);
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


        SecurityContextHolder.getContext().setAuthentication(auth);



        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
