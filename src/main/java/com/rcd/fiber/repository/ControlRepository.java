package com.rcd.fiber.repository;

import com.rcd.fiber.domain.entity.Control;
import com.rcd.fiber.domain.entity.Telemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author:zhoayi
 * @Description:
 * @Data: Created in 10:22 2019/9/25
 * @Modify By:
 */
@Repository
public interface ControlRepository extends JpaRepository<Control, Long> {

    List<Control> findAll();

    @Override
    List<Control> findAllById(Iterable<Long> longs);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "UPDATE control SET value = ?1 where  " +
        "site_name = ?2 and device_name=?3 and data_name=?4 " +
        "Order by timestamp DESC  LIMIT 1")
    int updateControlValue(int value,String site_name,String device_name,String data_name);

}
