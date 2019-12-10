package com.rcd.fiber.service;

import com.rcd.fiber.domain.entity.Site;
import com.rcd.fiber.domain.entity.Telemetry;
import com.rcd.fiber.repository.SiteRepository;
import com.rcd.fiber.repository.TelemetryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author:zhoayi
 * @Description:
 * @Data: Created in 20:09 2019/11/26
 * @Modify By:
 */
@Service
@Transactional
public class SiteService {
    private final Logger log = LoggerFactory.getLogger(SiteService.class);
    private final SiteRepository siteRepository;

    public SiteService(SiteRepository siteRepository) {

        this.siteRepository = siteRepository;
    }

    // 返回所有数据
    public List<Site> getAllSite() {

        return siteRepository.findAll();
    }

}
