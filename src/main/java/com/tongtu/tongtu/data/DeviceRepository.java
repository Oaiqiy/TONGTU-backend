package com.tongtu.tongtu.data;

import com.tongtu.tongtu.domain.Device;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface DeviceRepository extends CrudRepository<Device,Long> {

    /**
     * find device by id
     * @param id device id
     * @return a device if exists
     */
    Device findDeviceByIdAndUser_Id(Long id,Long userId);

    /**
     * find devices by user id order by last login time
     * @param user_id user id
     * @return a list of devices if exists
     */
    List<Device> findDevicesByUser_IdOrderByLastLoginAt(Long user_id);

    /**
     * find devices by username order by last login time
     * @param username username
     * @return a list of devices if exists
     */
    List<Device> findDevicesByUser_UsernameOrderByLastLoginAt(String username);

    /**
     * find device by uuid
     * @param uuid uuid
     * @return one device
     */
    Device findDeviceByUuid(String uuid);

    /**
     * delete device by id and user id
     * @param id device id
     * @param userID user id
     */
    @Transactional
    void deleteDeviceByIdAndUser_Id(Long id,Long userID);

    /**
     * update device login time
     * @param id device id
     * @param date device login time
     */
    @Transactional
    @Modifying
    @Query(value = "update Device set lastLoginAt = ?3 where id = ?1 and user.id = ?2")
    int updateLastLoginAt(Long id, Long userId, Date date);

    /**
     * update device alias
     * @param id device id
     * @param alias device new alias
     */
    @Transactional
    @Modifying
    @Query(value = "update Device set alias = ?2 where id = ?1")
    void updateAlias(Long id,String alias);

    /**
     * update device's last login date by uuid
     * @param uuid uuid
     * @param lastLoginAt current date
     */
    @Transactional
    @Modifying
    @Query(value = "update Device set lastLoginAt = ?2 where uuid = ?1")
    void updateLastLoginAt(String uuid, Date lastLoginAt);


}
