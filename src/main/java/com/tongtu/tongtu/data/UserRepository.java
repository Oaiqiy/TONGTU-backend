package com.tongtu.tongtu.data;

import com.tongtu.tongtu.domain.Device;
import com.tongtu.tongtu.domain.User;
import org.hibernate.mapping.Collection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserById(Long id);

    User findUserByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);


    /**
     * update user's used storage
     * @param size positive number means increase, negative number means decrease.
     * @param id user id
     */
    @Transactional
    @Modifying
    @Query("update User set usedStorage = usedStorage + ?1 where id = ?2")
    void updateUsedStorage(Long size, Long id);

    /**
     * user's used recycle storage
     * @param size positive number means increase, negative number means decrease.
     * @param id user id
     */
    @Transactional
    @Modifying
    @Query("update User set usedRecycleStorage = usedRecycleStorage + ?2 where id = ?2")
    void updateUsedRecycleStorage(Long size,Long id);


}
