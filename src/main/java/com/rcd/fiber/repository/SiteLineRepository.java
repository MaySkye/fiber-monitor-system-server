package com.rcd.fiber.repository;

import com.rcd.fiber.domain.entity.Site;
import com.rcd.fiber.domain.entity.SiteLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:zhoayi
 * @Description:
 * @Data: Created in 15:57 2019/12/13
 * @Modify By:
 */
@Repository
public interface SiteLineRepository extends JpaRepository<SiteLine, Long> {
    List<SiteLine> findAll();
}
