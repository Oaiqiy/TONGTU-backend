package com.tongtu.tongtu.api.oss;

import com.aliyun.oss.OSS;
import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.data.DeletedFileRepository;
import com.tongtu.tongtu.data.FileInfoRepository;
import com.tongtu.tongtu.data.UserRepository;
import com.tongtu.tongtu.domain.DeletedFile;
import com.tongtu.tongtu.domain.Device;
import com.tongtu.tongtu.domain.FileInfo;
import com.tongtu.tongtu.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/oss/file")
@AllArgsConstructor
public class FileController {
    private FileInfoRepository fileInfoRepository;
    private DeletedFileRepository deletedFileRepository;
    private UserRepository userRepository;
    private OSS oss;

    /**
     * 获取用户分组信息 (token)
     * @return if user has folders, return a list of folders <br>
     *         else return code 1 msg not have folders
     */

    @GetMapping("folders")
    public ResultInfo<List<String>> getFolders(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();

        List<String> result = fileInfoRepository.findFoldersByUser_Id(user.getId());

        if(result.isEmpty()){
            return new ResultInfo<>(1,"not have folders");
        }else{
            return new ResultInfo<>(0,Integer.toString(result.size()),result);
        }

    }

    /**
     * 分页获取用户文件信息 (token)
     * @param size page size
     * @param page page num from zero
     * @return if request successfully, return file counts and file infos.<br>
     *         if parameters are invalid ,return "no files"
     */

    @GetMapping("list")
    public ResultInfo<List<FileInfo>> getFiles(Integer size, Integer page) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Pageable pageable = PageRequest.of(page,size);
        Page<FileInfo> files = fileInfoRepository.findFileInfosByUser_IdAndDeleted(user.getId(),false,pageable);
        if(files.isEmpty()){
            return new ResultInfo<>(1,"no files");
        }else {
            return new ResultInfo<>(0,Integer.toString(files.getNumberOfElements()),files.toList());
        }
    }

    /**
     * 分页，分组获取文件信息 (token)
     * @param size page size
     * @param page page num from 0
     * @param folder folder name
     * @return if request successfully, return file counts and file infos.<br>
     *         else return 1 "no files"
     */

    @GetMapping("list/folder")
    public ResultInfo<List<FileInfo>> getFilesInFolder(Integer size,Integer page,String folder){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Pageable pageable = PageRequest.of(page,size);
        Page<FileInfo> files = fileInfoRepository.findFileInfosByUser_IdAndDeletedAndFolder(user.getId(),false,folder,pageable);
        if(files.isEmpty()){
            return new ResultInfo<>(1,"no files");
        }else {
            return new ResultInfo<>(0,Integer.toString(files.getNumberOfElements()),files.toList());
        }
    }

    /**
     * 删除文件 (token)
     * @param device_id device id
     * @param file_id file id
     * @return if recycle bin is full return code 1 <br>
     *         else return 0
     */

    @PostMapping("delete/{device_id}/{file_id}")
    public ResultInfo<String> deleteFile(@PathVariable Long device_id,@PathVariable Long file_id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        FileInfo fileInfo = fileInfoRepository.findFileInfoByIdAndUser_Id(file_id,user.getId());
        if(fileInfo == null){
            return new ResultInfo<>(1,"no such file");
        }
        if(user.getUsedRecycleStorage()+fileInfo.getSize()> user.getRecycleStorage()){
            return new ResultInfo<>(2,"recycle bin is full");
        }
//        user.addRecycle(fileInfo.getSize());
//        user.deleteFile(fileInfo.getSize(),fileInfo.getFileType());
//        userRepository.save(user);

        userRepository.updateUsedRecycleStorage(fileInfo.getSize(), user.getId());
        userRepository.updateUsedStorage(-fileInfo.getSize(), user.getId());

        fileInfoRepository.updateFileInfoDeletedByFileInfoIdAndUserId(file_id,user.getId(),true);
        DeletedFile deletedFile = new DeletedFile();
        deletedFile.setId(file_id);
        deletedFile.setDeletingDevice(new Device(device_id));
        deletedFileRepository.save(deletedFile);
        return new ResultInfo<>(0,"zero forever");
    }

    /**
     * 强制删除文件 (token)
     * @param file_id file id
     * @return if file doesn't exist, return code 1
     */

    @PostMapping("delete/force/{file_id}")
    public ResultInfo<String> forceDeleteFile(@PathVariable Long file_id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();

        FileInfo fileInfo = fileInfoRepository.findFileInfoByIdAndUser_Id(file_id,user.getId());

        if(fileInfo==null)
            return new ResultInfo<>(1,"no such file");

        //TODO: mq
        fileInfoRepository.deleteFileInfoById(file_id);
//        user.deleteFile(fileInfo.getSize(),fileInfo.getFileType());
//        userRepository.save(user);
        userRepository.updateUsedStorage(fileInfo.getSize(),user.getId());
        deleteFileInOSS(user, fileInfo);

        return new ResultInfo<>(0,"success");

    }


    /**
     * 分页获取回收站信息 (token)
     * @param size page size
     * @param page page num from zero
     * @return if request successfully, return file counts and file infos.<br>
     *         if parameters are invalid, return "no files"
     */

    @GetMapping("recycle")
    public ResultInfo<List<FileInfo>> recycleBin(Integer size,Integer page){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Pageable pageable = PageRequest.of(page,size);
        Page<FileInfo> files = fileInfoRepository.findFileInfosByUser_IdAndDeleted(user.getId(),true,pageable);
        if(files.isEmpty()){
            return new ResultInfo<>(1,"no files");
        }else {
            return new ResultInfo<>(0,Integer.toString(files.getNumberOfElements()),files.toList());
        }
    }

    /**
     * 删除回收站中的文件 (token)
     * @param id file id
     * @return if success, return 0.
     */

    @PostMapping("/delete/recycle/{id}")
    public ResultInfo<String> deleteRecycleFile(@PathVariable Long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        FileInfo fileInfo = fileInfoRepository.findFileInfoByIdAndUser_Id(id,user.getId());
        if(fileInfo==null)
            return new ResultInfo<>(1,"no such files");
        if(!deletedFileRepository.existsById(id))
            return new ResultInfo<>(2,"not deleted");
        deletedFileRepository.deleteById(id);
        fileInfoRepository.deleteFileInfoById(id);

        deleteFileInOSS(user, fileInfo);

        userRepository.updateUsedRecycleStorage(fileInfo.getSize(),user.getId());

//        user.deleteRecycle(fileInfo.getSize());
//        userRepository.save(user);

        return new ResultInfo<>(0,"success");
    }

    /**
     * 清空回收站 (token)
     * @return code 0
     */

    @PostMapping("delete/recycle/all")
    public ResultInfo<String> deleteRecycleAll(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        List<FileInfo> fileInfos = fileInfoRepository.findFileInfosByUser_IdAndDeleted(user.getId(), true);

        for (FileInfo fileInfo : fileInfos) {
            deleteFileInOSS(user, fileInfo);
//            user.deleteRecycle(fileInfo.getSize());
            userRepository.updateUsedRecycleStorage(fileInfo.getSize(), user.getId());
        }

//        userRepository.save(user);
        fileInfoRepository.deleteFileInfosByUser_IdAndDeleted(user.getId(),true);
        return new ResultInfo<>(0,"success");
    }

    /**
     * 恢复回收站中的文件 (token)
     * @param id file id
     * @return success
     */
    @PostMapping("restore/{id}")
    public ResultInfo<String> restoreFile(@PathVariable Long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        FileInfo fileInfo = fileInfoRepository.findFileInfoByIdAndUser_Id(id,user.getId());
        if(fileInfo==null)
            return new ResultInfo<>(1,"no such file");

        fileInfoRepository.updateFileInfoDeletedByFileInfoIdAndUserId(id,user.getId(),false);
        deletedFileRepository.deleteById(id);
        userRepository.updateUsedRecycleStorage(fileInfo.getSize(),user.getId());
        userRepository.updateUsedStorage(-fileInfo.getSize(), user.getId());

        return new ResultInfo<>(0,"success");
    }

    public void deleteFileInOSS(User user, FileInfo fileInfo) {
        String bucket = DigestUtils.md5DigestAsHex(user.getUsername().getBytes(StandardCharsets.UTF_8));
        StringBuilder object = new StringBuilder();
        if(fileInfo.getFolder()!=null){
            object.append(fileInfo.getFolder()).append("/");
        }
        object.append(fileInfo.getName());
        oss.deleteObject(bucket,object.toString());
    }


}
