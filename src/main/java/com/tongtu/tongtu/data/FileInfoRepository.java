package com.tongtu.tongtu.data;

import com.tongtu.tongtu.domain.FileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface FileInfoRepository extends CrudRepository<FileInfo,Long> {

    List<FileInfo> findFileInfosByUser_Id(Long user_id);
    List<FileInfo> findFileInfosByUser_IdAndDeleted(Long id, Boolean deleted);
    FileInfo findFileInfosById(Long id);
    FileInfo findFileInfoByIdAndUser_Id(Long id,Long userId);
    @Transactional
    void deleteFileInfoById(Long fileId);
    Page<FileInfo> findFileInfosByUser_IdAndDeleted(Long id, Boolean deleted,Pageable pageable);
    Page<FileInfo> findFileInfosByUser_IdAndDeletedAndFolder(Long id,Boolean deleted,String folder,Pageable pageable);
    @Transactional
    void deleteFileInfosByUser_IdAndDeleted(Long id,Boolean deleted);

    @Modifying
    @Transactional
    @Query(value = "update FileInfo set device.id = ?1 where device.id=?2")
    void updateFileInfoDevice(Long newDevice, Long oldDevice);

    @Modifying
    @Transactional
    @Query(value = "update FileInfo set deleted = 1 where device.id = ?1")
    void updateFileInfoDeleted(Long id);

    @Modifying
    @Transactional
    @Query(value = "update FileInfo set deleted = ?3 where id = ?1 and user.id = ?2")
    void updateFileInfoDeletedByFileInfoIdAndUserId(Long fileId,Long userId,Boolean deleted);



}
