package com.rcd.fiber.service;

import com.rcd.fiber.domain.entity.Site;
import com.rcd.fiber.repository.SiteRepository;
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

    //添加数据
    public void addSite(Site site){
        siteRepository.saveAndFlush(site);
    }

    //删除数据
    public void deleteSite(Site site){
        siteRepository.delete(site);
    }

    //更新数据
    public int  updateSite(Site site){
       return siteRepository.updateSiteValue(site.getSiteLevel(),site.getSiteLocalx(),site.getSiteLocaly(),site.getSiteName(),
           site.getSiteInfo(),site.getSiteType(),site.getSiteAddress(),site.getSiteId());
    }

}
