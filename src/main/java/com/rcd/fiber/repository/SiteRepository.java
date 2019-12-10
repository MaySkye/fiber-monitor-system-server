package com.rcd.fiber.repository;

import com.rcd.fiber.domain.entity.Site;
import com.rcd.fiber.domain.entity.Telemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:zhoayi
 * @Description:
 * @Data: Created in 20:06 2019/11/26
 * @Modify By:
 */
@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {

    List<Site> findAll();
}
