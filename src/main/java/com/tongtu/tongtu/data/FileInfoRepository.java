package com.tongtu.tongtu.data;

import com.tongtu.tongtu.domain.FileInfo;
import org.springframework.data.repository.CrudRepository;

public interface FileInfoRepository extends CrudRepository<FileInfo,Long> {
    
}
