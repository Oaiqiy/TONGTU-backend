package com.tongtu.tongtu.security.jwt;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.oss.OssUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录验证的类，接收json格式的数据并验证登录，如果成功返回token
 */

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private TokenProcessor tokenProcessor;
    private OssUtils ossUtils;




    public AuthenticationFilter(TokenProcessor tokenProcessor, OssUtils ossUtils) {

        this.tokenProcessor = tokenProcessor;
        this.ossUtils = ossUtils;
        this.setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        if (request.getContentType() != null) {
            if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
                
                ObjectMapper mapper = new ObjectMapper();
                UsernamePasswordAuthenticationToken authRequest = null;
                try (InputStream is = request.getInputStream()) {
                    Map<String, String> authenticationBean = mapper.readValue(is, Map.class);

                    authRequest = new UsernamePasswordAuthenticationToken(authenticationBean.get("username"),
                            authenticationBean.get("password"));
                    
                } catch (IOException e) {
                    e.printStackTrace();
                    authRequest = new UsernamePasswordAuthenticationToken("", "");
                    return this.getAuthenticationManager().authenticate(authRequest);
                }
                
                try{
                    return this.getAuthenticationManager().authenticate(authRequest);
                }catch(DisabledException e){

        
                    response.setHeader("error", "2");
                   

                    throw new DisabledException("");
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





        //发起请求，并得到响应。
        AssumeRoleResponse assumeRoleResponse;
        try {
            assumeRoleResponse = ossUtils.getOssToken();

        } catch (Exception e){
            response.getWriter().append("{\"code\":3,\"msg\":\"cannot access oss\"}");
            return;
        }


        Map<String,Object> data = new HashMap<>();

        data.put("token",tokenProcessor.createToken(authResult.getName()));
        data.put("sts",assumeRoleResponse.getCredentials());


        ResultInfo<Map> resultInfo = new ResultInfo<Map>(0,"login success");
        resultInfo.setData(data);
        ObjectMapper om = new ObjectMapper();

        response.getWriter().append(om.writeValueAsString(resultInfo));

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

            
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        
        if (response.getHeader("error")!= null) {
           response.getWriter().append("{\"code\":2,\"msg\":\"Email is not enabled\"}");



        }
        else   
            response.getWriter().append("{\"code\":1,\"msg\":\"login failure!\"}");

        
        
    }

}
