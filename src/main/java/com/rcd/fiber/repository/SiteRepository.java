package com.rcd.fiber.repository;

import com.rcd.fiber.domain.entity.Site;
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
 * @Data: Created in 20:06 2019/11/26
 * @Modify By:
 */
@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    List<Site> findAll();


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE site SET site_level = ?1 ,site_localx = ?2 ,site_localy = ?3 ," +
        "site_name = ?4 ,site_info = ?5, site_type = ?6, site_address = ?7 where site_id = ?8" )
    int updateSiteValue(Long siteLevel, Double siteLocalx, Double siteLocaly, String siteName, String siteInfo,
                           String siteType, String siteAddress,Long siteId);
}
