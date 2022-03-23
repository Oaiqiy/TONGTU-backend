package com.tongtu.tongtu.task;



import com.tongtu.tongtu.data.DeviceRepository;
import com.tongtu.tongtu.data.FileInfoRepository;
import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.domain.Device;
import com.tongtu.tongtu.domain.FileInfo;
import com.tongtu.tongtu.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;


/**
 * test file, generate test database
 */

@Slf4j
@Component
public class RepositoryInit {


    @Bean
    @Transactional
    public CommandLineRunner init(MassIndexer indexer,FileInfoRepository fileInfoRepository, UserRepository userRepository, DeviceRepository deviceRepository, PasswordEncoder passwordEncoder){
        return args -> {


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

            Device device = new Device();
            device.setUser(new User(1L));
            device.setName("Redmi K50 8+256G 2k 120w 2599");
            device.setType("phone");
            device.setUuid("mhl222sb");
            device.setAlias("mhl的红米开五菱");
            deviceRepository.save(device);

            device = new Device();
            device.setUser(new User(1L));
            device.setName("IPhone 12");
            device.setType("phone");
            device.setUuid("mhl111sb");
            device.setAlias("西瓜皮");
            deviceRepository.save(device);

            FileInfo fileInfo = new FileInfo("file_three","test",2048L, FileInfo.FileType.IMAGE,mhl,device,"test file");
            fileInfoRepository.save(fileInfo);

            fileInfo = new FileInfo("file-two","test",2048L, FileInfo.FileType.IMAGE,mhl,device,"test file");
            fileInfoRepository.save(fileInfo);

            fileInfo = new FileInfo("a good file","test",2048L, FileInfo.FileType.IMAGE,mhl,device,"test file");
            fileInfoRepository.save(fileInfo);

            fileInfo = new FileInfo("file4","test",2048L, FileInfo.FileType.IMAGE,mhl,device,"test file");
            fileInfoRepository.save(fileInfo);

            fileInfoRepository.save(new FileInfo("一个文件","test",2048L, FileInfo.FileType.IMAGE,mhl,device,"test file"));

            fileInfoRepository.save(new FileInfo("两个文件","test",2048L, FileInfo.FileType.IMAGE,mhl,device,"test file"));
            fileInfoRepository.save(new FileInfo("三个文件","test",2048L, FileInfo.FileType.IMAGE,mhl,device,"test file"));
            fileInfoRepository.save(new FileInfo("四个文件","test",2048L, FileInfo.FileType.IMAGE,mhl,device,"test file"));


            indexer.purgeAllOnStart(true);
            indexer.startAndWait();

//            var em = entityManager.getEntityManagerFactory().createEntityManager();
//            SearchSession searchSession = Search.session(em);
//
//            MassIndexer indexer = searchSession.massIndexer(FileInfo.class).threadsToLoadObjects(4);
//            System.out.println("start");
//            indexer.startAndWait();
//
//            System.out.println("end");
//            fileInfoRepository.save(new FileInfo("五个文件","test",2048L, FileInfo.FileType.IMAGE,mhl,device,"test file"));
//            SearchResult<FileInfo> result = searchSession.search(FileInfo.class)
//                .where(f ->f.match().fields("name").matching("*文件*")).fetch(20);
//
//            for(var x: result.hits()){
//                System.out.println(x.getName());
//            }
//
//            System.out.println("finish");


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
