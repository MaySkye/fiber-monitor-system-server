package com.rcd.fiber.repository;

import com.rcd.fiber.domain.entity.TelemetryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: HUHU
 * @Date: 2019/7/16 11:41
 */
@Repository
public interface TelemetryImageRepository extends JpaRepository<TelemetryImage, Long> {

    @Query(nativeQuery=true,value = "select t.id,t.sitename,t.stateimage,t.timestamp from telemetry_image t where t.siteName = ?1 Order by t.timestamp DESC  LIMIT 1")
    List<TelemetryImage> findlBySiteName(String siteName);
}
