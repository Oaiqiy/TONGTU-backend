package com.tongtu.tongtu.data;

import com.tongtu.tongtu.domain.FileInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileInfoRepository extends CrudRepository<FileInfo,Long> {
    List<FileInfo> findFileInfosByUser_Id(Long user_id);

    @Query(value = "update FileInfo set device.id = ?1 where device.id=?2")
    void updateFileInfoDevice(Long newDevice, Long oldDevice);
}
