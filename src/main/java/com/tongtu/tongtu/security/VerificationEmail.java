package com.tongtu.tongtu.security;

import com.tongtu.tongtu.api.user.MailController;
import com.tongtu.tongtu.security.jwt.TokenProcessor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.mail.internet.MimeMessage;

@Component
public class VerificationEmail {
    private final JavaMailSender javaMailSender;
    private final TokenProcessor tokenProcessor;
    VerificationEmail(JavaMailSender javaMailSender,TokenProcessor tokenProcessor){
        this.javaMailSender = javaMailSender;
        this.tokenProcessor = tokenProcessor;
    }
    public void registerMail(String username,String email) throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("TongTu <tong_tu@outlook.com>");
        messageHelper.setTo(email);
        messageHelper.setSubject("通途注册验证邮件");
        String token = tokenProcessor.createRegisterToken(username);
        String root = MvcUriComponentsBuilder.fromMethodName(MailController.class,"mailCheck",token).toUriString();
        String content = String.format("<a href=\"%s\">验证</a>",root);
        messageHelper.setText(content,true);
        messageHelper.setCc("tong_tu@outlook.com");
        javaMailSender.send(messageHelper.getMimeMessage());
    }
}
