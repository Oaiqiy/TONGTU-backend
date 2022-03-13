package com.tongtu.tongtu.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.domain.DeletedFile;
import com.tongtu.tongtu.domain.Device;
import com.tongtu.tongtu.domain.FileInfo;
import com.tongtu.tongtu.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class RepositoryTest{
    @Autowired
    UserRepository userRepository;
    @Autowired
    FileInfoRepository fileInfoRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    DeletedFileRepository deletedFileRepository;


    @Test
    public void repoTest() throws Exception{
        User user = new User("123","324","324@fdsa.com");
        User saved = userRepository.save(user);

        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(objectMapper.writeValueAsString(new ResultInfo<String>(0, "asdf", "fasfda")));

        userRepository.delete(saved);
    }

    @Test
    public void deviceTest() throws Exception{
        Device device = new Device();
        device.setName("huawei p30");
        device.setUser(new User(1L));
        deviceRepository.save(device);
    }

    @Test
    public void fileTest() throws Exception{

//        FileInfo fileInfo = fileInfoRepository.findFileInfosById(1L);
//        DeletedFile deletedFile = new DeletedFile();
//        deletedFile.setId(1L);
//        fileInfo.setDeletedFile(deletedFile);
//
//
//        fileInfoRepository.save(fileInfo);
//        System.out.println(fileInfoRepository.findFileInfosByUser_Id(1L).size());
//        fileInfoRepository.deleteFileInfosByDeletedFile_CreatedAtLessThan(new Date());
//        System.out.println(fileInfoRepository.findFileInfosByUser_Id(1L).size());
    }


}
