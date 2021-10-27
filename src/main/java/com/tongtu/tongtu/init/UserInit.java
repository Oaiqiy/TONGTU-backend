package com.tongtu.tongtu.init;



import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class UserInit {
    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                User mhl = new User("mhl",passwordEncoder.encode("mhl"),"mhl@tongtu.xyz");
                mhl.setVerified(true);
                userRepository.save(mhl);
//                mhl=userRepository.findUserByUsername("mhl");
//
//                userRepository.save(mhl);

                User horace = new User("horace",passwordEncoder.encode("123456"),"1070236799@qq.com");
                userRepository.save(horace);
                horace=userRepository.findUserByUsername("horace");
                horace.setVerified(true);
                userRepository.save(horace);



            }
        };
    }

//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.yeah.net");
//        mailSender.setPort(587);
//
//        mailSender.setUsername("tong_tu@yeah.net");
//        mailSender.setPassword("QIRAYKDHVKVLDIZP");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }


//    @Bean
//    public CommandLineRunner sendEmaLineRunner(JavaMailSender javaMailSender) {
//        return new CommandLineRunner(){
//            @Override
//            public void run(String... args)  {
//
//                log.info("init mail");
//
//                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//
//                simpleMailMessage.setFrom("TongTu <tong_tu@outlook.com>");
//                simpleMailMessage.setTo("1320371940@qq.com");
//                simpleMailMessage.setSubject("通途验证码");
//                simpleMailMessage.setText("<html>666666</html>");
//                simpleMailMessage.setCc("tong_tu@outlook.com");
//
//                MimeMessage message = javaMailSender.createMimeMessage();
//                MimeMessageHelper messageHelper = new MimeMessageHelper(message);
//
//
//
//
//
//                try {
//                    log.info("prepare");
//                    messageHelper.setFrom("TongTu <tong_tu@outlook.com>");
//                    messageHelper.setTo("1320371940@qq.com");
//                    messageHelper.setSubject("通途验证码");
//                    messageHelper.setText("<a href=\"https://blog.csdn.net/dayonglove2018/article/details/106784064\">老周的博客</a>",true);
//                    messageHelper.setCc("tong_tu@outlook.com");
//                    javaMailSender.send(messageHelper.getMimeMessage());
//
//                }catch (Exception e){
//                    log.info("exception");
//                    e.printStackTrace();
//
//                }
//
//
//                log.info("send mail");
//
//            }
//        };
//    }
}
