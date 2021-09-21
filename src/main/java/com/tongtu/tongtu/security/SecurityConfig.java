package com.tongtu.tongtu.security;


import com.tongtu.tongtu.security.jwt.AuthenticationFilter;
import com.tongtu.tongtu.security.jwt.TokenProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


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

    }

    @Bean
    AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter filter = new AuthenticationFilter(tokenProcessor());
        filter.setAuthenticationFailureHandler((httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setStatus(403);
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().append("{\"code\":1,\"msg\":\"login failure!\"}");
        });
        filter.setFilterProcessesUrl("/login");
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
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
