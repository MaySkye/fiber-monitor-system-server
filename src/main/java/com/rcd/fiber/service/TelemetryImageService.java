package com.rcd.fiber.service;

import com.rcd.fiber.domain.entity.TelemetryImage;
import com.rcd.fiber.repository.TelemetryImageRepository;
import com.rcd.fiber.web.rest.TelemetryImageResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HUHU
 * @Date: 2019/7/16 11:43
 */
@Service
public class TelemetryImageService {

    private final Logger log = LoggerFactory.getLogger(TelemetryImageService.class);

    private final TelemetryImageRepository dao;

    public TelemetryImageService(TelemetryImageRepository dao) {
        this.dao = dao;
    }

    public List<TelemetryImage> findBySiteName(String siteName) {
        return dao.findlBySiteName(siteName);
    }
}
