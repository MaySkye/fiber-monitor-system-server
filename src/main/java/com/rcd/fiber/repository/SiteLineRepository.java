package com.rcd.fiber.repository;

import com.rcd.fiber.domain.entity.Site;
import com.rcd.fiber.domain.entity.SiteLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE site_line SET line_name=?1 ,stable = ?2 ,transspeed=?3 ,state=?4 " +
        "where point1 = ?5 and point2=?6 ")
    int updateValues(String line_name, double stable, double transspeed, String state,String point1,String point2);
}
