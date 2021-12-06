package com.tongtu.tongtu.data;

import com.tongtu.tongtu.domain.Device;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface DeviceRepository extends CrudRepository<Device,Long> {
    Device findDeviceById(Long id);
    List<Device> findDevicesByUser_IdOrderByLastLoginAt(Long user_id);
    List<Device> findDevicesByUser_UsernameOrderByLastLoginAt(String username);

    @Transactional
    void deleteDeviceByIdAndUser_Id(Long id,Long userID);

    @Transactional
    @Modifying
    @Query(value = "update Device set lastLoginAt = ?2 where id = ?1")
    void updateLastLoginAt(Long id, Date date);


    @Transactional
    @Modifying
    @Query(value = "update Device set alias = ?2 where id = ?1")
    void updateAlias(Long id,String alias);



}
