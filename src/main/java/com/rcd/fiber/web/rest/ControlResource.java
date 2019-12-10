package com.rcd.fiber.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rcd.fiber.domain.entity.Control;
import com.rcd.fiber.domain.entity.Telemetry;
import com.rcd.fiber.service.ControlService;
import com.rcd.fiber.service.TelemetryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:zhoayi
 * @Description:
 * @Data: Created in 10:33 2019/9/25
 * @Modify By:
 */
@RestController
@RequestMapping("/control")
@CrossOrigin // 防止跨域
public class ControlResource {
    private final Logger logger = LoggerFactory.getLogger(ControlResource.class);
    private final ControlService service;
    public ControlResource(ControlService service) {
        this.service = service;
    }

    @GetMapping("/findall")
    @Timed
    public ResponseEntity<List<Control>> getAllTelemetry() {
        List<Control> list = service.getAllControl();
        return ResponseEntity.ok(list);
    }


}
