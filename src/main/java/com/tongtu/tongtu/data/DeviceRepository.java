package com.tongtu.tongtu.data;

import com.tongtu.tongtu.domain.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device,Long> {
    Device findDeviceById(Long id);
}
