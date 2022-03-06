package com.tongtu.tongtu.mq.listener;

import com.tongtu.tongtu.api.user.RegisterForm;
import com.tongtu.tongtu.security.VerificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
@AllArgsConstructor
public class UserListener {
    private VerificationEmail verificationEmail;

    @RabbitListener(queues = "register")
    public void sendRegisterMail(RegisterForm registerForm){
        try {
            log.info("register: username: " + registerForm.getUsername() + " email: " + registerForm.getEmail());

            //System.out.println(MvcUriComponentsBuilder.fromMappingName("/user/check").arg(0, "ssda").build());
            UriComponentsBuilder.fromUriString(registerForm.getUri()).path("/user/check");
            verificationEmail.registerMail(registerForm.getUsername(), registerForm.getEmail(),UriComponentsBuilder.fromUriString(registerForm.getUri()).path("/user/check"));
           // String root = MvcUriComponentsBuilder.fromMethodName(MailController.class,"mailCheck","324").toUriString();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
