package com.tongtu.tongtu.api.user;

import com.tongtu.tongtu.api.ResultInfo;
import com.tongtu.tongtu.data.DeviceRepository;
import com.tongtu.tongtu.domain.Device;
import com.tongtu.tongtu.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/user/device")
public class DeviceController {
    private DeviceRepository deviceRepository;


    /**
     * 新增设备时使用
     * @param deviceInfo
     * @return
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
        device = deviceRepository.save(device);
        return new ResultInfo<>(0,"success",device.getId().toString());

    }


    /**
     * 获取用户全部设备信息
     * @return
     */

    @GetMapping("list")
    public ResultInfo<List<Device>> getAllDevice(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        List<Device> devices = deviceRepository.findDevicesByUser_IdOrderByLastLoginAt(user.getId());
        return new ResultInfo<>(0,"success",devices);
    }

    /**
     * 通过id删除设备使设备不能再次获取token
     * @param id
     * @return
     */

    @PostMapping("{id}")
    public ResultInfo<String> deleteDevice(@PathVariable Long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        deviceRepository.deleteDeviceByIdAndUser_Id(id,user.getId());
        return new ResultInfo<>(0,"deleted");
    }

    /**
     * 重命名设备
     * @param id
     * @param alias
     * @return
     */

    @GetMapping("{id}/{alias}")
    public ResultInfo<String> setAlias(@PathVariable Long id,@PathVariable String alias){
        deviceRepository.updateAlias(id,alias);
        return new ResultInfo<>(0,"updated");
    }



}
