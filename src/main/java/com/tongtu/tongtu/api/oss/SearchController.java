package com.tongtu.tongtu.api.oss;

import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.domain.FileInfo;
import com.tongtu.tongtu.domain.User;
import lombok.AllArgsConstructor;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/oss/search")
public class SearchController {
    private final SearchSession searchSession;

    @GetMapping("/file")
    public ResultInfo<List<FileInfo>> searchFileByName(String name){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();

        SearchResult<FileInfo> searchResult = searchSession.search(FileInfo.class)
                .where(f->f.bool()
                        .must(f.match().field("user.id").matching(user.getId()))
                        .must(f.match().field("name").matching('*'+name+'*')))
                .fetch(100);
        List<FileInfo> result = searchResult.hits();

        if(result.isEmpty())
            return new ResultInfo<>(1,"no file matches");
        else
            return new ResultInfo<>(1,"success",result);

    }


}
