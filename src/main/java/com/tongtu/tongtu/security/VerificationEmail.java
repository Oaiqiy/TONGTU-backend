package com.tongtu.tongtu.security;

import com.tongtu.tongtu.api.user.MailController;
import com.tongtu.tongtu.api.user.RegisterForm;
import com.tongtu.tongtu.security.jwt.TokenProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.internet.MimeMessage;

@Component
@Slf4j
public class VerificationEmail {
    private final JavaMailSender javaMailSender;
    private final TokenProcessor tokenProcessor;
    private final String root;
    VerificationEmail(JavaMailSender javaMailSender,TokenProcessor tokenProcessor){
        this.javaMailSender = javaMailSender;
        this.tokenProcessor = tokenProcessor;
        root =  MvcUriComponentsBuilder.fromMethodName(MailController.class,"mailCheck","").toUriString();
    }
    public void registerMail(String username,String email) throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("TongTu <tong_tu@outlook.com>");
        messageHelper.setTo(email);
        messageHelper.setSubject("通途注册验证邮件");
        String token = tokenProcessor.createRegisterToken(username);
        //String root ="www.baidu.com";
        //String root = MvcUriComponentsBuilder.fromMethodName(MailController.class,"mailCheck",token).toUriString();
        log.info(root+token);
        String content = String.format("<a href=\"%s\">验证</a>",root+token);
        messageHelper.setText(content,true);
        messageHelper.setCc("tong_tu@outlook.com");
        javaMailSender.send(messageHelper.getMimeMessage());
    }

    @RabbitListener(queues = "test")
    public void sendRegisterMail(RegisterForm registerForm){
        log.info("send email");
        try {
            registerMail(registerForm.getUsername(), registerForm.getEmail());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
