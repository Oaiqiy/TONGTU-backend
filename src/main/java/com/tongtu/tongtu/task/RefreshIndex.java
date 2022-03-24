package com.tongtu.tongtu.task;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class RefreshIndex {
    private MassIndexer indexer;

    @Scheduled(cron = "0 0 * * * *")
    public void refresh(){

        try {
            indexer.startAndWait();

        } catch (InterruptedException e) {
            log.error("Index refresh failed");
        }
    }


}
