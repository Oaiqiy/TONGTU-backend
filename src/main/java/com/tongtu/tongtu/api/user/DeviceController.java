package com.tongtu.tongtu.api.user;

import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.data.DeviceRepository;
import com.tongtu.tongtu.data.FileInfoRepository;
import com.tongtu.tongtu.domain.Device;
import com.tongtu.tongtu.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/user/device")
public class DeviceController {
    private DeviceRepository deviceRepository;
    private FileInfoRepository fileInfoRepository;


    /**
     * 新增设备时使用
     * @param deviceInfo json includes uuid name type
     * @return if success
     */

    @PostMapping("add")
    public ResultInfo<String> addDevice(@RequestBody Map<String,String> deviceInfo){

        String uuid = deviceInfo.get("uuid");
        if (uuid==null){
            return new ResultInfo<>(1,"empty uuid");
        }
        String name = deviceInfo.get("name");
        String type = deviceInfo.get("type");
        Device device = new Device(uuid,name,type, (User) SecurityContextHolder.getContext().getAuthentication().getDetails());
        device.setLastLoginAt(new Date());
        device = deviceRepository.save(device);
        return new ResultInfo<>(0,"success",device.getId().toString());

    }


    /**
     * 获取用户全部设备信息
     * @return device list
     */

    @GetMapping("list")
    public ResultInfo<List<Device>> getAllDevice(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        //String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Device> devices = deviceRepository.findDevicesByUser_UsernameOrderByLastLoginAt(user.getUsername());
        return new ResultInfo<>(0,"success",devices);
    }

    /**
     * 通过id删除设备使设备不能再次获取token
     * @param data a json include "old" and optional "new"<br>
     *             if include "new" the files uploaded by "old" device will be transformed to "new" device<br>
     *             if not include "new" the files uploaded by "old" will be deleted<br>
     *             "old" and "new" both are device id
     * @return if success
     */

    @PostMapping("delete")
    public ResultInfo<String> deleteDevice(@RequestBody Map<String,Long> data){
        if(data.get("old")==null||data.get("new")==null){
            return new ResultInfo<>(1,"empty body");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();

        if(data.get("delete")!=null){
            fileInfoRepository.updateFileInfoDeleted(data.get("old"));
        }
        fileInfoRepository.updateFileInfoDevice(data.get("new"),data.get("old"));
        deviceRepository.deleteDeviceByIdAndUser_Id(data.get("old"),user.getId());

        return new ResultInfo<>(0,"delete successfully");

    }

    /**
     * 重命名设备
     * @param id device id
     * @param alias device alias
     * @return if success
     */

    @GetMapping("{id}/{alias}")
    public ResultInfo<String> setAlias(@PathVariable Long id,@PathVariable String alias){
        deviceRepository.updateAlias(id,alias);
        return new ResultInfo<>(0,"updated");
    }

}
