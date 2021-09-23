package com.tongtu.tongtu.security;


import com.tongtu.tongtu.security.jwt.AuthenticationFilter;
import com.tongtu.tongtu.security.jwt.AuthorizationFilter;
import com.tongtu.tongtu.security.jwt.TokenProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 此部分用来配置用户细节服务
     */
    private UserDetailsService userDetailsService;
    SecurityConfig(UserDetailsService userDetailsService){
        this.userDetailsService=userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    /**
     * 接下来配置访问鉴权和权限要求
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.antMatchers("/creat/**","/like/**","/dislike/**","/user","/delete/**","/update/**","/comment/**").access("hasRole('ROLE_USER')")
                .antMatchers("/**","/").access("permitAll()")

                .and().formLogin().loginProcessingUrl("/default/login").successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setStatus(200);
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        httpServletResponse.getWriter().append("{\"code\":0,\"msg\":\"登录成功!\",\"data\":\"success\"}");
                    }
                })
                .and().addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilter(authorizationFilter())

                .csrf().disable();
    }


    public AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter filter = new AuthenticationFilter(tokenProcessor());
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }


    public AuthorizationFilter authorizationFilter() throws Exception{
        return new AuthorizationFilter(tokenProcessor(), authenticationManagerBean());
    }




    /**
     * 声明一个password encoder bean
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 声明一个token processor bean
     */

    @Bean
    public TokenProcessor tokenProcessor(){return new TokenProcessor();}
}
