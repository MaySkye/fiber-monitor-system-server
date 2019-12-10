package com.rcd.fiber.repository;

import com.rcd.fiber.domain.entity.Telemetry;
import com.rcd.fiber.domain.entity.TelemetryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: HUHU
 * @Date: 2019/6/17 14:47
 */
@Repository
public interface TelemetryRepository extends JpaRepository<Telemetry, Long> {

    List<Telemetry> findAll();

    @Override
    List<Telemetry> findAllById(Iterable<Long> longs);

    @Query(nativeQuery = true, value = "select * from telemetry  where site_name = ?1 and " +
        "device_name=?2 and data_name=?3 " +
        "Order by timestamp DESC  LIMIT 1")
    List<Telemetry> findMonitorValue(String site_name, String device_name, String data_name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE telemetry SET detected_value = ?1 where  " +
        "site_name = ?2 and device_name=?3 and data_name=?4 " +
        "Order by timestamp DESC  LIMIT 1")
    int updateMonitorValue(double detected_value, String site_name, String device_name, String data_name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE telemetry SET alarm_state = ?1 where  " +
        "site_name = ?2 and device_name=?3 and data_name=?4 " +
        "Order by timestamp DESC  LIMIT 1")
    int updateAlarmState(double alarm_state, String site_name, String device_name, String data_name);


    //王伟的jpa，修改监控值detected_value
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "" +
        "update telemetry " +
        "set detected_value = ?1 " +
        "where site_name = ?2 and device_name = ?3 and data_name = ?4")
    int updateDetectedValue(String detected_value, String site_name, String device_name, String data_name);

    //王伟的jpa，查询监控值dected_value
    @Query(nativeQuery = true, value = "" +
        "select * from telemetry " +
        "where site_name = ?1 and device_name=?2")
    List<Telemetry> getDetectedValueByParameters(String site_name, String device_name);
}
