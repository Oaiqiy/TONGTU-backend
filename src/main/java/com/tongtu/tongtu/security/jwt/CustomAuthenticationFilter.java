package com.tongtu.tongtu.security.jwt;

/*
 *没有用
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


/**
 * 用户登录验证的类，接收json格式的数据并验证登录，如果成功返回token
 */

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private TokenProcessor tokenProcessor;
    public CustomAuthenticationFilter(TokenProcessor tokenProcessor){
        this.tokenProcessor = tokenProcessor;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        if(request.getContentType()!=null)
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)||request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE) ){

            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = request.getInputStream()) {
                Map<String, String> authenticationBean = mapper.readValue(is, Map.class);
//                log.info(authenticationBean.get("username"));
//                log.info(authenticationBean.get("password"));
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.get("username"), authenticationBean.get("password"));
            } catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");
            } finally {
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        } else {
            return super.attemptAuthentication(request, response);
        }

        return super.attemptAuthentication(request, response);
    }

    /**
     * 登录成功后的动作
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append("{\"code\":0,\"msg\":\"login success\",\"token\":\""+  tokenProcessor.createToken(authResult.getName() )+"\"}");
    }
}
