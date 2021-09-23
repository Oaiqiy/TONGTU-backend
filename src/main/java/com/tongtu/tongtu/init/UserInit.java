package com.tongtu.tongtu.init;

import java.util.Properties;

import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserInit {
    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                User mhl = new User("mhl",passwordEncoder.encode("mhl"),"mhl@tongtu.xyz");
                userRepository.save(mhl);
                mhl=userRepository.findUserByUsername("mhl");
                mhl.setVerified(true);

                userRepository.save(mhl);

            }
        };
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.yeah.net");
        mailSender.setPort(587);

        mailSender.setUsername("tong_tu@yeah.net");
        mailSender.setPassword("QIRAYKDHVKVLDIZP");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        
        return mailSender;
    }


    @Bean
    public CommandLineRunner sendEmaLineRunner(JavaMailSender javaMailSender) {
        return new CommandLineRunner(){
            @Override
            public void run(String... args) throws Exception {
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setFrom("tong_tu@yeah.net");
                simpleMailMessage.setTo("1320371940@qq.com");
                simpleMailMessage.setSubject("test");
                simpleMailMessage.setText("666");
                javaMailSender.send(simpleMailMessage);

            }
        };
    }
}
