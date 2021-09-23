package com.tongtu.tongtu.security.jwt;

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
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private TokenProcessor tokenProcessor;

    public AuthenticationFilter(TokenProcessor tokenProcessor) {

        this.tokenProcessor = tokenProcessor;
        this.setAuthenticationFailureHandler((httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setStatus(403);
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().append("{\"code\":1,\"msg\":\"login failure!\"}");
        });
        this.setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        if (request.getContentType() != null) {
            if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
                log.info("asdfas");
                ObjectMapper mapper = new ObjectMapper();
                UsernamePasswordAuthenticationToken authRequest = null;
                try (InputStream is = request.getInputStream()) {
                    Map<String, String> authenticationBean = mapper.readValue(is, Map.class);

                    authRequest = new UsernamePasswordAuthenticationToken(authenticationBean.get("username"),
                            authenticationBean.get("password"));
                } catch (IOException e) {
                    e.printStackTrace();
                    authRequest = new UsernamePasswordAuthenticationToken("", "");
                } finally {
                    setDetails(request, authRequest);
                    return this.getAuthenticationManager().authenticate(authRequest);
                }
            } else {
                return super.attemptAuthentication(request, response);
            }
        }

        else
            return super.attemptAuthentication(request, response);
    }

    /**
     * 登录成功后的操作
     */

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append("{\"code\":0,\"msg\":\"login success\",\"token\":\""
                + tokenProcessor.createToken(authResult.getName()) + "\"}");
    }

}
