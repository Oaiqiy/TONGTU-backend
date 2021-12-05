package com.tongtu.tongtu.data;

import com.tongtu.tongtu.domain.Device;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface DeviceRepository extends CrudRepository<Device,Long> {
    Device findDeviceById(Long id);
    List<Device> findDevicesByUser_IdOrderByLastLoginAt(Long user_id);

    void deleteDeviceByIdAndUser_Id(Long id,Long userID);
    @Query(value = "update Device set lastLoginAt = ?2 where id = ?1")
    void updateLastLoginAt(Long id, Date date);

    @Query(value = "update Device set alias = ?2 where id = ?1")
    void updateAlias(Long id,String alias);



}
