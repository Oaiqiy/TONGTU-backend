package com.tongtu.tongtu.data;

import com.tongtu.tongtu.domain.FileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


/**
 * table name file_info
 * native query:
 * {@link #findFoldersByUser_Id}
 */

public interface FileInfoRepository extends CrudRepository<FileInfo,Long> {

    // READ

    /**
     * find file infos by user's id
     * @param user_id user's id
     * @return a list of file infos, if exists
     */
    List<FileInfo> findFileInfosByUser_Id(Long user_id);

    /**
     * find file infos by user's id and if be deleted
     * @param id user's id
     * @param deleted if be deleted
     * @return a list a file infos, if exists
     */
    List<FileInfo> findFileInfosByUser_IdAndDeleted(Long id, Boolean deleted);

    /**
     * find file info by id
     * @param id file info's id
     * @return a file info, if exists
     */
    FileInfo findFileInfoById(Long id);

    /**
     * find file info by id and user's id
     * @param id file info's id
     * @param userId user's id
     * @return a file info, if exists
     */
    FileInfo findFileInfoByIdAndUser_Id(Long id,Long userId);

    /**
     * find file infos by user's id and if deleted
     * @param id user's id
     * @param deleted if deleted
     * @param pageable page
     * @return a page of file infos
     */
    Page<FileInfo> findFileInfosByUser_IdAndDeleted(Long id, Boolean deleted,Pageable pageable);


    /**
     * *nativeQuery*
     * find user's folders by user id
     * @param id user id
     * @return a list of folders' name
     */
    @Query(value = "select distinct file_info.folder from file_info where file_info.user_id = ?1",nativeQuery = true)
    List<String> findFoldersByUser_Id(Long id);



    /**
     * find file infos by user's id and if deleted and folder
     * @param id user's id
     * @param deleted if deleted
     * @param folder file's folder
     * @param pageable page
     * @return a page of file infos
     */
    Page<FileInfo> findFileInfosByUser_IdAndDeletedAndFolder(Long id,Boolean deleted,String folder,Pageable pageable);


    //UPDATE

    /**
     * update file's device
     * @param newDevice new device's id
     * @param oldDevice old device's id
     */
    @Modifying
    @Transactional
    @Query(value = "update FileInfo set device.id = ?1 where device.id=?2")
    void updateFileInfoDevice(Long newDevice, Long oldDevice);

    /**
     * update a device's files' delete status
     * @param id device's id
     */
    @Modifying
    @Transactional
    @Query(value = "update FileInfo set deleted = 1 where device.id = ?1")
    void updateFileInfoDeleted(Long id);

    /**
     * update file's delete status by file' id and user's id
     * @param fileId file's id
     * @param userId user's id
     * @param deleted delete status
     */
    @Modifying
    @Transactional
    @Query(value = "update FileInfo set deleted = ?3 where id = ?1 and user.id = ?2")
    void updateFileInfoDeletedByFileInfoIdAndUserId(Long fileId,Long userId,Boolean deleted);


    //DELETE

    /**
     * delete file infos by the delete time
     * @param date delete time
     */
    @Transactional
    void deleteFileInfosByDeletedFile_CreatedAtLessThan(Date date);

    /**
     * delete file info by file's id
     * @param fileId file's id
     */
    @Transactional
    void deleteFileInfoById(Long fileId);

    /**
     * delete file infos by user's id and if deleted
     * @param id user's id
     * @param deleted if deleted
     */
    @Transactional
    void deleteFileInfosByUser_IdAndDeleted(Long id,Boolean deleted);



}
