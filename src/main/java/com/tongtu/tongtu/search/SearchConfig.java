package com.tongtu.tongtu.search;

import com.tongtu.tongtu.domain.FileInfo;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Component
public class SearchConfig {

    @Bean
    public SearchSession searchSession(EntityManagerFactory entityManagerFactory){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return Search.session(entityManager);
    }

    @Bean
    public MassIndexer massIndexer(EntityManagerFactory entityManagerFactory){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        SearchSession searchSession = Search.session(entityManager);
        return searchSession.massIndexer(FileInfo.class).threadsToLoadObjects(4);
    }

}
