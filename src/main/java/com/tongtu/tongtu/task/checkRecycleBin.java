package com.tongtu.tongtu.task;

import com.tongtu.tongtu.data.FileInfoRepository;
import com.tongtu.tongtu.domain.FileInfo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
@Data
@RequiredArgsConstructor
@ConfigurationProperties("tongtu.recycle")
public class checkRecycleBin {
    private Integer dates = 7;
    private final FileInfoRepository fileInfoRepository;


    @Scheduled(cron = "* 0 * * * *")
    public void deleteExpired(){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR)-dates);
        Date date = calendar.getTime();
        fileInfoRepository.deleteFileInfosByDeletedFile_CreatedAtLessThan(date);


    }
}
