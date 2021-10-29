package com.tongtu.tongtu.data;

import com.tongtu.tongtu.domain.FileInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileInfoRepository extends CrudRepository<FileInfo,Long> {
    List<FileInfo> findFileInfosByUser_Id(Long user_id);
}
