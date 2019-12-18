package com.rcd.fiber.service;

import com.rcd.fiber.domain.entity.Site;
import com.rcd.fiber.domain.entity.SiteLine;
import com.rcd.fiber.repository.SiteLineRepository;
import com.rcd.fiber.repository.SiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author:zhoayi
 * @Description:
 * @Data: Created in 16:03 2019/12/13
 * @Modify By:
 */
@Service
@Transactional
public class SiteLineService {
    private final Logger log = LoggerFactory.getLogger(SiteLineService.class);
    private final SiteLineRepository siteLineRepository;

    public SiteLineService(SiteLineRepository siteLineRepository) {
        this.siteLineRepository = siteLineRepository;
    }
    // 返回所有数据
    public List<SiteLine> getAllSiteLine() {
        return siteLineRepository.findAll();
    }
    public void addsiteLine(SiteLine siteline){
        siteLineRepository.saveAndFlush(siteline);
    }

}
