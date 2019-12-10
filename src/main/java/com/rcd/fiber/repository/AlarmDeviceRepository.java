package com.rcd.fiber.repository;

import com.rcd.fiber.domain.entity.AlarmDevice;
import com.rcd.fiber.domain.entity.TelemetryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: HUHU
 * @Date: 2019/6/27 14:51
 */
@Repository
public interface AlarmDeviceRepository extends JpaRepository<AlarmDevice, Long> {

    List<AlarmDevice> findAllByStatus(Integer status);

    /**
     @Transactional 注解用于提交事务，若没有带上这句，会报事务异常提示。
     @Modifying(clearAutomatically = true) 自动清除实体里保存的数据。
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "UPDATE alarm_device SET status = 1 ")
    int updateData();
}
