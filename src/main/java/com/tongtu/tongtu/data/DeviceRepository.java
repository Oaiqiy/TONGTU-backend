package com.tongtu.tongtu.data;

import com.tongtu.tongtu.domain.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceRepository extends CrudRepository<Device,Long> {
    Device findDeviceById(Long id);
    List<Device> findDevicesByUser_Id(Long user_id);

}
